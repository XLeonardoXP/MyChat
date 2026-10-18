
package org.example.mychat.service;

import org.example.mychat.model.Utente;
import org.example.mychat.repository.UtenteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtenteDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public UtenteDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utente non trovato: " + username));

        return User.withUsername(utente.getUsername())
                .password(utente.getPassword())
                .roles("USER")
                .build();
    }
}
