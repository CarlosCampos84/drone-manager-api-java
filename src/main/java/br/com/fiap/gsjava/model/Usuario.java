package br.com.fiap.gsjava.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_gs_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_usuario", nullable = false, length = 100)
    private String nome;

    @Column(name = "nm_email", nullable = false, unique = true)
    private String email;

    @Column(name = "ds_senha", nullable = false, length = 255)
    private String senha;

    public Usuario() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}


