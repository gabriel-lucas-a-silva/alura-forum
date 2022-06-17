package com.alura.forum.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alura.forum.model.Topico;
import com.alura.forum.repository.TopicoRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtualizacaoTopicoRequestDto {
  
  @NotNull
  @NotEmpty
  @Size(min = 3)
  private String titulo;

  @NotNull
  @NotEmpty
  @Size(min = 5)
  private String mensagem;

  public Topico atualizar(Long id, TopicoRepository topicoRepository) {
    Topico topico = topicoRepository.getReferenceById(id);
    topico.setTitulo(this.titulo);
    topico.setMensagem(this.mensagem);
    
    return topico;
  }

}
