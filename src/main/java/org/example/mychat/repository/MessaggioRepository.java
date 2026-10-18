package org.example.mychat.repository;

// Importa la classe che rappresenta un messaggio della chat.
import org.example.mychat.model.Messaggio;

// Importa JpaRepository, che mette a disposizione metodi
// già pronti per interagire con il database.
import org.springframework.data.jpa.repository.JpaRepository;

// Questa interfaccia permette di gestire i messaggi nel database.
// Spring Data JPA genera automaticamente l'implementazione,
// quindi non dobbiamo scrivere noi il codice SQL per le operazioni standard.
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {

    // Messaggio: indica il tipo di oggetto gestito dal repository.
    // Long: indica il tipo di dato usato per l'ID del messaggio.
    //
    // Esempi di metodi già disponibili:
    // save(messaggio)  -> salva un messaggio o aggiorna quello esistente.
    // findAll()        -> recupera tutti i messaggi.
    // findById(id)     -> cerca un messaggio tramite il suo ID.
    // deleteById(id)   -> elimina un messaggio tramite il suo ID.
}
