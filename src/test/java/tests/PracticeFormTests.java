package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PracticeFormTests extends TestBase {
    @Test
    void successfulFillAllFields() {
        open("/automation-practice-form");

        // Заполнение всех полей
        $("[id=firstName]").setValue("Julia");
        $("[id=lastName]").setValue("Fox");
        $("[id=userEmail]").setValue("julia@fox.aqa");
        $("[name='gender'][value='Female']").click();
        $("[id=userNumber]").setValue("1231231230");

        $("[id=dateOfBirthInput]").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[id=dateOfBirthInput]").sendKeys("15 May 2000" + "\n");
        $("[id=dateOfBirthInput]").pressEnter();

        $("#subjectsInput").clear();
        $("#subjectsInput").setValue("Computer Science").pressEnter();
        $("#subjectsInput").setValue("Maths").pressEnter();

        $("[type='checkbox'][value='1']").click();
        $("[type='checkbox'][value='2']").click();

        File picturePath = new File("src/test/resources/picture-homa.jpg");
        $("#uploadPicture").uploadFile(picturePath);

        $("[id=currentAddress]").setValue("General Lizyukov Street, house 4");
        $("#react-select-3-input").setValue("Uttar Pradesh").pressEnter();
        $("#react-select-4-input").setValue("Lucknow").pressEnter();

        // Проверки заполнения
        $(byText("Julia"));
        $(byText("Fox"));
        $(byText("julia@fox.aqa"));
        $("[name='gender'][value='Female']").shouldBe(selected);
        $(byText("1231231230"));
        $(byText("15 May 2000"));
        $(byText("Computer Science"));
        $(byText("Maths"));
        $("[type='checkbox'][value='1']").shouldBe(selected);
        $("[type='checkbox'][value='2']").shouldBe(selected);
        $(byText("picture-homa.jpg"));
        $(byText("General Lizyukov Street, house 4"));
        $(byText("Uttar Pradesh"));
        $(byText("Lucknow"));

        // Отправка формы и открытие модального окна (предварительно прокрутив страницу)
        $("[id=submit]").scrollTo();
        $("[id=submit]").click();

        $("[id=example-modal-sizes-title-lg]").shouldHave(text("Thanks for submitting the form"));

    }

    @Test
    void successfulFillRequiredFields() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("[id=firstName]").setValue("Julia");
        $("[id=lastName]").setValue("Fox");
        $("[name='gender'][value='Female']").click();
        $("[id=userNumber]").setValue("1231231230");

        $("[id=dateOfBirthInput]").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[id=dateOfBirthInput]").sendKeys("15 May 2000" + "\n");
        $("[id=dateOfBirthInput]").pressEnter();

        // Проверки заполнения
        $(byText("Julia"));
        $(byText("Fox"));
        $("[name='gender'][value='Female']").shouldBe(selected);
        $(byText("1231231230"));
        $(byText("15 May 2000"));

        // Отправка формы и открытие модального окна (предварительно прокрутив страницу)
        $("[id=submit]").scrollTo();
        $("[id=submit]").click();

        $("[id=example-modal-sizes-title-lg]").shouldHave(text("Thanks for submitting the form"));

    }

    @Test
    void unsuccessfulSubmissionIncompleteForm() {
        open("/automation-practice-form");

        // Отправка формы без заполнения полей
        $("[id=submit]").click();

        // Проверка отображения сообщений об ошибках: цвет границы поля - красный
        $("#firstName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#userNumber.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#gender-radio-1.form-check-input").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#gender-radio-2.form-check-input").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#gender-radio-3.form-check-input").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

}
