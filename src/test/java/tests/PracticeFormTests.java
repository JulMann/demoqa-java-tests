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


        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        // Проверки сохранения и отображения данных в модальном окне
        $(".modal-body tr:nth-child(1) td:nth-child(1)").shouldHave(text("Student Name"));
        $(".modal-body tr:nth-child(1) td:nth-child(2)").shouldHave(text("Julia Fox"));

        $(".modal-body tr:nth-child(2) td:nth-child(1)").shouldHave(text("Student Email"));
        $(".modal-body tr:nth-child(2) td:nth-child(2)").shouldHave(text("julia@fox.aqa"));

        $(".modal-body tr:nth-child(3) td:nth-child(1)").shouldHave(text("Gender"));
        $(".modal-body tr:nth-child(3) td:nth-child(2)").shouldHave(text("Female"));

        $(".modal-body tr:nth-child(4) td:nth-child(1)").shouldHave(text("Mobile"));
        $(".modal-body tr:nth-child(4) td:nth-child(2)").shouldHave(text("1231231230"));

        $(".modal-body tr:nth-child(5) td:nth-child(1)").shouldHave(text("Date of Birth"));
        $(".modal-body tr:nth-child(5) td:nth-child(2)").shouldHave(text("15 May,2000"));

        $(".modal-body tr:nth-child(6) td:nth-child(1)").shouldHave(text("Subjects"));
        $(".modal-body tr:nth-child(6) td:nth-child(2)").shouldHave(text("Computer Science, Maths"));

        $(".modal-body tr:nth-child(7) td:nth-child(1)").shouldHave(text("Hobbies"));
        $(".modal-body tr:nth-child(7) td:nth-child(2)").shouldHave(text("Sports, Reading"));

        $(".modal-body tr:nth-child(8) td:nth-child(1)").shouldHave(text("Picture"));
        $(".modal-body tr:nth-child(8) td:nth-child(2)").shouldHave(text("picture-homa.jpg"));

        $(".modal-body tr:nth-child(9) td:nth-child(1)").shouldHave(text("Address"));
        $(".modal-body tr:nth-child(9) td:nth-child(2)").shouldHave(text("General Lizyukov Street, house 4"));

        $(".modal-body tr:nth-child(10) td:nth-child(1)").shouldHave(text("State and City"));
        $(".modal-body tr:nth-child(10) td:nth-child(2)").shouldHave(text("Uttar Pradesh Lucknow"));

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

        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        // Проверки сохранения и отображения данных в модальном окне
        $(".modal-body tr:nth-child(1) td:nth-child(1)").shouldHave(text("Student Name"));
        $(".modal-body tr:nth-child(1) td:nth-child(2)").shouldHave(text("Julia Fox"));

        $(".modal-body tr:nth-child(2) td:nth-child(1)").shouldHave(text("Student Email"));
        $(".modal-body tr:nth-child(2) td:nth-child(2)").shouldHave(empty);

        $(".modal-body tr:nth-child(3) td:nth-child(1)").shouldHave(text("Gender"));
        $(".modal-body tr:nth-child(3) td:nth-child(2)").shouldHave(text("Female"));

        $(".modal-body tr:nth-child(4) td:nth-child(1)").shouldHave(text("Mobile"));
        $(".modal-body tr:nth-child(4) td:nth-child(2)").shouldHave(text("1231231230"));

        $(".modal-body tr:nth-child(5) td:nth-child(1)").shouldHave(text("Date of Birth"));
        $(".modal-body tr:nth-child(5) td:nth-child(2)").shouldHave(text("15 May,2000"));

        $(".modal-body tr:nth-child(6) td:nth-child(1)").shouldHave(text("Subjects"));
        $(".modal-body tr:nth-child(6) td:nth-child(2)").shouldHave(empty);

        $(".modal-body tr:nth-child(7) td:nth-child(1)").shouldHave(text("Hobbies"));
        $(".modal-body tr:nth-child(7) td:nth-child(2)").shouldHave(empty);

        $(".modal-body tr:nth-child(8) td:nth-child(1)").shouldHave(text("Picture"));
        $(".modal-body tr:nth-child(8) td:nth-child(2)").shouldHave(empty);

        $(".modal-body tr:nth-child(9) td:nth-child(1)").shouldHave(text("Address"));
        $(".modal-body tr:nth-child(9) td:nth-child(2)").shouldHave(empty);

        $(".modal-body tr:nth-child(10) td:nth-child(1)").shouldHave(text("State and City"));
        $(".modal-body tr:nth-child(10) td:nth-child(2)").shouldHave(empty);

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

    @Test
    void unsuccessfulSubmissionInvalidName() {
        open("/automation-practice-form");

        // Заполнение невалидного Name
        $("#firstName").setValue("-Ju1i@?!№**&");
        $("#lastName").setValue("F");

        // Заполнение обязательных полей
        $("#gender-radio-2").click();
        $("#userNumber").setValue("1231231230");
        $("#dateOfBirthInput").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("#dateOfBirthInput").sendKeys("15 May 2000" + "\n");
        $("#dateOfBirthInput").pressEnter();

        // Скролл до кнопки. Оправка формы и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();

        // Проверка отображения сообщений об ошибках для полей Name и Last Name. Ожидание: цвет границы поля - красный
        $("#firstName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName.mr-sm-2.form-control").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
    }
}
