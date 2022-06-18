package com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.forum.dto.request.AtualizacaoTopicoRequestDto;
import com.alura.forum.dto.request.TopicoRequestDto;
import com.alura.forum.dto.response.DetalhesDoTopicoResponseDto;
import com.alura.forum.dto.response.TopicoResponseDto;
import com.alura.forum.model.Topico;
import com.alura.forum.repository.CursoRepository;
import com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

  @Autowired
  private TopicoRepository topicoRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @PostMapping
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<TopicoResponseDto> cadastrar(@RequestBody @Valid final TopicoRequestDto topicoRequestDto, UriComponentsBuilder uBuilder) {
    Topico topico = topicoRequestDto.converterParaTopico(cursoRepository);
    topicoRepository.save(topico);

    URI uri = uBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
    TopicoResponseDto topicoResponseDto = new TopicoResponseDto(topico);

    return ResponseEntity.created(uri).body(topicoResponseDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetalhesDoTopicoResponseDto> detalhar(@PathVariable final Long id) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);
    DetalhesDoTopicoResponseDto detalheTopicoResponseDto = new DetalhesDoTopicoResponseDto();

    if (optionalTopico.isPresent()) {
      Topico safeTopico = optionalTopico.get();
      detalheTopicoResponseDto = new DetalhesDoTopicoResponseDto(safeTopico);
      return ResponseEntity.ok(detalheTopicoResponseDto);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping
  @Cacheable(value = "listaDeTopicos")
  public Page<TopicoResponseDto> listar(@RequestParam(required = false) final String nomeCurso,
  @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) final Pageable paginacao) {
    // Pageable paginacao = PageRequest.of(pagina, qtdElementos, Direction.ASC, ordenacao);
    if (nomeCurso == null) {
      Page<Topico> topicos = topicoRepository.findAll(paginacao);
      return TopicoResponseDto.converter(topicos);
    }

    Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
    return TopicoResponseDto.converter(topicos);
  }

  @PutMapping("/{id}")
  @Transactional
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<TopicoResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoRequestDto atualicaoTopicoRequestDto) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);

    if (optionalTopico.isPresent()) {
      Topico topico = atualicaoTopicoRequestDto.atualizar(id, topicoRepository);
      TopicoResponseDto topicoResponseDto = new TopicoResponseDto(topico);
      return ResponseEntity.ok(topicoResponseDto);
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<?> remover(@PathVariable Long id) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);

    if (optionalTopico.isPresent()) {
      topicoRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.notFound().build();
  }

} 
