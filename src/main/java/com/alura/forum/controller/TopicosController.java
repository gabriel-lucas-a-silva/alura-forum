package com.alura.forum.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.forum.dto.response.TopicoDto;
import com.alura.forum.model.Topico;

@RestController
public class TopicosController {
  
  @RequestMapping("/topicos")
  public List<TopicoDto> lista() {
    Topico topico1 = Topico.builder().build();

    return TopicoDto.converter(List.of(topico1, topico1, topico1));
  }

} 
