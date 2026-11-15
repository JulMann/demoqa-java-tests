package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.String.format;
import static testData.TestData.*;


public class TextBoxTests extends TestBase {

    @Test
    void successfulSubmissionAllFields() {
        open("/text-box");

        // Заполнение всех полей формы
        $("#userName").setValue(USER_NAME);
        $("#userEmail").setValue(USER_EMAIL);
        $("#currentAddress").setValue(CURRENT_ADDRESS);
        $("#permanentAddress").setValue(PERMANENT_ADDRESS);

        $("#submit").click();

        // Проверки отображения отправленных данных
        $("#name.mb-1").scrollTo();
        $("#name.mb-1").shouldHave(text(USER_NAME));
        $("#email.mb-1").shouldHave(text(USER_EMAIL));
        $("#currentAddress.mb-1").shouldHave(text(CURRENT_ADDRESS));
        $("#permanentAddress.mb-1").shouldHave(text(PERMANENT_ADDRESS));

    }

    @Test
    void trimsExtraSpacesInNameField() {
        open("/text-box");

        // Заполнение поля Name с лишними пробелами
        $("#userName").setValue(format("   %s   ", USER_NAME));
        $("#submit").click();

        // Проверка отправленного поля Name: Ожидание - лишние пробелы удалены
        $("#name.mb-1").scrollTo();
        $("#name.mb-1").shouldHave(text(USER_NAME));

    }
}
