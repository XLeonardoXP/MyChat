
package org.example.mychat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.mychat.model.Utente;
import org.example.mychat.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtenteService utenteService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthController(
            UtenteService utenteService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository) {
        this.utenteService = utenteService;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registraUtente(
            @RequestBody Map<String, String> dati) {

        try {
            String username = dati.get("username");
            String password = dati.get("password");

            Utente utente = utenteService.registraUtente(
                    username, password);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "messaggio", "Registrazione completata.",
                            "username", utente.getUsername()
                    ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("errore", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> dati,
            HttpServletRequest request,
            HttpServletResponse response) {

        String username = dati.get("username");
        String password = dati.get("password");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "errore",
                            "Username e password sono obbligatori."
                    ));
        }

        try {
            Authentication autenticazione =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username, password
                            )
                    );

            SecurityContext contesto =
                    SecurityContextHolder.createEmptyContext();

            contesto.setAuthentication(autenticazione);
            SecurityContextHolder.setContext(contesto);

            securityContextRepository.saveContext(
                    contesto, request, response
            );

            return ResponseEntity.ok(
                    Map.of(
                            "messaggio", "Login effettuato.",
                            "username",
                            autenticazione.getName()
                    )
            );

        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "errore",
                            "Username o password non corretti."
                    ));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> utenteCorrente(
            org.springframework.security.core.Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName().equals("anonymousUser")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("errore", "Utente non autenticato."));
        }

        return ResponseEntity.ok(
                Map.of("username", authentication.getName())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of(
                "messaggio", "Logout effettuato."
        ));
    }
}
