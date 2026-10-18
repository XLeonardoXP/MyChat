package org.example.mychat.config;

// Permette di salvare il contesto di sicurezza nella sessione HTTP.
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

// Servizio personalizzato che recupera gli utenti dal database.
import org.example.mychat.service.UtenteDetailsService;

// Annotazioni per registrare configurazioni e oggetti gestiti da Spring.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Componenti responsabili dell'autenticazione.
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

// Permette di configurare le regole di sicurezza delle richieste HTTP.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Componente utilizzato per verificare le password.
import org.springframework.security.crypto.password.PasswordEncoder;

// Rappresenta la catena di filtri che protegge le richieste HTTP.
import org.springframework.security.web.SecurityFilterChain;

// Indica a Spring che questa classe contiene configurazioni di sicurezza.
@Configuration
public class SecurityConfig {

    @Bean
    // Configura il componente che verifica le credenziali degli utenti.
    // Collega il servizio che recupera gli utenti al controllo delle password.
    public DaoAuthenticationProvider authenticationProvider(
            UtenteDetailsService utenteDetailsService,
            PasswordEncoder passwordEncoder) {

        // Crea un provider che utilizza il nostro servizio
        // per caricare i dati dell'utente da autenticare.
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(utenteDetailsService);

        // Imposta l'encoder BCrypt per verificare la password inserita
        // rispetto all'hash della password memorizzato nel database.
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    // Espone l'AuthenticationManager, che coordina il processo
    // di autenticazione quando l'endpoint di login lo richiede.
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        // Recupera l'AuthenticationManager configurato da Spring Security.
        return configuration.getAuthenticationManager();
    }

    @Bean
    // Definisce le regole con cui Spring Security protegge le richieste HTTP.
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // Disabilita la protezione CSRF.
                // In questa configurazione il frontend invia richieste
                // POST per login, registrazione e logout.
                // Prima di usare questa configurazione in produzione,
                // bisogna valutare come proteggere le richieste autenticate.
                .csrf(csrf -> csrf.disable())

                // Stabilisce quali richieste possono essere effettuate
                // senza autenticazione.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                     // Pagina principale
                                "/index.html",           // Interfaccia della chat
                                "/login.html",           // Pagina di accesso
                                "/api/auth/register",    // Registrazione
                                "/api/auth/login"        // Login
                        ).permitAll()

                        // Tutte le altre richieste richiedono
                        // un utente autenticato.
                        .anyRequest().authenticated()
                )

                // Disabilita la pagina di login automatica di Spring Security.
                // MyChat utilizza la propria pagina login.html.
                .formLogin(form -> form.disable())

                // Disabilita l'autenticazione HTTP Basic.
                .httpBasic(basic -> basic.disable());

        // Costruisce e restituisce la catena di filtri configurata.
        return http.build();
    }

    @Bean
    // Definisce come salvare il contesto di sicurezza nella sessione HTTP.
    // Il contesto contiene informazioni sull'utente autenticato.
    public SecurityContextRepository securityContextRepository() {

        // Utilizza la sessione HTTP per conservare il contesto di sicurezza.
        return new HttpSessionSecurityContextRepository();
    }
}
