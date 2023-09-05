package ro.bogdansoftware.product.model;

import java.math.BigDecimal;

public record Promotion(String id, String name, BigDecimal percentage) {
}
