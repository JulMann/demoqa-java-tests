package testData;

import java.util.List;

public class TestData {
    public static final String VALIDATION_CLASS = "was-validated";
    public static final String MODAL_TITLE = "Thanks for submitting the form";

    public static final String FIRST_NAME = "Julia";
    public static final String ONLY_WHITESPACE_FIRST_NAME = "    ";
    public static final String INVALID_FIRST_NAME = "-Ju1i@?!№**&";

    public static final String LAST_NAME = "Fox";
    public static final String ONLY_WHITESPACE_LAST_NAME = " ";
    public static final String INVALID_LAST_NAME = "F";

    public static final String FULL_NAME = "Julia Fox";
    public static final String LEADING_TRAILING_FULL_NAME = "    Julia Fox    ";

    public static final String USER_EMAIL = "julia@fox.aqa";
    public static final String INVALID_USER_EMAIL = "juliafox.aqa";

    public static final List<String> GENDER = List.of("Male", "Female", "Other");

    public static final String USER_NUMBER = "1231231230";
    public static final String WHITESPACE_USER_NUMBER = "          ";

    public static final String DATE_OF_BIRTH_MONTH = "May";
    public static final String DATE_OF_BIRTH_YEAR = "2000";
    public static final String DATE_OF_BIRTH_DAY = "15";

    public static final List<String> SUBJECT = List.of("Maths", "Computer Science", "Chemistry", "Physics", "Biology");
    public static final List<String> HOBBIES = List.of("Reading", "Music");

    public static final String PICTURE = "picture-homa.jpg";
    public static final String CURRENT_ADDRESS = "General Lizyukov Street, house 4";
    public static final String PERMANENT_ADDRESS = "Marata Street, house 17";

    public static final List<List<String>> STATE_AND_CITIES =
            List.of(
                    List.of("NCR", "Delhi"),
                    List.of("NCR", "Gurgaon"),
                    List.of("NCR", "Noida"),
                    List.of("Uttar Pradesh", "Agra"),
                    List.of("Uttar Pradesh", "Lucknow"),
                    List.of("Uttar Pradesh", "Merrut"),
                    List.of("Haryana", "Karnal"),
                    List.of("Haryana", "Panipat"),
                    List.of("Rajasthan", "Jaipur"),
                    List.of("Rajasthan", "Jaiselmer")
            );

    public static final String BORDER_PROPERTY = "border-color";
    public static final String RED_COLOR = "rgb(220, 53, 69)";
}

