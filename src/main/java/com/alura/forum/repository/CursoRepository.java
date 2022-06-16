package com.alura.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.forum.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
  
  Curso findByNome(String nome);

}
