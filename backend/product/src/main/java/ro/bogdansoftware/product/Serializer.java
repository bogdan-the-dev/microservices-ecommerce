package ro.bogdansoftware.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Serializer {
    public static String serializeSpecs(Map<String, Map<String, String>> specs) {
        String json = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(specs);

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Map<String, Map<String, String>> deserializeSpecs(String json) {
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            resultMap = objectMapper.readValue(json, new TypeReference<Map<String, Map<String, String>>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
