package com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public List<TopicoResponseDto> lista(final String nomeCurso) {
    if (nomeCurso == null) {
      List<Topico> topicos = topicoRepository.findAll();
      return TopicoResponseDto.converter(topicos);
    }
    
    List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
    return TopicoResponseDto.converter(topicos);
  }

  @PutMapping("/{id}")
  @Transactional
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
  public ResponseEntity<?> remover(@PathVariable Long id) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);

    if (optionalTopico.isPresent()) {
      topicoRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.notFound().build();
  }

} 
