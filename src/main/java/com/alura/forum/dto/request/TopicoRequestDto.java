package com.alura.forum.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alura.forum.model.Curso;
import com.alura.forum.model.Topico;
import com.alura.forum.repository.CursoRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicoRequestDto {

  @NotNull
  @NotEmpty
  @Size(min = 3)
  private String titulo;

  @NotNull
  @NotEmpty
  @Size(min = 5)
  private String mensagem;
  
  @NotNull
  @NotEmpty
  private String nomeCurso;

  public Topico converterParaTopico(CursoRepository cursoRepository) {
    Curso curso = cursoRepository.findByNome(nomeCurso);
    return new Topico(titulo, mensagem, curso);
  }
   
}
