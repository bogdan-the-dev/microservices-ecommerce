package ro.bogdansoftware.notification;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.clients.notification.NotificationType;
import ro.bogdansoftware.clients.notification.SendNotificationRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("send-notification")
    public ResponseEntity<Void> sendNotification(@RequestBody SendNotificationRequest sendNotificationRequest) {
        this.notificationService.sendNotification(sendNotificationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("test")
    public void test() {
        this.sendNotification(new SendNotificationRequest("VrEaU sA mErG lA tEaTrU!!!!!", NotificationType.EMAIL, "gabirel", "Vreau la teatru", "cornicushorn@gmail.com"));
    }
}
