package com.message.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.message.model.Agent;
import com.message.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lex on 2016/12/9.
 */
@Controller
public class AppController extends AbstractController {
    @Autowired
    private SocketIOServer chatServer;
    @Autowired
    private CacheService cache;

    @RequestMapping("/")
    public String index(Model model) {
        List<Agent> users = new ArrayList<>();
        model.addAttribute("users", users);
        return "chat/index";
    }

    @RequestMapping("/guest")
    public String guest(Model model) {
        return "chat/guest";
    }

    @RequestMapping("/agent")
    public String agent(Model model) {
        model.addAttribute("username", getCurrentUsername());
        return "chat/agent";
    }

    @RequestMapping("/login")
    public String login(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "password", required = false) String password
    ) {
        return "chat/login";
    }
}
