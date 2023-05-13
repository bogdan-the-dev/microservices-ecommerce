package ro.bogdansoftware.clients.notification;

public record SendNotificationRequest(
        String message,
        NotificationType type,
        String destination
) {
}
