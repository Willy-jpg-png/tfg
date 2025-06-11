package com.ggv.tfg.adapter.driving;

import com.ggv.tfg.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload final ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(
                "/topic/chat/" + chatMessage.getOrderId(),
                chatMessage
        );
    }
}
