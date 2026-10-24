package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
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
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("1231231230");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").shouldBe(visible).selectOptionByValue("4");
        $(".react-datepicker__year-select").shouldBe(visible).selectOptionByValue("2000");
        $(".react-datepicker__day--015").click();

        $("#subjectsInput").clear();
        $("#subjectsInput").setValue("Computer Science").pressEnter();
        $("#subjectsInput").setValue("Maths").pressEnter();

        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Reading")).click();

        $("#uploadPicture").uploadFromClasspath("picture-homa.jpg");

        $("#currentAddress").setValue("General Lizyukov Street, house 4");
        $("#react-select-3-input").setValue("Uttar Pradesh").pressEnter();
        $("#react-select-4-input").setValue("Lucknow").pressEnter();


        // Оправка формы, проверка валидации и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));
        $("#example-modal-sizes-title-lg").shouldBe(Condition.visible);

        // Проверки отображения отправленных данных в модальном окне
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Julia Fox"));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text("julia@fox.aqa"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Female"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("1231231230"));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text("15 May,2000"));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text("Computer Science, Maths"));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text("Sports, Reading"));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text("picture-homa.jpg"));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text("General Lizyukov Street, house 4"));
        $(".table-responsive").$(byText("State and City")).parent().shouldHave(text("Uttar Pradesh Lucknow"));

        // Закрытие модального окна
        $("#closeLargeModal").click();
        $("#example-modal-sizes-title-lg").shouldNotBe(Condition.visible);
    }

    @Test
    void successfulSubmissionOnlyRequiredFields() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue("Julia");
        $("#lastName").setValue("Fox");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("1231231230");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").shouldBe(visible).selectOptionByValue("4");
        $(".react-datepicker__year-select").shouldBe(visible).selectOptionByValue("2000");
        $(".react-datepicker__day--015").click();

        // Оправка формы, проверка валидация и открытие модального окна
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        // Проверки отображения отправленных данных в модальном окне
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Julia Fox"));
        $(".table-responsive").$(byText("Student Email")).parent().$$("td").get(1).shouldHave(Condition.empty);
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Female"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("1231231230"));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text("15 May,2000"));
        $(".table-responsive").$(byText("Subjects")).parent().$$("td").get(1).shouldHave(Condition.empty);
        $(".table-responsive").$(byText("Hobbies")).parent().$$("td").get(1).shouldHave(Condition.empty);
        $(".table-responsive").$(byText("Picture")).parent().$$("td").get(1).shouldHave(Condition.empty);
        $(".table-responsive").$(byText("Address")).parent().$$("td").get(1).shouldHave(Condition.empty);
        $(".table-responsive").$(byText("State and City")).parent().$$("td").get(1).shouldHave(Condition.empty);

        // Закрытие модального окна
        $("#closeLargeModal").click();
        $("#example-modal-sizes-title-lg").shouldNotBe(Condition.visible);

    }

    @Test
    void unsuccessfulSubmissionIncompleteForm() {
        open("/automation-practice-form");

        // Отправка формы без заполнения полей, проверка валидации
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));

        // Проверка выделения полей с ошибками. Ожидание: цвет границы поля - красный
        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#genterWrapper").$(byText("Male")).shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#genterWrapper").$(byText("Female")).shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#genterWrapper").$(byText("Other")).shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionSpacesInsteadName() {
        open("/automation-practice-form");

        // Заполнение только пробелами обязательных полей Name и Last Name
        $("#firstName").setValue("    ");
        $("#lastName").setValue(" ");

        // Заполнение остальных обязательных полей
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("1231231230");

        // Оправка формы, проверка валидации
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));

        // Проверка выделения полей Name и Last Name. Ожидание: цвет границы поля - красный
        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionSpacesInsteadMobile() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue("Bob");
        $("#lastName").setValue("Bulk");
        $("#genterWrapper").$(byText("Male")).click();

        // Заполнение только пробелами обязательного поля Mobile
        $("#userNumber").setValue("          ");

        // Оправка формы, проверка валидации
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));

        // Проверка выделения поля Mobile. Ожидание: цвет границы поля - красный
        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionInvalidEmail() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue("Julia");
        $("#lastName").setValue("Fox");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("1231231230");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").shouldBe(visible).selectOptionByValue("4");
        $(".react-datepicker__year-select").shouldBe(visible).selectOptionByValue("2000");
        $(".react-datepicker__day--015").click();

        // Заполнение невалидного email
        $("#userEmail").setValue("juliafox.aqa");

        // Оправка формы, проверка валидации
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));

        // Проверка выделения поля Email. Ожидание: цвет границы поля - красный
        $("#userEmail").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }

    @Test
    void unsuccessfulSubmissionInvalidName() {
        open("/automation-practice-form");

        // Заполнение невалидного Name
        $("#firstName").setValue("-Ju1i@?!№**&");
        $("#lastName").setValue("F");

        // Заполнение обязательных полей
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("1231231230");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").shouldBe(visible).selectOptionByValue("4");
        $(".react-datepicker__year-select").shouldBe(visible).selectOptionByValue("2000");
        $(".react-datepicker__day--015").click();

        // Оправка формы, проверка валидации
        $("#submit").scrollTo();
        $("#submit").click();
        $("#userForm").shouldHave(Condition.cssClass("was-validated"));

        // Проверка выделения полей Name и Last Name. Ожидание: цвет границы поля - красный
        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

    }
}
