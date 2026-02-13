package tests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    // параметры, которые будут применяться в тестах
    String firstName;
    String lastName;
    String address;
    String metroStation;
    String phoneNumber;
    String date;
    String rentDays;
    String colorId;
    String comment;

    // конструктор
    public OrderTest(String firstName, String lastName, String address, String metroStation, String phoneNumber, String date, String rentDays, String colorId, String comment) {
        //Имя
        this.firstName = firstName;
        // Фамилия
        this.lastName = lastName;
        // адрес
        this.address = address;
        //станция Метро
        this.metroStation = metroStation;
        // номер телефона формат: 87463546374 (11-13 символов в поле, только цифры и + начинаются с 8 или +7)
        this.phoneNumber = phoneNumber;
        // дата формат: 29.11.2025
        this.date = date;
        // колличество суток (сутки/двое суток/трое суток/четверо суток/пятеро суток/шестеро суток/семеро суток)
        this.rentDays = rentDays;
        // чекбокс два цвета: серы й и черный. Формат ввода: black и grey можно выбрать два сразу
        this.colorId = colorId;
        // комментарии для курьера
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Тест {index}: заказ для {0} {1} - {7} самокат")
    // тестовые данные
    public static Collection<Object[]> getOrderTest() {
        return Arrays.asList(new Object[][]{
                {"Даниил", "Курч", "Ханты-Манты, Красная", "Спортивная", "89959590959", "12.02.2025", "двое суток", "grey", "Оставить у двери"},
                {"Анастасия", "Пак", "Астана, Фридриха Энгельса 14", "Лубянка", "+7644480882", "21.03.2026", "сутки", "black", "Будем крутить педали,пока не наваляли"},
                {"Ефросиния", "Орджаникидзе", "Тбилисси, Гамарджоба 17", "Университет", "+78005553535", "30.01.2026", "пятеро суток", "black", "Буду дома через год"}
        });
    }
    @Test
    // оформление заказа, нажатие кнопки "Заказать" в правом верхнем углу
    public void upperOrderButtonFTest() {
        // клик по кнопке Заказать
        homePage.clickFirstOrderButton();
        // метод для ввода данных в поля
        scooterOrder.completeOrderForm(firstName, lastName, address, metroStation, phoneNumber);
        //вторая страница про аренду: метод заполняет все поля и кликает кнопку "Заказать" и кнопку Да
        orderDetails.fillOrderDetailsAndSubmit(date, rentDays, colorId, comment);
        // получаем текст подтверждения
        String text = orderDetails.getOrderConfirmationText();
        assertTrue(text.contains("Заказ оформлен"));
    }
        // оформление заказа, нажатие кнопки "Заказать" внизу страницы
        @Test
        public void lowerOrderButtonTest() {
            // скролл, клик по кнопке "Заказать" внизу страницы
            homePage.clickBottomOrderButton();
            // метод для ввода данных в поля
            scooterOrder.completeOrderForm(firstName, lastName, address, metroStation, phoneNumber);
            //вторая страница про аренду: метод заполняет все поля и кликает кнопку "Заказать" и кнопку Да
            orderDetails.fillOrderDetailsAndSubmit(date, rentDays, colorId, comment);
            // получаем текст подтверждения
            String text = orderDetails.getOrderConfirmationText();
            assertTrue(text.contains("Заказ оформлен"));

        }
}