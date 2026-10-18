package org.example.mychat.config;

// Importa le annotazioni per configurare Spring.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Importa BCrypt, l'algoritmo utilizzato per elaborare le password.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Indica a Spring che questa classe contiene configurazioni dell'applicazione.
@Configuration
public class PasswordConfig {

    // Registra un oggetto PasswordEncoder nel contenitore di Spring.
    // Gli altri componenti possono richiederlo tramite dependency injection.
    @Bean
    public PasswordEncoder passwordEncoder() {

        // Crea un encoder che utilizza l'algoritmo BCrypt.
        // Viene utilizzato per generare hash delle password
        // e per verificare le password inserite durante il login.
        return new BCryptPasswordEncoder();
    }

    // BCrypt non memorizza la password originale, ma il suo hash.
    // Durante il login, confronta la password inserita con l'hash salvato.
    // Utilizza un salt casuale, quindi la stessa password può generare
    // hash differenti.
    // Non è necessario né possibile decifrare l'hash per recuperare la password.
}
