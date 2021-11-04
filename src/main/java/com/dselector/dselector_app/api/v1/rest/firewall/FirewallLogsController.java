package com.dselector.dselector_app.api.v1.rest.firewall;

import com.dselector.dselector_app.http.request.FirewallIPRequest;
import com.dselector.dselector_app.http.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.*;
import java.io.*;

@RestController
@RequestMapping(value ="/api/v1/firewall/logs", produces = "application/json;charset=UTF-8")
public class FirewallLogsController {

    @Autowired
    public FirewallLogsController(){}

    //http://localhost:8080/api/v1/firewall/logs/
    @MessageMapping("/user-all")
    @SendTo("/topic/firewall-logs")
    public ResponseEntity<?> getFirewallLogs(@RequestBody FirewallIPRequest firewallIPRequest){
        Socket socket;
        String firewallIp = "127.0.0.1";
        int firewallPort = 514;
        BufferedReader socketInputData;
        String line;
        try {
            //establish a socket connection with the firewall
            socket = new Socket(firewallIp, firewallPort);

            //Create input stream to read from the firewall
            socketInputData = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //start reading incoming data from the firewall
            line = socketInputData.readLine();
            while( line != null ) {
                System.out.println( line );
                line = socketInputData.readLine();
            }
        } catch(Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK, "firewall logs"));
    }


}
