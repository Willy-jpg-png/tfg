package com.ggv.tfg.adapter.driving;

import com.ggv.tfg.model.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class LocationWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/location/update")
    public void updateLocation(final Location location) {
        messagingTemplate.convertAndSend("/topic/order/" + location.getOrderId(), location);
    }
}
