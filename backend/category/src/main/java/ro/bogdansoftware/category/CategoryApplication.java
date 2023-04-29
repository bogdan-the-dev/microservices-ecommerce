package ro.bogdansoftware.category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ro.bogdansoftware.category.model.Category;

import java.util.ArrayList;

@SpringBootApplication
@EnableDiscoveryClient
public class CategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class, args);
    }



    @Bean
    CommandLineRunner runner(CategoryRepository repository, MongoTemplate mongoTemplate) {
        return args -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is("Uncategorized"));
            if(!mongoTemplate.exists(query, Category.class)) {
                repository.insert(Category
                        .builder()
                        .name("Uncategorized")
                        .subcategories(new ArrayList<>())
                        .build());
            }
        };
    }
}
