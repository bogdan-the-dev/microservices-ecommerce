package ro.bogdansoftware.client_information.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
public class UserInformation {

    @Id
    private String id;
    private Long userId;
    private String firstName;
    private String lastName;
    private List<UserContact> userContacts;
    private List<String> phoneNumbers;
    private List<UserAddress> addresses;


}
