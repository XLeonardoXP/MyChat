
package org.example.mychat.controller;

import org.example.mychat.service.UtentiOnlineService;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class UtentiOnlineController {

    private final UtentiOnlineService utentiOnlineService;

    public UtentiOnlineController(UtentiOnlineService utentiOnlineService) {
        this.utentiOnlineService = utentiOnlineService;
    }

    // Restituisce l'elenco aggiornato quando un client si iscrive.
    @SubscribeMapping("/utenti-online")
    public Set<String> getUtentiOnline() {
        return utentiOnlineService.getUtentiOnline();
    }
}
