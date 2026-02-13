package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// класс страницы заказа самоката
public class ScooterOrderPage {
    public WebDriver driver;
    // локатор поле ввода Имя
    private By fieldName = By.cssSelector("input[placeholder='* Имя']");
    // локатор сообщение об ошибке поле ввода Имя
    private By fieldNameErrorMessage = By.xpath("//input[@placeholder='* Имя']/following-sibling::div[contains(@class,'Input_ErrorMessage')]");
    // локатор поле ввода Фамилия
    private By fieldSurname = By.cssSelector("input[placeholder='* Фамилия']");
    // локатор сообщение об ошибке поле Фамилия
    private By fieldSurnameErrorMessage = By.xpath("//input[@placeholder='* Фамилия']/ancestor::div[contains(@class,'Input_InputContainer')]" +
            "//div[contains(@class,'Input_ErrorMessage')]");
    // локатор поле ввода Адрес
    private By fieldAddress = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    // локатор сообщение об ошибке поле Адрес
    private By fieldAddressErrorMessage = By.xpath("//div[contains(text(),'Введите корректный адрес')]");
    // локатор выпадающий список Станция метро
    private By dropdownListMetroStation = By.cssSelector("input[placeholder='* Станция метро']");
    // локатор сообщение об ошибке поле Станция метро
    private By dropdownListMetroStationErrorMessage = By.xpath("//div[contains(text(),'Выберите станцию')]");
    // динамический локатор выбор из выпадающего списка станции метро
    private By metroStationSelection(String stationName) {
        return By.xpath("//li[contains(@class,'select-search__row')]//button[normalize-space()='" + stationName + "']"
            );
}

        // локатор Поле ввода Телефон
        private By fieldPhoneNumber = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");

        // локатор сообщение об ошибке поле Телефон
        private By fieldPhoneNumberErrorMessage = By.xpath("//div[contains(text(),'Введите корректный номер')]");

        // локатор кнопка Далее
        private By nextButton = By.xpath("//button[text()='Далее']");

        // локатор иконка Самокат (дополнительное задание)
        private By iconScooter = By.className("Header_LogoScooter__3lsAR");

        // локатор надписи Для кого самокат для того чтобы убрать фокус с поля
        private By whoIsTheScooterFor = By.xpath("//div[contains(@class,'Order_Header') and text()='Для кого самокат']");

        // конструктор
        public ScooterOrderPage(WebDriver driver) {
            this.driver = driver;
        }

        // метод ввести Имя
        // ОТДЕЛЬНЫЕ методы для каждого поля (для негативных тестов)
        public void setFirstName(String firstName) {
            WebElement element = driver.findElement(fieldName);
            element.clear();
            element.sendKeys(firstName);
            // кликаем на другое поле чтобы вызвать валидацию
            driver.findElement(fieldSurname).click();
        }

        // метод ввести Фамилию
        public void setLastName(String lastName) {
            driver.findElement(fieldSurname).clear();
            driver.findElement(fieldSurname).sendKeys(lastName);
        }

        // метод ввести Адрес
        public void setAddress(String address) {
            driver.findElement(fieldAddress).clear();
            driver.findElement(fieldAddress).sendKeys(address);
        }

        // метод выбора станции из выпадающего списка
        public void selectMetroStation(String stationName) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // ждём, пока поле станет кликабельным и скроллим к нему
            WebElement metroInput = wait.until(
                    ExpectedConditions.elementToBeClickable(dropdownListMetroStation)
            );
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", metroInput);

            // кликаем по полю и вводим текст станции
            metroInput.click();
            metroInput.clear();
            metroInput.sendKeys(stationName);

            // ждём появление нужной строки в списке и кликаем по ней
            By optionLocator = By.xpath(
                    "//div[contains(@class,'select-search__select')]//button[normalize-space()='" + stationName + "']"
            );

            WebElement option = wait.until(
                    ExpectedConditions.elementToBeClickable(optionLocator)
            );
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", option);

            option.click();
        }

        // метод ввести Телефон
        public void setPhone(String phoneNumber) {
            driver.findElement(fieldPhoneNumber).clear();
            driver.findElement(fieldPhoneNumber).sendKeys(phoneNumber);
        }

        // метод клик по кнопке далее
        public void clickNextButton() {
            driver.findElement(nextButton).click();
        }

        // метод клик на надпись Для кого самокат чтоб убрать фокус с поля
        public void unfocus() {
            driver.findElement(whoIsTheScooterFor).click();
        }

        //методы Ошибки для всех полей формы заказа.fieldNameErrorMessage
        private String getErrorText(By locator) {
            List<WebElement> errors = driver.findElements(locator);
            return errors.isEmpty() ? "" : errors.get(0).getText();
        }

        public String getFirstNameErrorText() {
            return getErrorText(fieldNameErrorMessage);
        }

        public String getLastNameErrorText() {
            return getErrorText(fieldSurnameErrorMessage);
        }

        public String getAddressErrorText() {
            return driver.findElement(fieldAddressErrorMessage).getText();
        }

        public String getMetroErrorText() {
            return getErrorText(dropdownListMetroStationErrorMessage);
        }

        public String getPhoneErrorText() {
            return driver.findElement(fieldPhoneNumberErrorMessage).getText();
        }


        // метод ждет загрузки страницы, заполняет поля ввода, выбирает из выпадающего списка станцию метро и нажимает на кнопку далее
        public void completeOrderForm(
                String firstName,
                String lastName,
                String address,
                String metroStation,
                String phoneNumber
        )

        {     // поле имя
            driver.findElement(fieldName).sendKeys(firstName);
            // поле фамилия
            driver.findElement(fieldSurname).sendKeys(lastName);
            //поле адрес
            driver.findElement(fieldAddress).sendKeys(address);
            // вызов метода выбора метро
            selectMetroStation(metroStation);
            // поле номер телефона
            driver.findElement(fieldPhoneNumber).sendKeys(phoneNumber);
            // клик по кнопке Далее
            driver.findElement(nextButton).click();
        }

        // метод для клика на иконку Самокат
        public void clickIconScooter() {
            driver.findElement(iconScooter).click();
        }
}
