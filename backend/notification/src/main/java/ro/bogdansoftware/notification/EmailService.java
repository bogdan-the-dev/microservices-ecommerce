package ro.bogdansoftware.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String email, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setSubject(subject);
            helper.setText(email, true);
            helper.setTo(to);
            helper.setFrom("everything.online.office@gmail.com");
            mailSender.send(mimeMessage);
        }catch (MessagingException e) {
            throw new IllegalStateException("Failed to send mail");
        }
    }
}
