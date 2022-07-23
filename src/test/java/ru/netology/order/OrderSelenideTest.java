package ru.netology.order;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderSelenideTest {

    @Test
    void shouldPositiveTestOrder() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Сергей Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("p[data-test-id = 'order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш" +
                " менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestOrderWrongName() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Пётр Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[class = 'input__sub']").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы" +
                " только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongNameFromNumbers() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("1234 12345");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[class = 'input__sub']").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы" +
                " только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongNameFromLatin() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Peter Sergeevych");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[class = 'input__sub']").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы" +
                " только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongEmptyName() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[class = 'input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestOrderWrongNameNotPassport() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Иван");

        $("span[class = 'input__sub']").shouldHave(exactText("Укажите точно как в паспорте"));
    }

    @Test
    void shouldTestOrderWrongPhone() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Петр Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("+7123456789012122123123123123");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[data-test-id = 'phone'] span[class = 'input__sub']").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestOrderWrongPhoneFromOneNumber() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Петр Андреевич");
        form.$("span[data-test-id = 'phone'] input").setValue("1");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[data-test-id = 'phone'] span[class = 'input__sub']").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestOrderWrongPhoneFromText() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Иван Сергеевич");
        form.$("span[data-test-id = 'phone'] input").setValue("номер телефона");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[data-test-id = 'phone'] span[class = 'input__sub']").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestOrderWrongEmptyPhone() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Семен Свалов");
        //form.$("span[data-test-id = 'phone'] input").setValue("номер телефона");
        form.$("span[class = 'checkbox__box']").click();
        form.$("button[type = 'button']").click();

        $("span[data-test-id = 'phone'] span[class = 'input__sub']").shouldHave(exactText("Поле обязательно" +
                " для заполнения"));
    }

    @Test
    void shouldTestOrderWrongNotClickCheckbox() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("span[data-test-id = 'name'] input").setValue("Иван Петрович");
        form.$("span[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("button[type = 'button']").click();

        $("span[data-test-id = 'phone'] span[class = 'input__sub']").shouldHave(exactText("На указанный" +
                " номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер" +
                " ваш и введен корректно."));
    }
}
