package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxTests extends TestBase {

    @Test
    void successfulSubmissionAllFields() {
        open("/text-box");

        // Заполнение всех полей формы
        $("#userName").setValue("Алиса Игоревна Селезнева");
        $("#userEmail").setValue("Alice.Selesneva@cosmo.ru");
        $("#currentAddress").setValue("Москва, Гагаринский переулок, дом 28");
        $("#permanentAddress").setValue("Москва, НИИ Космозоо");

        $("#submit").click();

        // Проверки сохранения и отображения данных
        $("#name.mb-1").scrollTo();
        $("#name.mb-1").shouldHave(text("Name: Алиса Игоревна Селезнева"));
        $("#email.mb-1").shouldHave(text("Email: Alice.Selesneva@cosmo.ru"));
        $("#currentAddress.mb-1").shouldHave(text("Current Address: Москва, Гагаринский переулок, дом 28"));
        $("#permanentAddress.mb-1").shouldHave(text("Permananet Address: Москва, НИИ Космозоо"));

    }

    @Test
    void submissionNameFieldTrimsSpaces() {
        open("/text-box");

        // Заполнение всех полей формы
        $("#userName").setValue("          Алиса Игоревна         Селезнева            ");

        $("#submit").click();

        // Проверка сохранения поля Name с удаленными пробелами
        $("#name.mb-1").scrollTo();
        $("#name.mb-1").shouldHave(text("Name: Алиса Игоревна Селезнева"));

    }
}
