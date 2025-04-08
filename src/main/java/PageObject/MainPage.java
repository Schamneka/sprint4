package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {


    // локатор кнопки заказа вверху
    private final By buttonOrderTop = By.xpath(".//div[starts-with(@class,'Header_Nav')]//button[text()='Заказать']");
    // локатор кнопки заказа внизу
    private final By buttonOrderBottom = By.xpath(".//div[contains(@class,'FinishButton')]//button[text()='Заказать']");
    // секция Вопросы о важном
    private final By sectionFaq = By.xpath(".//div[starts-with(@class,'Home_FAQ')]");
    // элемент секции
    private final By accordionItem = By.className("accordion__item");
    // кнопка с вопросом
    private final By accordionButton = By.className("accordion__button");
    // панель с ответом
    private final By accordionPanel = By.className("accordion__panel");
    // изображение сомаката
    private final By imageScooter = By.xpath(".//img[@alt = 'Scooter blueprint']");
    // кнопка закрытия куки
    private final By buttonAcceptCookie = By.id("rcc-confirm-button");

    private final WebDriver driver;
    // конструктор класса
    public MainPage(WebDriver driver){
        this.driver = driver;
    }


    // ждем пока прогрузятся вопросы о важном
    public void waitForLoadFaq() {
        WebElement faqElement = driver.findElement(sectionFaq);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(sectionFaq));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", faqElement);
    }

    public void waitForLoadPage() {
        WebElement imageElement = driver.findElement(imageScooter);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(imageScooter));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", imageElement);
    }

    public boolean isElementExist(By locatorBy) {
        try {
            driver.findElement(locatorBy);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    // возвращает список всех вопросов-ответов
    public List<WebElement> getFaqItems(){
        return driver.findElements(accordionItem);
    }

    public boolean isButtonClickable(WebElement faqElement) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(faqElement.findElement(accordionButton)));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
        //return faqElement.findElement(accordionButton).isEnabled();
    }

    public String getQuestion(WebElement faqElement) {
        return faqElement.findElement(accordionButton).getText();
    }

    public String getAnswer(WebElement faqElement) {
        return faqElement.findElement(accordionPanel).getText();
    }

    public void clickOrder(int indexButton) {
        switch (indexButton) {
            case 0:
                driver.findElement(buttonOrderTop).click();
                break;
            case 1:
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                WebElement buttonOrder = driver.findElement(buttonOrderBottom);
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (buttonOrder.isDisplayed()));
                buttonOrder.click();
                break;
        }
    }

    public void clickGetCookie() {
        if (isElementExist(buttonAcceptCookie))
            driver.findElement(buttonAcceptCookie).click();
    }

}