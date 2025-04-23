package com.example.countries.service;

import com.example.countries.entity.Pais;
import com.example.countries.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;

    public List<Pais> listarTodos() {
        return paisRepository.findAll();
    }

    public List<Pais> pesquisarPorNome(String filtro) {
        return paisRepository.findByNomeContainingIgnoreCase(filtro);
    }

    public Pais salvar(Pais pais) {
        if (pais.getId() != null && pais.getId() == 0) {
            pais.setId(null); // força o Hibernate a fazer persist(), não merge()
        }
        return paisRepository.save(pais);
    }

    public boolean excluir(Long id) {
        Optional<Pais> pais = paisRepository.findById(id);
        if (pais.isPresent()) {
            paisRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
