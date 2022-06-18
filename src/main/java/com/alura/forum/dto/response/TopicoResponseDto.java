package com.alura.forum.dto.response;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.alura.forum.model.Topico;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

  public static Page<TopicoResponseDto> converter(Page<Topico> topicos) {
    return topicos.map(TopicoResponseDto::new);
  }

}
