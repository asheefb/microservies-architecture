package com.asheef.users.service.constants;

import java.util.regex.Pattern;

public class Constants {

    public static final String SUCCESS = "Success";

    public static final String UNABLE_TO_VALIDATE_DATA = "Unable to validate data";

    public static final String FIRST_NAME_SHOULD_NOT_BE_EMPTY = "first name should not be null";

    public static final String FIRST_NAME = "firstName";

    public static final Integer MIN_VALUE = 3;

    public static final String FIRST_NAME_LESS_MESSAGE = "first name should not be less than 3 character";

    public static final String DUPLICATE_NAME = "Name is Already exist";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static final String INVALID_EMAIL = "Invalid Email";

    public static final String EMAIL = "email";

    public static final String INVALID_PHONE_NUMBER = "Invalid phone number. It should be exactly 10 digits.";

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String DATE_OF_BIRTH_SHOULD_NOT_BE_NULL = "date of birth should not be null";

    public static final String DATE_OF_BIRTH = "dateOfBirth";

    public static final String ADDRESS_LINE_1_ERROR_MESSAGE = "address line 1 should not be less than 3 character";

    public static final String ADDRESS_LINE_1 = "AddressLine1";

    public static final String CITY_SHOULD_NOT_BE_EMPTY = "city should not be empty";

    public static final String CITY = "city";

    public static final String STATE_SHOULD_NOT_BE_EMPTY = "state should not be empty";

    public static final String STATE = "state";

    public static final String COUNTRY_SHOULD_NOT_BE_EMPTY = "country should not be empty";

    public static final String COUNTRY = "country";

    public static final String ADDED_SUCCESS = "User Added Successfully!!";

    public static final String COUNTRY_TYPE = "COUNTRY";

    public static final String NO_DATA_FOUND = "No data found";

    public static final String UNABLE_TO_FETCH_DATA = "Unable to fetch data";

    public static final String ACTIVE = "Active";

    public static final String FAILED = "Failed";

    public static final String EMAIL_ALREADY_EXIST = "Email Already Exist";

    public static final String PHONE_NUMBER_ALREADY_EXIST = "Phone Number Already Exist";

    //update users
    public static final String INVALID_ID_USER_NOT_FOUND = "Invalid id User Not Found";

    public static final String INVALID_USER_ID_USER_NOT_FOUND = "Invalid userId User Not Found";

    public static final String LAST_NAME = "Last Name";

    public static final String USER = "User";


    public static final String ADDRESS_LINE_2 = "Address Line 2";

    public static final String PIN_CODE = "Pin Code";

    public static final String UNABLE_TO_UPDATE_USER = "Unable to update user";

    public static final String UPDATED_SUCCESS = "Updated Successfully!!";

    public static final String DESIGNATION_SHOULD_NOT_BE_EMPTY = "Designation should not be null";

    public static final String DESIGNATION = "designation";

    public static final String JOINING_DATE_SHOULD_NOT_BE_EMPTY = "Joining date should not be empty";

    public static final String JOINING_DATE = "joiningDate";

    public static final String EMPLOYEE_TYPE_SHOULD_NOT_BE_EMPTY = "Employee type should not be null";

    public static final String EMPLOYEE_TYPE = "employeeType";

    public static final String SKILLS_SHOULD_NOT_BE_EMPTY = "Skills should not be null";

    public static final String SKILLS = "skills";

    public static final String SALARY_MESSAGE = "Salary should not be Empty";

    public static final String SALARY = "salary";

    public static final String BANK_NUMBER = "bankAccountNumber";

    public static final String BANK_NUMBER_MESSAGE = "Bank number should not be empty";

    public static final String IFSC_CODE_SHOULD_NOT_BE_EMPTY = "IFSC code should not be Empty";

    public static final String IFSC_CODE = "ifscCode";

    public static final String INVALID_IFSC = "Ifsc Code is not valid";
    public static final Object ADDITIONAL_DETAILS_ADDED = "Additional Details added Successfully!!";
    public static final String UNABLE_TO_UPDATE_DATA = "Unable to update data";
    public static final String ID_SHOULD_NOT_BE_EMPTY = "Id should not be empty";
    public static final String ID = "id";
    public static final String EDUCATION_DETAILS = "Education Details";
    public static final String EDUCATION_DETAILS_REACHED_MAX_LIMIT = "Education Details reached Max Limit";
    public static final String EDUCATIONAL_LEVEL_MESSAGE ="Education Level should not be empty" ;
    public static final String EDUCATION_LEVEL = "educationLevel";
    public static final String SSLC = "sslc";
    public static final String BOARD_NAME_MESSAGE = "Board name should not be empty";
    public static final String BOARD_NAME = "boardName";
    public static final String SCHOOL_OR_COLLEGE_NAME_SHOULD_NOT_BE_EMPTY = "School or College name should not be empty";
    public static final String SCHOOL_OR_COLLEGE_NAME = "schoolOrCollegeName";
    public static final String LOCATION_SHOULD_NOT_BE_EMPTY = "Location should not be empty";
    public static final String LOCATION = "location";
    public static final String PASSING_YEAR_SHOULD_NOT_BE_EMPTY = "Passing year should not be empty";
    public static final String PASSING_YEAR = "passOut";
    public static final String PERCENTAGE_SHOULD_NOT_BE_EMPTY = "Percentage should not be empty";
    public static final String PERCENTAGE = "percentage";
    public static final String EDUCATION_DETAILS_ADDED = "Education Details added Successfully!!";
    public static final String GRADE_SHOULD_NOT_BE_EMPTY = "Grade should not be empty";
    public static final String GRADE = "grade";
    public static final String INVALID_PHONE_USER_NOT_FOUND = "Invalid phone User Not Found";
    public static final String INVALID_ID = "Invalid Id";
    public static final String INVALID_SEARCH_TEXT = "Minimum 3 characters required for search";


    public static String BANK_NUMBER_MESSAGE_MIN_LENGTH = "Account number should not be less than 9 digits";

    public static String IFSC_REGEX = "^[A-Z]{4}0[A-Z0-9]{6}$";

    public static Pattern PATTERN_IFSC = Pattern.compile(IFSC_REGEX);

    //Queries
    public static final String LOCATION_LOOKUP = " { $lookup: {" +
            "      from: 'city_state_location'," +
            "      localField: 'address_info.city'," +
            "      foreignField: '_id'," +
            "      as: 'cityDetails'}}";

    public static final String LOCATION_UNWIND = " {\n" +
            "    $unwind: {\n" +
            "      path: \"$cityDetails\",\n" +
            "      preserveNullAndEmptyArrays: true\n" +
            "    }\n" +
            "  }";

    public static final String PROJECT = "{\n" +
            "    $project: {\n" +
            "      user_id: 1,\n" +
            "      first_name: 1,\n" +
            "      last_name: 1,\n" +
            "      email: 1,\n" +
            "      phone_number: 1,\n" +
            "      addressLine1:\n" +
            "        \"$address_info.address_line_1\",\n" +
            "      addressLine2:\n" +
            "        \"$address_info.address_line_2\",\n" +
            "      cityName: \"$cityDetails.city_name\",\n" +
            "      stateName: \"$cityDetails.state_name\",\n" +
            "      countryName: \"$cityDetails.country_name\"\n" +
            "    }\n" +
            "  }";
}
