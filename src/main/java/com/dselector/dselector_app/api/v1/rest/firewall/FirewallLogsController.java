package com.dselector.dselector_app.api.v1.rest.firewall;

import com.dselector.dselector_app.http.request.FirewallIPRequest;
import com.dselector.dselector_app.http.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value ="/api/v1/firewall/logs", produces = "application/json;charset=UTF-8")
public class FirewallLogsController {

    @Autowired
    public FirewallLogsController(){}

    //http://localhost:8080/api/v1/firewall/logs/
    //@RequestBody FirewallIPRequest firewallIPRequest
    //@MessageMapping("/user-all")
    //@SendTo("/topic/firewall-logs")
    @GetMapping("/")
    public ResponseEntity<?> getFirewallLogs(){
        Socket socket;
        String firewallIp = "192.168.1.1";
        int firewallPort = 444;
        BufferedReader socketInputData;
        String line;
        try {
            //establish a socket connection with the firewall
            /*
            socket = new Socket(Proxy.NO_PROXY);
            System.out.println("2");
            SocketAddress socketAddress = new InetSocketAddress(firewallIp, firewallPort);
            socket.connect(socketAddress);
            System.out.println(Arrays.toString(socket.getInetAddress().getAddress()));
            */
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory
                    .createSocket(firewallIp, firewallPort);
            if(!sslsocket.getInetAddress().isReachable(30000)){
                System.out.println("not reachable");
                return ResponseEntity.ok(new APIResponse(HttpStatus.REQUEST_TIMEOUT, "firewall is unreachable"));
            }
            //Create input stream to read from the firewall
            System.out.println("3");
            socketInputData = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
            System.out.println("4");
            //start reading incoming data from the firewall
            line = socketInputData.readLine();
            System.out.println("line is " + line);
            while(line != null) {
                System.out.println("5");
                System.out.println( line );
                line = socketInputData.readLine();
            }
        } catch(Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK, "firewall logs"));
    }

    @GetMapping("/available-ips")
    public void getAvailableIps(){
        IntStream.rangeClosed(1,254).mapToObj(num -> "192.168.1."+num).parallel()
                .filter((addr) -> {
                    try {
                        System.out.println("address is " + addr);
                        return InetAddress.getByName(addr).isReachable(2000);
                    } catch (IOException e) {
                        return false;
                    }
                }).forEach(System.out::println);
    }


}
