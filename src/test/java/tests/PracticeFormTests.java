package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PracticeFormTests extends TestBase {
    @Test
    void successfulSubmissionAllFields() {
        open("/automation-practice-form");

        // Заполнение всех полей формы
        $("#firstName").setValue("Julia");
        $("#lastName").setValue("Fox");
        $("#userEmail").setValue("julia@fox.aqa");
        $("#gender-radio-2").click();
        $("#userNumber").setValue("1231231230");

        $("#dateOfBirthInput").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("#dateOfBirthInput").sendKeys("15 May 2000" + "\n");
        $("#dateOfBirthInput").pressEnter();

        $("#subjectsInput").clear();
        $("#subjectsInput").setValue("Computer Science").pressEnter();
        $("#subjectsInput").setValue("Maths").pressEnter();

        $("#hobbies-checkbox-1").click();
        $("#hobbies-checkbox-2").click();

        File picturePath = new File("src/test/resources/picture-homa.jpg");
        $("#uploadPicture").uploadFile(picturePath);

        $("#currentAddress").setValue("General Lizyukov Street, house 4");
        $("#react-select-3-input").setValue("Uttar Pradesh").pressEnter();
        $("#react-select-4-input").setValue("Lucknow").pressEnter();

        // Проверки заполнения
        $("#firstName").shouldHave(value("Julia"));
        $("#lastName").shouldHave(value("Fox"));
        $("#userEmail").shouldHave(value("julia@fox.aqa"));
        $("#gender-radio-2").shouldBe(selected);
        $("#userNumber").shouldHave(value("1231231230"));
        $("#dateOfBirthInput").shouldHave(value("15 May 2000"));
        $(".subjects-auto-complete__value-container").shouldHave(text("Computer Science"));
        $(".subjects-auto-complete__value-container").shouldHave(text("Maths"));
        $("#hobbies-checkbox-1").shouldBe(selected);
        $("#hobbies-checkbox-2").shouldBe(selected);
        $("#uploadPicture").shouldHave(value("picture-homa.jpg"));
        $("#currentAddress").shouldHave(value("General Lizyukov Street, house 4"));
        $("#state").$("div[class*='singleValue']").shouldHave(exactText("Uttar Pradesh"));
        $("#city").$("div[class*='singleValue']").shouldHave(exactText("Lucknow"));


        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

    }

    @Test
    void successfulSubmissionOnlyRequiredFields() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue("Julia");
        $("#lastName").setValue("Fox");
        $("#gender-radio-2").click();
        $("#userNumber").setValue("1231231230");
        $("#dateOfBirthInput").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("#dateOfBirthInput").sendKeys("15 May 2000" + "\n");
        $("#dateOfBirthInput").pressEnter();

        // Проверки заполнения
        $("#firstName").shouldHave(value("Julia"));
        $("#lastName").shouldHave(value("Fox"));
        $("#gender-radio-2").shouldBe(selected);
        $("#userNumber").shouldHave(value("1231231230"));
        $("#dateOfBirthInput").shouldHave(value("15 May 2000"));

        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

    }

    @Test
    void unsuccessfulSubmissionIncompleteForm() {
        open("/automation-practice-form");

        // Отправка формы без заполнения полей
        $("#submit").click();

        // Проверка отображения сообщений об ошибках. Ожидание: цвет границы поля - красный
        $("#firstName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#userNumber.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#gender-radio-1.form-check-input").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#gender-radio-2.form-check-input").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#gender-radio-3.form-check-input").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionSpacesInsteadName() {
        open("/automation-practice-form");

        // Заполнение только пробелами обязательных полей Name и Last Name
        $("#firstName").setValue("    ");
        $("#lastName").setValue(" ");

        // Заполнение остальных обязательных полей
        $("#gender-radio-2").click();
        $("#userNumber").setValue("1231231230");

        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();

        // Проверка отображения сообщений об ошибках для полей Name и Last Name. Ожидание: цвет границы поля - красный
        $("#firstName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionSpacesInsteadMobile() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue("Bob");
        $("#lastName").setValue("Bulk");
        $("#gender-radio-1").click();

        // Заполнение только пробелами обязательного поля Mobile
        $("#userNumber").setValue("          ");

        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();

        // Проверка отображения сообщения об ошибке для поля Mobile. Ожидание: цвет границы поля - красный
        $("#userNumber.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionInvalidEmail() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue("Julia");
        $("#lastName").setValue("Fox");
        $("#gender-radio-2").click();
        $("#userNumber").setValue("1231231230");
        $("#dateOfBirthInput").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("#dateOfBirthInput").sendKeys("15 May 2000" + "\n");
        $("#dateOfBirthInput").pressEnter();

        // Заполнение невалидного email
        $("#userEmail").setValue("juliafox.aqa");

        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();

        // Проверка отображения сообщения об ошибке для поля Email. Ожидание: цвет границы поля - красный
        $("#userEmail.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }
}
