package ro.bogdansoftware.notification;

public interface EmailSender {
    void send(String to, String email, String subject);
}
