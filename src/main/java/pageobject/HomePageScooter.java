package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// класс домашней страницы самоката
public class HomePageScooter {
    private final WebDriver driver;

    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
    }
    // кнопка принятия куков
    private final By cookieAcceptButton = By.id("rcc-confirm-button");
    // кнопка Заказать в правом верхнем углу страницы
    private final By firstOrderButton = By.className("Button_Button__ra12g");
    // кнопка Заказать после скролла страницы внизу
    private final By secondBottomOrderButton = By.className("Button_Middle__1CSJM");
    // вопросы о важном - аккордеон (заголовок)
    public By accordionButton(int Index) {
        return By.id("accordion__heading-" + Index);
    }
    // вопросы о важном - аккордеон (ответ)
    private By answerAccordion(int Index) {
        return By.id("accordion__panel-" + Index);
    }
    // метод закрытия баннера с куками
    public void acceptCookies() {
        // если баннера нет, просто выходим
        if (driver.findElements(cookieAcceptButton).isEmpty()) {
            return;
        }

        WebElement button = driver.findElement(cookieAcceptButton);
        // клик
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }


    public void clickFirstOrderButton() {
        driver.findElement(firstOrderButton).click();
    }

    // метод клик на кнопку заказать внизу страницы
    public void clickBottomOrderButton() {
        scrollToElement(secondBottomOrderButton);
        driver.findElement(secondBottomOrderButton).click();
    }

    // метод скролл до элемента
    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    // Клик по вопросу
    public void clickAccordionButton(int index) {
        By btn = accordionButton(index);
        scrollToElement(btn);
        driver.findElement(accordionButton(index));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(accordionButton(index)))
                .click();
    }

    //  Получение текста ответа
    public String getAccordionAnswerText(int index) {
        WebElement answer = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(answerAccordion(index)));
        return answer.getText();
    }
}



