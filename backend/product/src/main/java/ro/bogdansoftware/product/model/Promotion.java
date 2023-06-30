package ro.bogdansoftware.product.model;

import java.math.BigDecimal;
import java.util.Date;

public record Promotion(String id, String name, BigDecimal percentage) {
}
