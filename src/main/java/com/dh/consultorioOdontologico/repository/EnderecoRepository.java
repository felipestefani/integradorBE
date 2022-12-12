package com.dh.consultorioOdontologico.repository;

import com.dh.consultorioOdontologico.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findById(Long id);

    Optional<Endereco> findByRuaAndNumeroAndSiglaEstado(String rua, int numero, String sigla_estado);
}
