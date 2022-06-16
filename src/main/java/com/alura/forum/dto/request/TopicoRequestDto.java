package com.alura.forum.dto.request;

import com.alura.forum.model.Curso;
import com.alura.forum.model.Topico;
import com.alura.forum.repository.CursoRepository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicoRequestDto {

  private String titulo;

  private String mensagem;

  private String nomeCurso;

  public Topico converterParaTopico(CursoRepository cursoRepository) {
    Curso curso = cursoRepository.findByNome(nomeCurso);
    return new Topico(titulo, mensagem, curso);
  }
  
}
