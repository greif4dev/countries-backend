package com.example.countries.repository;

import com.example.countries.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    List<Pais> findByNomeContainingIgnoreCase(String nome);
}
