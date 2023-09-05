package ro.bogdansoftware.review;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class IndividualReview {
    private String id = UUID.randomUUID().toString();
    private String username;
    private String review;
}
