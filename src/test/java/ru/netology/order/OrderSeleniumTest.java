package ru.netology.order;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderSeleniumTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldPositiveTestOrder() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Сергеевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+75551112223");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее" +
                " время.", text.trim());
    }

    @Test
    void shouldPositiveTwinNameTestOrder() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Максим-Иван Андреевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее" +
                " время.", text.trim());
    }

    @Test
    void shouldTestOrderWrongNameSymbols() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(";!%№!*:();!№");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы" +
                " и дефисы.", text.trim());
    }

    @Test
    void shouldTestOrderWrongNameFromNumbers() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("1234 12345");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы" +
                " и дефисы.", text.trim());
    }

    @Test
    void shouldTestOrderWrongNameFromLatin() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Peter Sergeevych");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы" +
                " и дефисы.", text.trim());
    }

    @Test
    void shouldTestOrderWrongEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestOrderWrongNameDescriptionNotPassport() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван");
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();

        Assertions.assertEquals("Укажите точно как в паспорте", text.trim());
    }

    @Test
    void shouldTestOrderWrongPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петр Андреевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+712345678901212212312");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."
                , text.trim());
    }

    @Test
    void shouldTestOrderWrongPhoneFromOneNumber() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петр Андреевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("1");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."
                , text.trim());
    }

    @Test
    void shouldTestOrderWrongPhoneFromText() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петр Андреевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("номер телефона");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."
                , text.trim());
    }

    @Test
    void shouldTestOrderWrongEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петр Андреевич");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestOrderPhoneDescriptionSendSms() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("номер телефона");
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();

        Assertions.assertEquals("На указанный номер моб. тел. будет отправлен смс-код для подтверждения" +
                " заявки на карту. Проверьте, что номер ваш и введен корректно.", text.trim());
    }

    @Test
    void shouldTestOrderWrongNotClickCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Сергеевич");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+75551112223");
        driver.findElement(By.cssSelector("[type = 'button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).getText();

        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}
