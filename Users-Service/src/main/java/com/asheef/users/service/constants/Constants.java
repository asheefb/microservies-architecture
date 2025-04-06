package com.asheef.users.service.constants;

import java.util.regex.Pattern;

public class Constants {
    public static final String FIRST_NAME_SHOULD_NOT_BE_EMPTY = "first name should not be null";

    public static final String FIRST_NAME = "firstName";

    public static final Integer MIN_VALUE = 3;

    public static final String FIRST_NAME_LESS_MESSAGE = "first name should not be less than 3 character";

    public static final String DUPLICATE_NAME = "Name is Already exist";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static final String INVALID_EMAIL = "Invalid Email";

    public static final String EMAIL = "email";

    public static final String INVALID_PHONE_NUMBER = "Invalid phone number. It should be exactly 10 digits.";

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String DATE_OF_BIRTH_SHOULD_NOT_BE_NULL = "date of birth should not be null";

    public static final String DATE_OF_BIRTH = "dateOfBirth";

}
