package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static testData.TestData.*;


public class TextBoxTests extends TestBase {

    @Test
    void successfulSubmissionAllFields() {
        open("/text-box");

        // Заполнение всех полей формы
        $("#userName").setValue(FULL_NAME);
        $("#userEmail").setValue(USER_EMAIL);
        $("#currentAddress").setValue(CURRENT_ADDRESS);
        $("#permanentAddress").setValue(PERMANENT_ADDRESS);

        $("#submit").click();

        // Проверки отображения отправленных данных
        $("#name.mb-1").scrollTo();
        $("#name.mb-1").shouldHave(text("Name: " + FULL_NAME));
        $("#email.mb-1").shouldHave(text("Email: " + USER_EMAIL));
        $("#currentAddress.mb-1").shouldHave(text("Current Address: " + CURRENT_ADDRESS));
        $("#permanentAddress.mb-1").shouldHave(text("Permananet Address: " + PERMANENT_ADDRESS));

    }

    @Test
    void trimsExtraSpacesInNameField() {
        open("/text-box");

        // Заполнение поля Name с лишними пробелами
        $("#userName").setValue(LEADING_TRAILING_FULL_NAME);

        $("#submit").click();

        // Проверка отправленного поля Name: Ожидание - лишние пробелы удалены
        $("#name.mb-1").scrollTo();
        $("#name.mb-1").shouldHave(text("Name: " + FULL_NAME));

    }
}
