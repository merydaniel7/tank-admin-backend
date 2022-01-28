package com.tank.tankadminbackend.services.marketingcost;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ArgepServiceWebDriver {
    private float sumAdCoast;
    private final String shop;
    private final String userName;
    private final String password;

    public ArgepServiceWebDriver(String shop, String userName, String password) {
        this.shop = shop;
        this.userName = userName;
        this.password = password;
        this.sumAdCoast = 0;
    }


   private WebDriver getDriver() {
        String exePath = "C:\\Windows\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }


    private void getCost(WebDriver driver, String date) {
        WebElement table = driver.findElement(By.className("clicksAndVisitDetails"));
        List<WebElement> dateRows =  table.findElements(By.tagName("tr"));
        for (WebElement row : dateRows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (Objects.equals(columns.get(1).getText(), date)) {
                String cost = columns.get(3).getText().replaceAll("\\D+","");
                sumAdCoast += Integer.parseInt(cost);
                break;
            }
        }
    }


    public float getSumOfAdCost(LocalDate date) {
        List<Integer> indices = new ArrayList<>();
        WebDriver driver = getDriver();
        driver.get("https://stats.guenstiger.de/p01default.aspx?lan=HU");
        List<WebElement> loginFields =  driver.findElements(By.className("tablerow"));
        WebElement userName = loginFields.get(0).findElement(By.tagName("input"));
        WebElement password = loginFields.get(1).findElement(By.tagName("input"));
        userName.sendKeys(this.userName);
        password.sendKeys(this.password);

        WebElement loginButton = driver.findElements(By.className("tableheader")).get(2).findElement(By.tagName("input"));
        loginButton.click();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        String yesterday = formatter.format(date);

        int year = date.getYear();
        int month = date.getMonth().getValue();

        driver.get("https://stats.guenstiger.de/p02main.aspx?view=stat&ltype=M&year="+year+"&month="+month);

        Select dropdown = new Select(driver.findElement(By.tagName("select")));
        List<WebElement> elements = dropdown.getOptions();
        int index = 0;
        for (WebElement element : elements) {
            if (element.getText().contains(shop)) {
                indices.add(index);
            }
            index++;
        }

        for (int i : indices) {
            dropdown = new Select(driver.findElement(By.tagName("select")));
            elements = dropdown.getOptions();
            elements.get(i).click();
            getCost(driver, yesterday);
        }

        return sumAdCoast;
    }
}
