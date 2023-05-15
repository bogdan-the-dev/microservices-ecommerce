package ro.bogdansoftware.notification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
    Optional<Template> findByNameIs(String name);
}
