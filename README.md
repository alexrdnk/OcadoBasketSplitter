# Ocado Projekt Java Wroclaw (Basket Splitter Application)

## What task has been set?
My task was to create a library that divides the items in the customer's basket into delivery groups.
Ocado had been already defined an API for Basket Splitter Class:
```java
package com.ocado.basket;
import java.util.List;
import java.util.Map;

public class BasketSplitter {

  public BasketSplitter(String absolutePathToConfigFile) {
  /* ... */
  }

  public Map<String, List<String>> split(List<String> items) {
  /* ... */
  }

/* ... */
}
```

In order to work correctly, the library must load a configuration file `config.json` that contains the possible delivery methods for all the products offered in the shop.

## Expected result
The algorithm as a result is supposed to return a division of products into delivery groups as a map.
Its key will be the delivery method and the value will be the list of products.
<br/>Algorithm requirements:
- Divides the products into the minimum possible number of delivery groups.
- The largest group contains as many products as possible

`Example of output:`
```java
Delivery method: Pick-up point
  Product: Fond - Chocolate

Delivery method: Courier
  Product: Cocoa Butter
  Product: Tart - Raisin And Pecan
  Product: Flower - Daisies
```

## Project structure
At first, I decided to make my project using JAVA 21 and Gradle 8.5.
<br/>`The structure of my project looks like:`
```java
- .gradle
- .idea
- build
  - ...
  - libs 
    - **Our library** 
  - ...
- gradle
- src 
  - *main* 
    - java
      - com.ocado.basket
        - BasketSplitter
        - ExceptionProductNotFound
        - Main
    - resources
      - config.json
      - basket-1.json
      - basket-2.json
  - *test*
    - java
      - com.ocado.basket
        -BasketSplitterTest
    - resources
      - config.json
      - basket-1.json
      - basket-2.json
  - .gitignore
  - build.gradle
  - gradlew
  - gradlew.bat
  - README.md
  - settings.gradle
```

## Programme functionality
The main thing in project is `BasketSplitter` class.
<br/>This class takes a list of product names and a configuration file path as input. The configuration file maps each product to its available delivery methods.
<br/>The split method then groups these products into baskets based on their shared delivery options. It iterates through the product list, finding the delivery method with the most occurrences.
<br/>Then, it builds a basket containing products that can be delivered using that method and removes them from the main list. This process continues until all products are assigned to a basket based on their compatible delivery options.

My `Main` class reads a `basket-1.json` file and a `config.json` file. After that, using `BasketSplitter` *method*, it splits our basket list of products based on configuration file. Its output is a map where each key represents a delivery method and the corresponding value is a list of products that can be delivered using that method.

## Tests
One of the main things in getting a programme to work properly is testing.
<br/>I decided to test one of the most important things in the code, so I created `BasketSplitterTest` class.
<br/>I made tests for:
- **Splitting a basket:** We test splitting a basket into designated deliveries, like "Pick-up point" and "Courier," based on configuration file.
- **Conquering complex baskets:** No basket is too intricate! We test with baskets containing a mix of same-day delivery, courier items, and express collection.
- **Empty baskets:** Our tests verify BasketSplitter gracefully handles empty lists.
- **Vanquishing incorrect items:** Adding an "Incorrect product" to the basket? BasketSplitter will identify it and throw an exception.

## Made using
- Gradle 8.5
- Java 21
- JetBrains IntellijIDEA
- [Jackson (The JAVA JSON library)](https://github.com/FasterXML/jackson/blob/master/README.md)

## Start application
At first you need to open terminal.
<br/>We build our project using `./gradlew build` command.
<br/>To run our application, we have runApplication task in `build.gradle`. So just type `./gradlew runApplication` in command line.
<br/>You can test program with `./gradlew test`

## Making .JAR file
That`s simple thing!!
You need to use `./gradlew clean` command and then use `./gradle jar` to make library.
Congratulations!

### Made by Oleksandr Radionenko
