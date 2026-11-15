package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static testData.TestData.*;

public class RegistrationFormTests extends TestBase {

    @Test
    void successfulSubmissionAllFields() {
        open("/automation-practice-form");

        // Заполнение всех полей формы
        $("#firstName").setValue(FIRST_NAME);
        $("#lastName").setValue(LAST_NAME);
        $("#userEmail").setValue(USER_EMAIL);
        $("#genterWrapper").$(byText(GENDER.get(1))).click();
        $("#userNumber").setValue(USER_NUMBER);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(DATE_OF_BIRTH_MONTH);
        $(".react-datepicker__year-select").selectOptionByValue(DATE_OF_BIRTH_YEAR);
        $(".react-datepicker__day--0" + DATE_OF_BIRTH_DAY).click();

        $("#subjectsInput").clear();
        for (String subject : SUBJECT) {
            $("#subjectsInput").setValue(subject);
            $(".subjects-auto-complete__option").click();
        }

        for (String hobbies : HOBBIES) {
            $("#hobbiesWrapper").$(byText(hobbies)).click();
        }

        $("#uploadPicture").uploadFromClasspath(PICTURE);
        $("#currentAddress").setValue(CURRENT_ADDRESS);
        $("#react-select-3-input").setValue(STATE_AND_CITIES.get(4).get(0)).pressEnter();
        $("#react-select-4-input").setValue(STATE_AND_CITIES.get(4).get(1)).pressEnter();

        // Отправка формы, проверка валидации и подтверждение корректного модального окна
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(cssClass(VALIDATION_CLASS));
        $("#example-modal-sizes-title-lg").shouldHave(text(MODAL_TITLE));

        // Проверки отображения отправленных данных в модальном окне
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text(FIRST_NAME + " " + LAST_NAME));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text(USER_EMAIL));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text(GENDER.get(1)));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text(USER_NUMBER));
        $(".table-responsive").$(byText("Date of Birth")).parent().
                shouldHave(text(DATE_OF_BIRTH_DAY + " " + DATE_OF_BIRTH_MONTH + "," + DATE_OF_BIRTH_YEAR));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text(String.join(", ", SUBJECT)));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text(String.join(", ", HOBBIES)));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text(PICTURE));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text(CURRENT_ADDRESS));
        $(".table-responsive").$(byText("State and City")).parent().
                shouldHave(text(STATE_AND_CITIES.get(4).get(0) + " " + STATE_AND_CITIES.get(4).get(1)));

    }

    @Test
    void successfulSubmissionOnlyRequiredFields() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue(FIRST_NAME);
        $("#lastName").setValue(LAST_NAME);
        $("#genterWrapper").$(byText(GENDER.get(1))).click();
        $("#userNumber").setValue(USER_NUMBER);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(DATE_OF_BIRTH_MONTH);
        $(".react-datepicker__year-select").selectOptionByValue(DATE_OF_BIRTH_YEAR);
        $(".react-datepicker__day--0" + DATE_OF_BIRTH_DAY).click();

        // Отправка формы, проверка валидации и подтверждение корректного модального окна
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(cssClass(VALIDATION_CLASS));
        $("#example-modal-sizes-title-lg").shouldHave(text(MODAL_TITLE));

        // Проверки отображения отправленных данных в модальном окне
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text(FIRST_NAME + " " + LAST_NAME));
        $(".table-responsive").$(byText("Student Email")).parent().$$("td").get(1).shouldHave(empty);
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text(GENDER.get(1)));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text(USER_NUMBER));
        $(".table-responsive").$(byText("Date of Birth")).parent().
                shouldHave(text(DATE_OF_BIRTH_DAY + " " + DATE_OF_BIRTH_MONTH + "," + DATE_OF_BIRTH_YEAR));
        $(".table-responsive").$(byText("Subjects")).parent().$$("td").get(1).shouldHave(empty);
        $(".table-responsive").$(byText("Hobbies")).parent().$$("td").get(1).shouldHave(empty);
        $(".table-responsive").$(byText("Picture")).parent().$$("td").get(1).shouldHave(empty);
        $(".table-responsive").$(byText("Address")).parent().$$("td").get(1).shouldHave(empty);
        $(".table-responsive").$(byText("State and City")).parent().$$("td").get(1).shouldHave(empty);

    }

    @Test
    void unsuccessfulSubmissionIncompleteForm() {
        open("/automation-practice-form");

        // Отправка формы без заполнения полей, проверка валидации
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(Condition.cssClass(VALIDATION_CLASS));

        // Проверка выделения полей с ошибками. Ожидание: цвет границы поля - красный
        $("#firstName").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#lastName").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#userNumber").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#genterWrapper").$(byText(GENDER.get(0))).shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#genterWrapper").$(byText(GENDER.get(1))).shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#genterWrapper").$(byText(GENDER.get(2))).shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));

    }

    @Test
    void unsuccessfulSubmissionSpacesInsteadName() {
        open("/automation-practice-form");

        // Заполнение только пробелами обязательных полей Name и Last Name
        $("#firstName").setValue(ONLY_WHITESPACE_FIRST_NAME);
        $("#lastName").setValue(ONLY_WHITESPACE_LAST_NAME);

        // Заполнение остальных обязательных полей
        $("#genterWrapper").$(byText(GENDER.get(0))).click();
        $("#userNumber").setValue(USER_NUMBER);

        // Оправка формы, проверка валидации
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(Condition.cssClass(VALIDATION_CLASS));

        // Проверка выделения полей Name и Last Name. Ожидание: цвет границы поля - красный
        $("#firstName").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#lastName").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));

    }

    @Test
    void unsuccessfulSubmissionSpacesInsteadMobile() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue(FIRST_NAME);
        $("#lastName").setValue(LAST_NAME);
        $("#genterWrapper").$(byText(GENDER.get(1))).click();

        // Заполнение только пробелами обязательного поля Mobile
        $("#userNumber").setValue(WHITESPACE_USER_NUMBER);

        // Оправка формы, проверка валидации
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(Condition.cssClass(VALIDATION_CLASS));

        // Проверка выделения поля Mobile. Ожидание: цвет границы поля - красный
        $("#userNumber").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));

    }

    @Test
    void unsuccessfulSubmissionInvalidEmail() {
        open("/automation-practice-form");

        // Заполнение обязательных полей
        $("#firstName").setValue(FIRST_NAME);
        $("#lastName").setValue(LAST_NAME);
        $("#genterWrapper").$(byText(GENDER.get(1))).click();
        $("#userNumber").setValue(USER_NUMBER);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(DATE_OF_BIRTH_MONTH);
        $(".react-datepicker__year-select").selectOptionByValue(DATE_OF_BIRTH_YEAR);
        $(".react-datepicker__day--0" + DATE_OF_BIRTH_DAY).click();

        // Заполнение невалидного email
        $("#userEmail").setValue(INVALID_USER_EMAIL);

        // Оправка формы, проверка валидации
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(Condition.cssClass(VALIDATION_CLASS));

        // Проверка выделения поля Email. Ожидание: цвет границы поля - красный
        $("#userEmail").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));

    }

    @Test
    void unsuccessfulSubmissionInvalidName() {
        open("/automation-practice-form");

        // Заполнение невалидного Name
        $("#firstName").setValue(INVALID_FIRST_NAME);
        $("#lastName").setValue(INVALID_LAST_NAME);

        // Заполнение обязательных полей
        $("#genterWrapper").$(byText(GENDER.get(1))).click();
        $("#userNumber").setValue(USER_NUMBER);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(DATE_OF_BIRTH_MONTH);
        $(".react-datepicker__year-select").selectOptionByValue(DATE_OF_BIRTH_YEAR);
        $(".react-datepicker__day--0" + DATE_OF_BIRTH_DAY).click();

        // Оправка формы, проверка валидации
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(Condition.cssClass(VALIDATION_CLASS));

        // Проверка выделения полей Name и Last Name. Ожидание: цвет границы поля - красный
        $("#firstName").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));
        $("#lastName").shouldHave(cssValue(BORDER_PROPERTY, RED_COLOR));

    }
}
