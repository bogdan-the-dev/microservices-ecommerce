package ro.bogdansoftware.clients.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewProductPreview {
    private List<Double> ratings = new LinkedList<>();
    private List<Integer> numberOfRatings = new LinkedList<>();

}
