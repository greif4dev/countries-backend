package com.example.countries.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAutenticado {
    private String login;
    private String nome;
    private String token;
    private boolean administrador;
    private boolean autenticado;
}
