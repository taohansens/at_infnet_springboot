package org.taohansen.at_infnet_springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.taohansen.at_infnet_springboot.entities.Usuario;
import org.taohansen.at_infnet_springboot.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByNome(username);
        if(usuario == null) {
            throw  new UsernameNotFoundException("Usuário não cadastrado!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getPapel()));
        return new org.springframework.security.core.userdetails.User(usuario.getNome(), usuario.getSenha(), authorities);
    }
}
