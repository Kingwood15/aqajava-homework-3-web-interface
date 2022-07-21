package ru.netology.order;

import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        //второй рабочий вариант:
        //System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestOrderCssSelector() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("span[data-test-id = 'name'] input")).sendKeys("Андрей Георгиевич");
        driver.findElement(By.cssSelector("span[data-test-id = 'phone'] input")).sendKeys("+79861234567");
        driver.findElement(By.cssSelector("span[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button[type = 'button']")).click();
        String output = driver.findElement(By.cssSelector("p[data-test-id = 'order-success']")).getText();

        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", output.trim());
    }

    @Test
    void shouldTestOrderSelenide() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Сергей Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("p[data-test-id = 'order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestOrderClassName() {
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));

        elements.get(0).sendKeys("Иван Сергеевич");
        elements.get(1).sendKeys("+75551112223");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();

        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestOrderWrongNameSelenide() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Пётр Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[class = 'input__sub']").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongPhoneSelenide() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Петр Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("+7123456789012122123123123123");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[data-test-id = 'phone'] span[class = 'input__sub']").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
