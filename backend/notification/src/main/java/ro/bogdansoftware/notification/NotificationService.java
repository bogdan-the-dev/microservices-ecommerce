package ro.bogdansoftware.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.notification.SendNotificationRequest;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailService emailService;

    public void sendNotification(SendNotificationRequest request) {
        switch (request.type()) {

            case CONFIRMATION_EMAIL -> {
                String message = "Please use the following link to confirm your account localhost:8502/api/v1/auth/verify-account?token=";
                message+=request.message();
                emailService.send(request.destination(), message, request.subject());
            }
            case PASSWORD_RESET -> {
                String message = "Please use the following link to reset your password localhost:8502/api/v1/auth/verify-account?token=";
                message+=request.message();
                emailService.send(request.destination(), message, request.subject());
            }
            case INFO_EMAIL -> {
                emailService.send(request.destination(), request.message(), request.subject());
            }
            case SMS -> {
                SMSSender.sendSMS(request.destination(), request.message());
                log.info("SMS sent to " + request.destination());
            }
        }
    }
}
