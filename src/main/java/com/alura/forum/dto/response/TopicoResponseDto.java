package com.alura.forum.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.alura.forum.model.Topico;

import lombok.Getter;

@Getter
public class TopicoResponseDto {
  
  private Long id;
  
  private String titulo;

  private String mensagem;

  private LocalDateTime dataCriacao;

  public TopicoResponseDto(Topico topico) {
    this.id = topico.getId();
    this.titulo = topico.getTitulo();
    this.mensagem = topico.getMensagem();
    this.dataCriacao = topico.getDataCriacao();
  }

  public static List<TopicoResponseDto> converter(List<Topico> topicos) {
    return topicos.stream().map(TopicoResponseDto::new).collect(Collectors.toList());
  }

}
