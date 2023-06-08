package ro.bogdansoftware.notification.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.bogdansoftware.clients.notification.SendNotificationRequest;
import ro.bogdansoftware.notification.NotificationService;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(SendNotificationRequest request) {
        log.info("Consumed {} from queue", request);
        notificationService.sendNotification(request);
    }

}
