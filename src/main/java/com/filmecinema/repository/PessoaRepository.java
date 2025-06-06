package com.filmecinema.repository;

import com.filmecinema.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByIdPessoa(Long IdPessoa);
}

