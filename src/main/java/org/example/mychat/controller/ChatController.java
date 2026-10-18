
package org.example.mychat.controller;

import org.example.mychat.model.Messaggio;
import org.example.mychat.repository.MessaggioRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class ChatController {

    private final MessaggioRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(
            MessaggioRepository repository,
            SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    //riceve i messaggi inviati dai client a /app/chat.
    public void inviaMessaggio(Messaggio messaggio, Principal principal) {

        // Verifica che esista un utente autenticato.
        if (principal == null) {
            throw new IllegalStateException("Utente non autenticato.");
        }

        // Imposta lo username dell'utente autenticato.
        messaggio.setUsername(principal.getName());

        // Salva il messaggio nel database.
        Messaggio messaggioSalvato = repository.save(messaggio);

        // Invia il messaggio a tutti gli utenti iscritti alla chat.
        messagingTemplate.convertAndSend("/topic/messaggi", messaggioSalvato);
    }
}
