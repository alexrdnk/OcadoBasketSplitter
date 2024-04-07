package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String absolutePathToConfigFile = "src/main/resources/basket-2.json";
        byte[] jsonData = Files.readAllBytes(Paths.get(absolutePathToConfigFile));
        List<String> firstBasket = parse(jsonData);

        BasketSplitter basketSplitter = new BasketSplitter("src/main/resources/config.json");
        Map <String, List<String>> finalDelivery = basketSplitter.split(firstBasket);


        for (Map.Entry<String, List<String>> entry : finalDelivery.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            System.out.println("Delivery method: " + key);

            for (String value : values) {
                System.out.println("  Product: " + value);
            }
            System.out.println();
        }

    }

    public static List<String> parse(byte[] jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, new TypeReference<>() {});
    }
}