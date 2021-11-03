package com.dselector.dselector_app.api.v1.rest.firewall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/api/v1/firewall/logs", produces = "application/json;charset=UTF-8")
public class FirewallLogsController {

    @Autowired
    public FirewallLogsController(){}

    @GetMapping("/")
    public ResponseEntity<?> getFirewallLogs(){
        return null;
    }

}
