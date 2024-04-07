package com.ocado.basket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {
    private BasketSplitter basketSplitter;
    private List<String> firstBasket;
    private List<String> secondBasket;
    private List<String> incorrectBasket;

    @Test
    @BeforeEach
    void setUpBasket1() throws IOException {
        basketSplitter = new BasketSplitter("src/test/resources/config.json");
        byte[] jsonData = Files.readAllBytes(Paths.get("src/test/resources/basket-1.json"));
        firstBasket = Main.parse(jsonData);
        Assertions.assertNotNull(basketSplitter);
        Assertions.assertNotNull(firstBasket);
    }

    @Test
    @BeforeEach
    void setUpBasket2() throws IOException {
        basketSplitter = new BasketSplitter("src/test/resources/config.json");
        byte[] jsonData = Files.readAllBytes(Paths.get("src/test/resources/basket-2.json"));
        secondBasket = Main.parse(jsonData);
        Assertions.assertNotNull(basketSplitter);
        Assertions.assertNotNull(secondBasket);
    }

    @Test
    void testSplitBasket1() {

        Map<String, List<String>> finalDelivery = basketSplitter.split(firstBasket);

        assertEquals(2, finalDelivery.size());
        assertEquals(List.of("Fond - Chocolate"), finalDelivery.get("Pick-up point"));
        assertEquals(Arrays.asList(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Cookies - Englishbay Wht"), finalDelivery.get("Courier"));
    }

    @Test
    void testSplitBasket2() {

        Map<String, List<String>> finalDelivery = basketSplitter.split(secondBasket);

        assertEquals(3, finalDelivery.size());
        assertEquals(Arrays.asList("Sauce - Mint", "Numi - Assorted Teas", "Garlic - Peeled"), finalDelivery.get("Same day delivery"));
        assertEquals(List.of("Cake - Miini Cheesecake Cherry"), finalDelivery.get("Courier"));
        assertEquals(Arrays.asList(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen",
                "Longan",
                "Bag Clear 10 Lb",
                "Nantucket - Pomegranate Pear",
                "Puree - Strawberry",
                "Apples - Spartan",
                "Cabbage - Nappa",
                "Bagel - Whole White Sesame",
                "Tea - Apple Green Tea"), finalDelivery.get("Express Collection"));
    }

    @Test
    void emptyItemListTest() {
        List<String> emptyBasket = Collections.emptyList();
        Map<String, List<String>> finalDelivery = basketSplitter.split(emptyBasket);
        assertEquals(0, finalDelivery.size());
    }

    @Test
    void incorrectItemsTest() {
        incorrectBasket = List.of(
                "Fond - Chocolate",
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Incorrect product");

        String incorrectProduct = "Incorrect product";

        Exception exception = assertThrows(ExceptionProductNotFound.class, () ->
                basketSplitter.split(incorrectBasket));

        System.out.println(exception.getMessage());

        assertEquals("Product not found: " + incorrectProduct, exception.getMessage());
    }

}
