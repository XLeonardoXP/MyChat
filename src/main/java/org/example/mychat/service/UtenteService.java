
package org.example.mychat.service;

import org.example.mychat.model.Utente;
import org.example.mychat.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//Comunica a Spring che questa classe contiene logica applicativa
//quindi si può usare UtenteService senza bisogno di istanziare
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(
            UtenteRepository utenteRepository,
            PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente registraUtente(String username, String password) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Lo username non può essere vuoto.");
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "La password deve contenere almeno 8 caratteri.");
        }

        if (utenteRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Questo username è già utilizzato.");
        }

        String passwordCifrata = passwordEncoder.encode(password);

        Utente nuovoUtente = new Utente(username, passwordCifrata);

        return utenteRepository.save(nuovoUtente);
    }
}
