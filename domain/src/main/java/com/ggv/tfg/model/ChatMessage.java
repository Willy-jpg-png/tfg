package com.ggv.tfg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private Long orderId;
    private String sender;
    private String message;
    private String timestamp;
}
