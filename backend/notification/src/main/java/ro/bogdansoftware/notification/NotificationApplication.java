package ro.bogdansoftware.notification;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ro.bogdansoftware.amqp.RabbitMQMessageProducer;
import ro.bogdansoftware.clients.notification.EmailType;

import java.time.Duration;


@SpringBootApplication(
        scanBasePackages = {
                "ro.bogdansoftware.notification",
                "ro.bogdansoftware.amqp"
        }
)
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = "ro.bogdansoftware.clients"
)
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPassword("");
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    CommandLineRunner runner(TemplateRepository repository) {
        return args -> {
          if(repository.findByNameIs(EmailType.AccountConfirmation.name()).isEmpty()) {
              Template t = Template.builder()
                      .name(EmailType.AccountConfirmation.name())
                      .content("Please use the following link to confirm your account localhost:8502/api/v1/auth/verify-account?token=")
                      .build();
              repository.save(t);
          }
          if(repository.findByNameIs(EmailType.PasswordReset.name()).isEmpty()) {
            Template t = Template.builder()
                    .name(EmailType.PasswordReset.name())
                    .content("")
                    .build();
            repository.save(t);
          }
          if(repository.findByNameIs(EmailType.Verification.name()).isEmpty()) {
              Template t = Template.builder()
                      .name(EmailType.Verification.name())
                      .content("")
                      .build();
              repository.save(t);
          }
        };
    }

//    @Bean
//    CommandLineRunner run2(RabbitMQMessageProducer producer, NotificationConfig config) {
//        return args -> {
//            producer.publish("alala", config.getInternalExchange(), config.getInternalNotificationRoutingKey());
//        };
//    }
}

