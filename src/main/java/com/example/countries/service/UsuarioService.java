package com.example.countries.service;

import com.example.countries.dto.UsuarioAutenticado;
import com.example.countries.entity.Token;
import com.example.countries.entity.Usuario;
import com.example.countries.repository.TokenRepository;
import com.example.countries.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioAutenticado autenticar(String login, String senha) {
        Optional<Usuario> optUsuario = usuarioRepository.findByLogin(login);
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            if (encoder.matches(senha, usuario.getSenha())) {
                String tokenStr = UUID.randomUUID().toString();
                LocalDateTime expiracao = LocalDateTime.now().plusMinutes(5);

                Token token = new Token(null, tokenStr, login, expiracao, usuario.isAdministrador());
                tokenRepository.save(token);

                return new UsuarioAutenticado(login, usuario.getNome(), tokenStr, usuario.isAdministrador(), true);
            }
        }
        return new UsuarioAutenticado(login, "", null, false, false);
    }

    public boolean renovarTicket(String tokenStr) {
        Optional<Token> optToken = tokenRepository.findByToken(tokenStr);
        if (optToken.isPresent()) {
            Token token = optToken.get();
            token.setExpiracao(LocalDateTime.now().plusMinutes(5));
            tokenRepository.save(token);
            return true;
        }
        return false;
    }
}