package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Notification;
import com.fastporte.fastportewebservice.service.INotificationService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
@Api(tags="Notification", value="Web Service RESTful of Notifications")
public class NotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

}
