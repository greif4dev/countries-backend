package com.example.countries.controller;

import com.example.countries.entity.Pais;
import com.example.countries.service.PaisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pais")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService paisService;

    @GetMapping("/listar")
    public List<Pais> listar() {
        return paisService.listarTodos();
    }

    @GetMapping("/pesquisar")
    public List<Pais> pesquisar(@RequestParam String filtro) {
        return paisService.pesquisarPorNome(filtro);
    }

    @PostMapping("/salvar")
    public Object salvar(@RequestBody Pais pais, HttpServletRequest request) {
        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            return new ResponseEntity<>("Acesso negado. Apenas administradores podem salvar.", HttpStatus.UNAUTHORIZED);
        }

        return paisService.salvar(pais);
    }

    @GetMapping("/excluir")
    public Object excluir(@RequestParam Long id, HttpServletRequest request) {
        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            return new ResponseEntity<>("Acesso negado. Apenas administradores podem excluir.", HttpStatus.UNAUTHORIZED);
        }

        boolean excluido = paisService.excluir(id);
        return excluido;
    }
}
