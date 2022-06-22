package com.alura.forum.controller;

import com.alura.forum.config.security.TokenService;
import com.alura.forum.dto.request.UsuarioLoginRequestDto;
import com.alura.forum.dto.response.TokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginRequestDto usuarioLoginRequestDto) {
        UsernamePasswordAuthenticationToken dadosLogin = usuarioLoginRequestDto.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            TokenResponseDto tokenDto = new TokenResponseDto(token, "Bearer");

            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
