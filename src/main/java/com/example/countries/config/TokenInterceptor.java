package com.example.countries.config;

import com.example.countries.entity.Token;
import com.example.countries.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenRepository tokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenStr = request.getHeader("Authorization");

        if (tokenStr != null && tokenStr.startsWith("Bearer ")) {
            tokenStr = tokenStr.substring(7); // Remove "Bearer "
        } else {
            tokenStr = request.getParameter("token"); // fallback
        }

        if (tokenStr == null || tokenStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Optional<Token> optToken = tokenRepository.findByToken(tokenStr);
        if (optToken.isEmpty() || optToken.get().getExpiracao().isBefore(LocalDateTime.now())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // Atualiza expiração (renovação automática)
        Token token = optToken.get();
        token.setExpiracao(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(token);

        // Armazena atributos para uso posterior
        request.setAttribute("login", token.getLogin());
        request.setAttribute("isAdmin", token.isAdministrador());

        return true;
    }
}

