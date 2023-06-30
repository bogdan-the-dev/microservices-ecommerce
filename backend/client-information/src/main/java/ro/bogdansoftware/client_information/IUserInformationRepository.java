package ro.bogdansoftware.client_information;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.bogdansoftware.client_information.model.UserInformation;

public interface IUserInformationRepository extends MongoRepository<UserInformation, String> {

}
