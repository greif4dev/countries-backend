package com.example.countries.controller;

import com.example.countries.dto.UsuarioAutenticado;
import com.example.countries.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/autenticar")
    public UsuarioAutenticado autenticar(@RequestParam String login, @RequestParam String senha) {
        return usuarioService.autenticar(login, senha);
    }

    @GetMapping("/renovar-ticket")
    public boolean renovar(@RequestParam String token) {
        return usuarioService.renovarTicket(token);
    }
}
