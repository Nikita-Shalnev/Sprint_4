package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.*;

import java.time.Duration;


public class OrderDetailsPage {
    private final WebDriver driver;

    public OrderDetailsPage(WebDriver driver) {
        this.driver = driver;
    }
    //  локатор поля когда привезти
    private final By dateField = By.cssSelector("input.Input_Input__1iN_Z.Input_Responsible__1jDKN");
    // локатор кнопки срок аренды
    private final By rentalDropdown = By.className("Dropdown-placeholder");
    // локатор появляющегося списка вариантов срока аренды
    private By rentalPeriodOption(String rentDays) {
        return By.xpath("//div[contains(@class,'Dropdown-option') and text()='" + rentDays + "']");
    }

    // локатор чек боксов цвет самоката
    private By checkboxScooterColor(String colorId) {
        return By.id(colorId);
    }
    //локатор коментарий для курьера
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // локатор кнопки Заказать
    private final By orderButton = By.xpath("//div[contains(@class,'Order_Buttons__1xGrp')]//button[normalize-space()='Заказать']");
    // локатор для кнопки подтверждения заказа Да
    private final By yesButton = By.xpath("//button[text()='Да']");
    // локатор для всплывающего окна с сообщением об успешном создании заказа
    private final By orderConfirmationHeader = By.xpath("//div[contains(@class,'Order_ModalHeader__') and contains(text(),'Заказ оформлен')]");
    // метод для ввода выбора даты аренды (когда привезти самокат)
    public void setDate(String date) {
        driver.findElement(dateField).clear();
        driver.findElement(dateField).sendKeys(date);
        // закрываем календарь
        driver.findElement(dateField).sendKeys(Keys.ENTER);

    }

    // метод для выбора количества дней(суток) из выпадающего списка (срок аренды)
    public void selectRentalPeriod(String rentDays) {
        driver.findElement(rentalDropdown).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodOption(rentDays)));
        driver.findElement(rentalPeriodOption(rentDays)).click();
    }
    // метод для выбора чекбокса (цвет самоката)
    public void selectCheckbox(String colorId) {
        driver.findElement(checkboxScooterColor(colorId)).click();
    }
    // метод написать комментарий (комментарий для курьера)
    public void enterComment(String comment) {
        driver.findElement(commentField).click();
        driver.findElement(commentField).sendKeys(comment);
    }
    // метод скролл
    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(false);",
                element
        );
    }
    // метод клик по кнопке Заказать
    public void clickOrderButton() {
        WebElement btn = driver.findElement(orderButton);
        scrollTo(btn);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(btn));
        btn.click();
    }

    // метод клик по кнопке Да
    public void clickYesButton() {
            // ожидание модального окна
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(yesButton));
        // клик по кнопке Да
        driver.findElement(yesButton).click();
        }

    // метод для выполнения действий со всеми полями класса и клик на кнопку Заказать, Да, проверка подтверждения
    public void fillOrderDetailsAndSubmit(String date, String rentDays, String colorId, String comment) {
            setDate(date);
            selectRentalPeriod(rentDays);
            selectCheckbox(colorId);
            enterComment(comment);
            clickOrderButton();
            clickYesButton();
    }
    //метод возвращает текст подтверждения
    public String getOrderConfirmationText() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationHeader));

        return driver.findElement(orderConfirmationHeader).getText();
    }

}

