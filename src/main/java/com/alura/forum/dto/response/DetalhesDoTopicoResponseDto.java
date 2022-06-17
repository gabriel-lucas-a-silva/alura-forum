package com.alura.forum.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alura.forum.model.StatusTopico;
import com.alura.forum.model.Topico;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DetalhesDoTopicoResponseDto {
   
  private Long id;
  
  private String titulo;

  private String mensagem;

  private LocalDateTime dataCriacao;

  private String nomeAuthor;

  private StatusTopico status;

  private List<RespostaResponseDto> respostasResponseDto;

  public DetalhesDoTopicoResponseDto(Topico topico) {
    this.id = topico.getId();
    this.titulo = topico.getTitulo();
    this.mensagem = topico.getMensagem();
    this.dataCriacao = topico.getDataCriacao();
    this.nomeAuthor = topico.getAutor().getNome();
    this.status = topico.getStatus();
    this.respostasResponseDto = new ArrayList<>();
    this.respostasResponseDto.addAll(topico.getRespostas().stream().map(RespostaResponseDto::new).collect(Collectors.toList()));
  }

}
