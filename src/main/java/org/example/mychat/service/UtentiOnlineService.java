
package org.example.mychat.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UtentiOnlineService {

    // Associa ogni username alle sessioni WebSocket attive.
    private final Map<String, Set<String>> utentiOnline =
            new ConcurrentHashMap<>();

    public void utenteConnesso(String username, String sessionId) {
        utentiOnline
                .computeIfAbsent(
                        username,
                        chiave -> ConcurrentHashMap.newKeySet()
                )
                .add(sessionId);
    }

    public void utenteDisconnesso(String username, String sessionId) {
        Set<String> sessioni = utentiOnline.get(username);

        if (sessioni != null) {
            sessioni.remove(sessionId);

            if (sessioni.isEmpty()) {
                utentiOnline.remove(username, sessioni);
            }
        }
    }

    public Set<String> getUtentiOnline() {
        return Set.copyOf(utentiOnline.keySet());
    }
}
