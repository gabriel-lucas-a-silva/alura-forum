package com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.forum.dto.request.TopicoRequestDto;
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
  public ResponseEntity<TopicoResponseDto> cadastrar(@RequestBody TopicoRequestDto topicoRequestDto, UriComponentsBuilder uBuilder) {
    Topico topico = topicoRequestDto.converterParaTopico(cursoRepository);
    topicoRepository.save(topico);

    URI uri = uBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
    TopicoResponseDto topicoResponseDto = new TopicoResponseDto(topico);
    
    return ResponseEntity.created(uri).body(topicoResponseDto);
  }
  
  @GetMapping
  public List<TopicoResponseDto> lista(String nomeCurso) {
    if (nomeCurso == null) {
      List<Topico> topicos = topicoRepository.findAll();
      return TopicoResponseDto.converter(topicos);
    }
    
    List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
    return TopicoResponseDto.converter(topicos);
  }

} 
