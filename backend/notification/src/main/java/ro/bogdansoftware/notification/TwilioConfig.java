package ro.bogdansoftware.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account-sid}")
    private String accountSid;
    @Value("${twilio.auth-token}")
    private String authToken;
    @Value("${twilio.sender-number}")
    private String senderNumber;

    @Bean
    public void initializeStaticFields() {
        SMSSender.ACCOUNT_SID = accountSid;
        SMSSender.AUTH_TOKEN = authToken;
        SMSSender.SENDER_NUMBER = senderNumber;
    }
}
