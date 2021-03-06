package com.message.conf;

import com.corundumstudio.socketio.SocketIOServer;
import com.message.chat.listener.*;
import com.message.mq.Sender;
import com.message.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lex on 2016/12/9.
 */
@Configuration
public class ChatServerConfig {

    @Autowired
    private CacheService cache;
    @Autowired
    private Sender sender;

    @Bean
    @Qualifier("chatServer")
    public SocketIOServer chatServer(
            @Value("${socket.port}") Integer socketPort
    ) {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

        config.setPort(socketPort);

        SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("send_message",
                Message.class,
                new MessageListener(server, cache, sender)
        );
        server.addEventListener("send_register",
                Register.class,
                new RegisterListener(server, cache, sender)
        );
        server.addEventListener("send_login",
                LoginGuest.class,
                new LoginGuestListener(server, cache, sender)
        );
        server.addEventListener("send_test",
                String.class,
                new TestListener()
        );
        server.addDisconnectListener(new DisconnectListener(server, cache, sender));

        server.start();
        return server;
    }
}
