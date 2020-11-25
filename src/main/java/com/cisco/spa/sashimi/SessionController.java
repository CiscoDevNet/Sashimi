package com.cisco.spa.sashimi;

import code.messy.net.radius.packet.RadiusPacket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.net.*;

@RestController
@RequestMapping("session")
public class SessionController {
    @GetMapping
    public RedirectView get() {
        return new RedirectView("/swagger-ui/index.html?url=/v3/api-docs#/default/create");
    }

    @PostMapping
    public ResponseEntity<Session> create(@Valid @RequestBody Session session) throws Exception {
        RadiusPacket resp = RadiusActions.login(session);
        RadiusPacket resp1 = RadiusActions.start(session);
        System.out.println(resp.toString());
        System.out.println(resp1.toString());
        return ResponseEntity.status(responseCode(resp.getCode()))
                .body(session);
    }

    @PutMapping
    public ResponseEntity<Session> update(@Valid @RequestBody Session session) throws Exception {
        RadiusPacket resp = null;
        if (session.getState() == STATE.DISCONNECTED) {
            resp = RadiusActions.stop(session);
        } else if (session.getState() == STATE.AUTHENTICATED) {
            resp = RadiusActions.login(session);
        } else if (session.getState() == STATE.STARTED) {
            resp = RadiusActions.start(session);
        }
        if (resp != null) {
            System.out.println(resp.toString());
        }
        return ResponseEntity.status(responseCode(resp.getCode()))
                .body(session);
    }

    public static int responseCode(int code) {
        switch (code) {
            case 2:
                return 202;
            case 3:
                return 401;
            case 11:
                return 302;
            case 5:
            case 40:
            case 43:
                return 200;
        }
        return 400;
    }
}

