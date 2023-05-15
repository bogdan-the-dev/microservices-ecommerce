package ro.bogdansoftware.notification;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Template")
@Builder
public class Template implements Serializable {
    @Id
    String name;
    String content;
}
