package org.example.mychat.repository;

// Optional rappresenta un valore che potrebbe essere presente oppure assente.
import java.util.Optional;

// Importa la classe che rappresenta un utente nel database.
import org.example.mychat.model.Utente;

// Fornisce metodi già pronti per interagire con il database.
import org.springframework.data.jpa.repository.JpaRepository;

// Repository dedicato alla gestione degli utenti.
// Spring Data JPA genera automaticamente l'implementazione di questa interfaccia.
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    // Utente: indica la classe gestita dal repository.
    // Long: indica il tipo di dato dell'ID dell'utente.
    //
    // Sono già disponibili metodi come:
    // save(utente)  -> salva un utente o aggiorna quello esistente.
    // findAll()     -> recupera tutti gli utenti.
    // findById(id)  -> cerca un utente tramite il suo ID.
    // deleteById(id)-> elimina un utente tramite il suo ID.

    // Cerca un utente tramite il suo username.
    // Spring Data JPA ricava automaticamente la query dal nome del metodo.
    // Optional contiene l'utente se viene trovato, altrimenti è vuoto.
    Optional<Utente> findByUsername(String username);

    // Verifica se esiste già un utente con lo username specificato.
    // Restituisce true se esiste, false altrimenti.
    // È utile durante la registrazione per evitare username duplicati.
    boolean existsByUsername(String username);
}