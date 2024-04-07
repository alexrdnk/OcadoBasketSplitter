package com.ocado.basket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BasketSplitter {
    private final String configFilePath;

    public BasketSplitter(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, Integer> deliveryOccurrences = new HashMap<>();
        Map<String, List<String>> finalDelivery = new HashMap<>();


        Map<String, List<String>> itemDeliveryMethods = new HashMap<>();
        for (String item : items) {
            List<String> deliveryMethods = getDeliveryMethods(item);
            if (deliveryMethods.isEmpty()) {
                throw new ExceptionProductNotFound("Product not found: " + item);
            }
            itemDeliveryMethods.put(item, deliveryMethods);
            deliveryMethods.forEach(option -> deliveryOccurrences.merge(option, 1, Integer::sum));
        }


        while (!items.isEmpty()) {
            String bestDelivery = Collections.max(deliveryOccurrences.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            List<String> products = new ArrayList<>();

            items.removeIf(item -> {
                List<String> itemDeliveryMethodsList = itemDeliveryMethods.get(item);
                if (itemDeliveryMethodsList.contains(bestDelivery)) {
                    products.add(item);
                    itemDeliveryMethodsList.forEach(option -> deliveryOccurrences.merge(option, -1, Integer::sum));
                    return true;
                }
                return false;
            });

            finalDelivery.put(bestDelivery, products);
            deliveryOccurrences.remove(bestDelivery);
        }


        return finalDelivery;
    }


    public List<String> getDeliveryMethods(String nameOfProduct) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> deliveryOptions = new ArrayList<>();

        try {
            JsonNode rootNode = mapper.readTree(new File(configFilePath));
            JsonNode deliveryOptNode = rootNode.get(nameOfProduct);

            if (deliveryOptNode != null && deliveryOptNode.isArray()) {
                deliveryOptNode.forEach(optionNode -> deliveryOptions.add(optionNode.asText()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return deliveryOptions;
    }
}
