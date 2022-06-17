package com.alura.forum.dto.response;

import java.time.LocalDateTime;

import com.alura.forum.model.Resposta;

import lombok.Data;

@Data
public class RespostaResponseDto {
  
  private Long id;

  private String menssagem;

  private LocalDateTime dataCriacao;

  private String nomeAutor;

  public RespostaResponseDto(Resposta resposta) {
    this.id = resposta.getId();
    this.menssagem = resposta.getMensagem();
    this.dataCriacao = resposta.getDataCriacao();
    this.nomeAutor = resposta.getAutor().getNome();
  }

}
