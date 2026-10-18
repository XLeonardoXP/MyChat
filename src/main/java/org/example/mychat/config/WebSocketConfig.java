package org.example.mychat.config;

// Permette di dichiarare una classe di configurazione Spring.
import org.springframework.context.annotation.Configuration;

// Permette di configurare il broker che gestisce la distribuzione dei messaggi.
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

// Abilita la gestione dei messaggi WebSocket tramite STOMP.
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

// Permette di registrare gli endpoint a cui i client si collegano.
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

// Interfaccia che fornisce i metodi per configurare WebSocket e STOMP.
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Indica a Spring che questa classe contiene configurazioni dell'applicazione.
@Configuration

// Abilita il supporto ai messaggi STOMP sopra WebSocket.
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Configura come vengono instradati e distribuiti i messaggi STOMP.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // Attiva un broker semplice che distribuisce i messaggi
        // ai client iscritti a destinazioni che iniziano con "/topic".
        // Esempio: "/topic/messaggi" e "/topic/utenti-online".
        config.enableSimpleBroker("/topic");

        // I messaggi inviati dai client a destinazioni che iniziano
        // con "/app" vengono indirizzati ai metodi dell'applicazione
        // annotati, per esempio, con @MessageMapping.
        // Esempio: "/app/chat" raggiunge @MessageMapping("/chat").
        config.setApplicationDestinationPrefixes("/app");
    }

    // Registra gli endpoint WebSocket a disposizione dei client.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Definisce l'indirizzo a cui il client apre la connessione WebSocket.
        // Nel frontend MyChat viene utilizzato:
        // ws://localhost:8080/chat
        registry.addEndpoint("/chat");
    }
}