package ro.bogdansoftware.review;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class IndividualReview {
    private final String id = UUID.randomUUID().toString();
    private final String username;
    private String review;
}
