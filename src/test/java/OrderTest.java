import PageObject.MainPage;
import PageObject.OrderPage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;

@RunWith(Parameterized.class)
public class OrderTest {
    public static WebDriver driver;
    public static MainPage objMainPage;
    public OrderPage objOrderPage;
    private final int indexButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String dateOrder;
    private final String period;
    private final String color;
    private final String comment;

    public OrderTest(int indexButton, String name, String surname, String address, String metro, String phone, String dateOrder, String period, String color, String comment) {
        this.indexButton = indexButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.dateOrder = dateOrder;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }

    @Parameters(
            name = "Оформление заказа: Способ вызова: {0}; Имя: {1}; Фамилия: {2}; Адрес: {3}; Метро: {4}; Телефон: {5}; Когда нужен: {6}; Срок аренды: {7}; Цвет: {8}; Комментарий: {9}"
    )
    public static Object[][] getTestData() {
        return new Object[][]{{0, "Кирилл", "Шамне", "Челябинск", "Выставочная", "+79193548019", "07.04.2025", "трое суток", "grey", "Проверка 1"}, {1, "Пупкина", "Мария", "Москва", "Беговая", "+79787272782", "05.05.2025", "сутки", "black", "Проверка 2"}};
    }

    @BeforeClass
    public static void initialOrder() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String[]{"--remote-allow-origins=*"});
        driver = new ChromeDriver(options);
        driver = (new Augmenter()).augment(driver);
    }

    @Test
    public void testOrder() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        objMainPage = new MainPage(driver);
        objMainPage.waitForLoadPage();
        objMainPage.clickGetCookie();
        objMainPage.clickOrder(this.indexButton);
        this.objOrderPage = new OrderPage(driver);
        this.objOrderPage.waitForLoadOrderPage();
        this.objOrderPage.setDataFieldsAndClickNext(this.name, this.surname, this.address, this.metro, this.phone);
        this.objOrderPage.waitForLoadRentPage();
        this.objOrderPage.setOtherFieldsAndClickOrder(this.dateOrder, this.period, this.color, this.comment);
        Assert.assertTrue("Отсутствует сообщение об успешном завершении заказа", objMainPage.isElementExist(this.objOrderPage.orderPlaced));
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }
}