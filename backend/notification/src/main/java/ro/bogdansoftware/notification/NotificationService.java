package ro.bogdansoftware.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.notification.SendNotificationRequest;

@Service
@AllArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    public void sendNotification(SendNotificationRequest request) {
        switch (request.type()) {

            case EMAIL -> {
                emailService.send(request.destination(), request.message(), request.subject());
                break;
            }
            case SMS -> {
                //TODO integrate with twilio
            }
        }
    }
}
