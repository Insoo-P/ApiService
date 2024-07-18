package com.insoo.project;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private static Set<WebSocketSession> sessions = new HashSet<>();
    private static Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 밑에 이 3줄은 id랑 메세지를 같이 보낼 경우 사용
        String receivedMessage = message.getPayload();
        System.out.println("Received: " + receivedMessage + " from " + session.getId());
        String jsonMessage = gson.toJson(new ChatMessage(session.getId(), receivedMessage));

        for (WebSocketSession s : sessions) {
            if (!s.getId().equals(session.getId())) {
                s.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }


    private static class ChatMessage {
        private String senderId;
        private String message;

        public ChatMessage(String senderId, String message) {
            this.senderId = senderId;
            this.message = message;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getMessage() {
            return message;
        }
    }
}