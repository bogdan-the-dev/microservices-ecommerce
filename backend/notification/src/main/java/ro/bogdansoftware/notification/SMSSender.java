package ro.bogdansoftware.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

public class SMSSender {
    @Value("${twilio.account-sid}")
    public static String ACCOUNT_SID;
    @Value("${twilio.auth-token}")
    public static String AUTH_TOKEN;
    @Value("${twilio.sender-number}")
    public static String SENDER_NUMBER;

    public static void sendSMS(String destinationNumber, String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(destinationNumber), new PhoneNumber(SENDER_NUMBER), text).create();
    }
}
