package ro.bogdansoftware.product.model;

import java.math.BigDecimal;
import java.util.Date;

public record Promotion(String id, String name, String description, BigDecimal discount_rate, Date start_Date, Date end_date) {
}
