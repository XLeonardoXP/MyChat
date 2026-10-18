
package org.example.mychat.controller;

import org.example.mychat.model.Messaggio;
import org.example.mychat.repository.MessaggioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Questa classe gestisce richieste HTTP e restituisce dati
@RequestMapping("/api/messaggi")
//Percorso delle api
public class MessaggioController {

    private final MessaggioRepository repository;

    public MessaggioController(MessaggioRepository repository) {
        this.repository = repository;
    }

    //Recupera tutti i messaggi con richiesta get
    @GetMapping
    public List<Messaggio> getMessaggi() {
        return repository.findAll();
    }

    //Inviare un messaggio con richiesta POST
    @PostMapping
    public Messaggio inviaMessaggio(@RequestBody Messaggio messaggio) {
        //@RequestBody converte json in oggetto Messaggio
        return repository.save(messaggio);
        //Usa il metodo save per salvare il messaggio nel database
    }
}
