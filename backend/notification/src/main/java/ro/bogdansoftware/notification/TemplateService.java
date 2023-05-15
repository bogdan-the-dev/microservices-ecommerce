package ro.bogdansoftware.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public boolean isTemplateExisting(String templateName) {
        return this.templateRepository.findByNameIs(templateName).isPresent();
    }

    public Template getTemplate(String name) {
        return this.templateRepository.findByNameIs(name).orElseThrow(() -> new RuntimeException("Template not preset"));
    }

    public void updateTemplate(Template updateVersion) {
        Template template = this.getTemplate(updateVersion.name);
        template.content = updateVersion.content;
        this.templateRepository.save(template);
    }

    public void createTemplate(Template template) {
        this.templateRepository.save(template);
    }
}
