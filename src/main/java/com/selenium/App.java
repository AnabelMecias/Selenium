package com.selenium;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ebay.com");
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys("iphone");
        WebElement searchButton = driver.findElement(By.id("gh-btn"));
        searchButton.click();

        WebElement webElement = driver.findElement(By.className("srp-results"));
        List<WebElement> liElements = webElement.findElements(By.className("s-item"));

        StringBuilder productDetails = new StringBuilder();

        for (WebElement li : liElements) {
            String description ="Product Description: "+li.findElement(By.className("s-item__title")).getText();
            String price = "Price: "+li.findElement(By.className("s-item__price")).getText();
            System.out.println(description);
            System.out.println(price);
            System.out.println();
            productDetails.append(description).append("\n").append(price).append("\n\n");
            saveToTextFile(productDetails.toString());      
        }
        driver.close();

        driver.get("https://www.target.com/");
        WebElement searchBox2 = driver.findElement(By.id("search"));
        searchBox2.sendKeys("iphone");
        WebElement searchButton2 = driver.findElement(By.className("kAYQBc"));
        searchButton2.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        List<WebElement> prices = driver.findElements(By.cssSelector("span[data-test='current-price']"));
        List<WebElement> products = driver.findElements(By.cssSelector("a[data-test='product-title']"));

        StringBuilder productDetails2 = new StringBuilder();

        for (int i = 0; i < products.size(); i++) {
            String description2 ="Product Description: "+products.get(i).getText();
            String price2 = "Price: "+prices.get(i).getText();
            System.out.println(description2);
            System.out.println(price2);
            System.out.println();
            productDetails2.append(description2).append("\n").append(price2).append("\n\n");
            saveToTextFile2(productDetails2.toString());  
        }
        driver.close();
    }

    private static void saveToTextFile(String data) {
        try {
            FileWriter writer = new FileWriter("ebay_products.txt");
            writer.write(data);
            writer.close();
            System.out.println("Product details saved to ebay_products.txt");
        } catch (IOException e) {
            System.err.println("Error occurred while saving the product details to the text file.");
            e.printStackTrace();
        }
    }

    private static void saveToTextFile2(String data) {
        try {
            FileWriter writer = new FileWriter("target_products.txt");
            writer.write(data);
            writer.close();
            System.out.println("Product details saved to target_products.txt");
        } catch (IOException e) {
            System.err.println("Error occurred while saving the product details to the text file.");
            e.printStackTrace();
        }
    }
}
