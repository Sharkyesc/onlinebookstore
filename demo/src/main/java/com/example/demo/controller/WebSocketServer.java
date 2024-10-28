package com.example.demo.controller;

import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/topic/order-status/{username}")
@Component
public class WebSocketServer {

    public WebSocketServer() {
        System.out.println("新的连接");
    }

    private static final AtomicInteger COUNT = new AtomicInteger();

    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public void sendMessage(Session toSession, String message) {
        if (toSession != null) {
            try {
                toSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("用户未登录");
        }
    }

    public void sendMessageToUser(String username, String message) {
        System.out.println(username);
        Session toSession = SESSIONS.get(username);
        sendMessage(toSession, message);
        System.out.println(message);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("服务器收到消息：" + message);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        if (SESSIONS.get(username) != null) {
            System.out.println("已经登录");
            return;
        }
        SESSIONS.put(username.trim(), session);
        COUNT.incrementAndGet();
        System.out.println(username + "上线了，当前在线人数：" + COUNT);

    }

    @OnClose
    public void onClose(@PathParam("username") String username) {
        SESSIONS.remove(username);
        COUNT.decrementAndGet();
        System.out.println(username + "下线了，当前在线人数：" + COUNT);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
}
