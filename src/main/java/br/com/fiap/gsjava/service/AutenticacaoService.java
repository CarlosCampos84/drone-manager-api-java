package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = repository.findByEmail(username);
        if (Objects.isNull(usuario)) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        return usuario;
    }
}
