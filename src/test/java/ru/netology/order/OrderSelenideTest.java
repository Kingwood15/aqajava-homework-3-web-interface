package ru.netology.order;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderSelenideTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPositiveTestOrder() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Сергей Андреевич");
        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш" +
                " менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldPositiveTwinNameTestOrder() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Максим-Иван Андреевич");
        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш" +
                " менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestOrderWrongNameSymbols() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue(";!%№!*:();!№");
        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongNameFromNumbers() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("1234 12345");
        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongNameFromLatin() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Peter Sergeevych");
        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderWrongEmptyName() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для" +
                " заполнения"));
    }

    @Test
    void shouldTestOrderWrongNameDescriptionNotPassport() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Иван");

        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Укажите точно как в паспорте"));
    }

    @Test
    void shouldTestOrderWrongPhone() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Петр Андреевич");
        form.$("[data-test-id = 'phone'] input").setValue("+7123456789012122123123123123");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestOrderWrongPhoneFromOneNumber() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Петр Андреевич");
        form.$("[data-test-id = 'phone'] input").setValue("1");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestOrderWrongPhoneFromText() {
        //open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Иван Сергеевич");
        form.$("[data-test-id = 'phone'] input").setValue("номер телефона");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestOrderWrongEmptyPhone() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Семен Свалов");
        form.$("[data-test-id = 'agreement']").click();
        form.$("[type = 'button']").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно" +
                " для заполнения"));
    }

    @Test
    void shouldTestOrderPhoneDescriptionSendSms() {
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'phone'] input").setValue("+79012345678");

        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("На указанный номер моб. тел. будет" +
                " отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно."));
    }

    @Test
    void shouldTestOrderWrongNotClickCheckbox() {
        //open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id = 'name'] input").setValue("Иван Петрович");
        form.$("[data-test-id = 'phone'] input").setValue("+71234567890");
        form.$("[type = 'button']").click();

        $("[data-test-id='agreement'].input_invalid");
    }
}
