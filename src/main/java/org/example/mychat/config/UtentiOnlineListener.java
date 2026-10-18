package org.example.mychat.config;

// Servizio che mantiene l'elenco degli utenti online.
import org.example.mychat.service.UtentiOnlineService;

// Permette di eseguire un metodo quando Spring rileva un evento.
import org.springframework.context.event.EventListener;

// Permette di inviare messaggi ai client collegati tramite WebSocket.
import org.springframework.messaging.simp.SimpMessagingTemplate;

// Permette di leggere le informazioni contenute nei messaggi STOMP.
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

// Indica a Spring che questa classe è un componente gestito automaticamente.
import org.springframework.stereotype.Component;

// Eventi generati quando una connessione STOMP viene stabilita
// oppure quando una sessione WebSocket viene chiusa.
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

// Rappresenta l'identità dell'utente autenticato.
import java.security.Principal;

// Registra questa classe come componente Spring.
@Component
public class UtentiOnlineListener {

    // Gestisce gli utenti online e le loro sessioni.
    private final UtentiOnlineService utentiOnlineService;

    // Permette di inviare aggiornamenti ai client della chat.
    private final SimpMessagingTemplate messagingTemplate;

    // Spring passa automaticamente le dipendenze necessarie
    // attraverso il costruttore (dependency injection).
    public UtentiOnlineListener(
            UtentiOnlineService utentiOnlineService,
            SimpMessagingTemplate messagingTemplate) {

        this.utentiOnlineService = utentiOnlineService;
        this.messagingTemplate = messagingTemplate;
    }

    // Viene eseguito quando Spring segnala che una connessione STOMP
    // è stata stabilita correttamente.
    @EventListener
    public void dopoConnessione(SessionConnectedEvent evento) {

        // Recupera le informazioni STOMP associate all'evento.
        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(evento.getMessage());

        // Recupera l'identità dell'utente associato alla connessione.
        Principal principal = accessor.getUser();

        // Recupera l'identificativo univoco della sessione STOMP.
        String sessionId = accessor.getSessionId();

        // Se manca l'utente autenticato o l'ID della sessione,
        // non è possibile registrare correttamente la connessione.
        if (principal == null || sessionId == null) {
            return;
        }

        // Registra l'utente come connesso.
        // L'username identifica l'utente e sessionId la connessione.
        utentiOnlineService.utenteConnesso(
                principal.getName(), sessionId);

        // Comunica a tutti i client l'elenco aggiornato degli utenti online.
        aggiornaListaUtenti();
    }

    // Viene eseguito quando una sessione WebSocket viene disconnessa.
    @EventListener
    public void dopoDisconnessione(SessionDisconnectEvent evento) {

        // Recupera le informazioni associate alla sessione disconnessa.
        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(evento.getMessage());

        // Recupera l'utente e l'ID della sessione.
        Principal principal = accessor.getUser();
        String sessionId = accessor.getSessionId();

        // Se mancano informazioni essenziali, interrompe il metodo.
        if (principal == null || sessionId == null) {
            return;
        }

        // Rimuove la sessione dell'utente dal servizio.
        // Se l'utente non ha altre sessioni attive, non risulterà più online.
        utentiOnlineService.utenteDisconnesso(
                principal.getName(), sessionId);

        // Invia ai client la nuova lista degli utenti online.
        aggiornaListaUtenti();
    }

    // Recupera l'elenco aggiornato degli utenti online
    // e lo invia a tutti i client iscritti al topic.
    private void aggiornaListaUtenti() {
        messagingTemplate.convertAndSend(
                "/topic/utenti-online",
                utentiOnlineService.getUtentiOnline());
    }
}
