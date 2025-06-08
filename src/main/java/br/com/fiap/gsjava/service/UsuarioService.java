package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.LoginDTO;
import br.com.fiap.gsjava.dto.UsuarioRequestDTO;
import br.com.fiap.gsjava.model.Usuario;
import br.com.fiap.gsjava.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final TokenService tokenService;
    private final AutenticacaoService autenticacaoService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, TokenService tokenService, AutenticacaoService autenticacaoService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.autenticacaoService = autenticacaoService;
        this.passwordEncoder = passwordEncoder;
    }

    public String loginUsuario(LoginDTO dto) {
        Usuario usuario = (Usuario) autenticacaoService.loadUserByUsername(dto.email());
        if (!passwordEncoder.matches(dto.senha(), usuario.getPassword())) {
            throw new BadCredentialsException("Senha incorreta");
        }
        return tokenService.genToken(usuario);
    }

    public void registrarUsuario(@Valid UsuarioRequestDTO dto) {
        if (!Objects.isNull(repository.findByEmail(dto.email()))) {
            throw new ValidationException("Usuário já cadastrado com este email");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        repository.save(usuario);
    }
}
