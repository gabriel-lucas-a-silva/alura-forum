package com.alura.forum.dto.request;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UsuarioLoginRequestDto {

    @Email
    private String email;

    @Size(min = 3)
    private String senha;

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
