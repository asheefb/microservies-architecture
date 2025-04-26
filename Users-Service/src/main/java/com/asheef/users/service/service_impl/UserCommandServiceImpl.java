package com.asheef.users.service.service_impl;

import com.asheef.common_model_mdb.model.CityStateLocation;
import com.asheef.common_model_mdb.model.employee.*;
import com.asheef.common_model_mdb.model.utils.ErrorStructure;
import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.common_model_mdb.repository.CityStateLocationRepository;
import com.asheef.common_model_mdb.repository.UserAuditRepository;
import com.asheef.common_model_mdb.repository.UserModelRepository;
import com.asheef.common_model_ms.model.Location;
import com.asheef.common_model_ms.model.employee.Salary;
import com.asheef.common_model_ms.model.employee.Users;
import com.asheef.common_model_ms.repository.LocationRepository;
import com.asheef.common_model_ms.repository.SalaryRepository;
import com.asheef.common_model_ms.repository.UsersRepository;
import com.asheef.users.service.constants.Constants;
import com.asheef.users.service.dto.*;
import com.asheef.users.service.service.UserCommandService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private final UserModelRepository userModelRepository;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final CityStateLocationRepository cityStateLocationRepository;

    @Autowired
    private final LocationRepository locationRepository;

    @Autowired
    private final SalaryRepository salaryRepository;

    @Autowired
    private final UserAuditRepository userAuditRepository;

    public UserCommandServiceImpl(UserModelRepository userModelRepository, UsersRepository usersRepository, CityStateLocationRepository cityStateLocationRepository, LocationRepository locationRepository, SalaryRepository salaryRepository, UserAuditRepository userAuditRepository) {
        this.userModelRepository = userModelRepository;
        this.usersRepository = usersRepository;
        this.cityStateLocationRepository = cityStateLocationRepository;
        this.locationRepository = locationRepository;
        this.salaryRepository = salaryRepository;
        this.userAuditRepository = userAuditRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO> addUsers(UsersDto usersDto) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {
            Users user = new Users();
            UserModel userModel = new UserModel();

            ErrorStructure errorStructure;

            var errors = new ArrayList<>();

            if (usersDto.getFirstName() == null || usersDto.getFirstName().isEmpty()) {
                errorStructure = new ErrorStructure(usersDto.getFirstName(), Constants.FIRST_NAME_SHOULD_NOT_BE_EMPTY, Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (usersDto.getFirstName().length() < Constants.MIN_VALUE) {
                errorStructure = new ErrorStructure(usersDto.getFirstName(), Constants.FIRST_NAME_LESS_MESSAGE, Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (userModelRepository.findByName(usersDto.getFirstName()).isPresent()) {
                errorStructure = new ErrorStructure(usersDto.getFirstName(), Constants.DUPLICATE_NAME, Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else {
                user.setFirstName(usersDto.getFirstName());
                userModel.setFirstName(usersDto.getFirstName());
            }

            user.setLastName(usersDto.getLastName());
            userModel.setLastName(usersDto.getLastName());

            if (usersDto.getEmail() == null || usersDto.getEmail().isEmpty() || !Constants.EMAIL_PATTERN.matcher(usersDto.getEmail()).matches()) {
                errorStructure = new ErrorStructure(usersDto.getEmail(), Constants.INVALID_EMAIL, Constants.EMAIL);
                errors.add(errorStructure);
            } else if (usersRepository.findByEmail(usersDto.getEmail()).isPresent()) {
                errorStructure = new ErrorStructure(usersDto.getEmail(), Constants.EMAIL_ALREADY_EXIST, Constants.EMAIL);
                errors.add(errorStructure);
            } else {
                user.setEmail(usersDto.getEmail());
                userModel.setEmail(usersDto.getEmail());
            }

            if (usersDto.getPhoneNumber() == null || usersDto.getPhoneNumber().length() != 10) {
                errorStructure = new ErrorStructure(usersDto.getPhoneNumber(), Constants.INVALID_PHONE_NUMBER, Constants.PHONE_NUMBER);
                errors.add(errorStructure);
            } else if (usersRepository.findByPhoneNumber(usersDto.getPhoneNumber()).isPresent()) {
                errorStructure = new ErrorStructure(usersDto.getPhoneNumber(), Constants.PHONE_NUMBER_ALREADY_EXIST, Constants.PHONE_NUMBER);
                errors.add(errorStructure);
            } else {
                user.setPhoneNumber(usersDto.getPhoneNumber());
                userModel.setPhoneNumber(usersDto.getPhoneNumber());
            }

            if (usersDto.getDateOfBirth() == null) {
                errorStructure = new ErrorStructure(null, Constants.DATE_OF_BIRTH_SHOULD_NOT_BE_NULL, Constants.DATE_OF_BIRTH);
                errors.add(errorStructure);
            } else {
                user.setDateOfBirth(usersDto.getDateOfBirth());
            }

            user.setGender(usersDto.getGender());

            AddressInformation addressInformation = new AddressInformation();

            if (usersDto.getAddressLine1() == null || usersDto.getAddressLine1().length() < Constants.MIN_VALUE) {
                errorStructure = new ErrorStructure(usersDto.getAddressLine1(), Constants.ADDRESS_LINE_1_ERROR_MESSAGE, Constants.ADDRESS_LINE_1);
                errors.add(errorStructure);
            } else {
                user.setAddressLine1(usersDto.getAddressLine1());
                addressInformation.setAddressLine1(usersDto.getAddressLine1());
            }

            user.setAddressLine2(usersDto.getAddressLine2());
            addressInformation.setAddressLine2(usersDto.getAddressLine2());

            if (usersDto.getCity() == null || usersDto.getCity().isEmpty()) {
                errorStructure = new ErrorStructure(usersDto.getCity(), Constants.CITY_SHOULD_NOT_BE_EMPTY, Constants.CITY);
                errors.add(errorStructure);
            } else {
                var cityDoc = cityStateLocationRepository.findById(new ObjectId(usersDto.getCity()))
                        .orElseThrow(() -> new NoSuchElementException("City not found"));
                user.setCityId(cityDoc.getErpId());
                addressInformation.setCity(usersDto.getCity());
            }

            if (usersDto.getState() == null || usersDto.getState().isEmpty()) {
                errorStructure = new ErrorStructure(usersDto.getState(), Constants.STATE_SHOULD_NOT_BE_EMPTY, Constants.STATE);
                errors.add(errorStructure);
            } else {
                var stateDoc = cityStateLocationRepository.findById(new ObjectId(usersDto.getState()))
                        .orElseThrow((() -> new NoSuchElementException("State Not found")));
                user.setStateId(stateDoc.getErpId());
                addressInformation.setState(usersDto.getState());
            }

            if (usersDto.getCountry() == null || usersDto.getCountry().isEmpty()) {
                errorStructure = new ErrorStructure(usersDto.getCountry(), Constants.COUNTRY_SHOULD_NOT_BE_EMPTY, Constants.COUNTRY);
                errors.add(errorStructure);
            } else {
                var countryDoc = cityStateLocationRepository.findById(new ObjectId(usersDto.getCountry()))
                        .orElseThrow((() -> new NoSuchElementException("Country Not found")));
                user.setCountryId(countryDoc.getErpId());
                addressInformation.setCountry(usersDto.getCountry());
            }

            user.setPinCode(Integer.valueOf(usersDto.getPinCode()));
            addressInformation.setPinCode(Integer.valueOf(usersDto.getPinCode()));

            userModel.setAddressInformation(addressInformation);

            user.setActive(true);
            userModel.setActive(true);

            user.setStatus(Constants.ACTIVE);
            userModel.setStatus(Constants.ACTIVE);

            user.setCreatedAt(new Date());
            userModel.setCreatedAt(new Date());

            user.setUpdatedAt(new Date());
            userModel.setUpdatedAt(new Date());

            if (!errors.isEmpty()) {
                response = new ResponseDTO(Boolean.FALSE, errors, HttpStatus.BAD_REQUEST.value(), Constants.FAILED);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response, httpStatus);
            }

            Users erpUser = usersRepository.save(user);

            userModel.setUserId(erpUser.getId());

            userModelRepository.save(userModel);

            response = new ResponseDTO(Boolean.TRUE, Constants.ADDED_SUCCESS, HttpStatus.OK.value(), Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_VALIDATE_DATA, e);
            response = new ResponseDTO(Boolean.FALSE, HttpStatus.UNPROCESSABLE_ENTITY.value(), Constants.UNABLE_TO_VALIDATE_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUser(UsersUpdateDto usersUpdateDto) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {

            ErrorStructure errorStructure;
            var errors = new ArrayList<>();

            List<UsersAudit> auditList = new ArrayList<>();

            if (usersUpdateDto.getId() == null || usersUpdateDto.getId().isEmpty()){
                errorStructure = new ErrorStructure(usersUpdateDto.getId(),Constants.ID_SHOULD_NOT_BE_EMPTY,Constants.ID);
                errors.add(errorStructure);
            }

            UserModel userModel = userModelRepository.findById(new ObjectId(usersUpdateDto.getId()))
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_ID_USER_NOT_FOUND));

            Users erpUser = usersRepository.findById(userModel.getUserId())
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_USER_ID_USER_NOT_FOUND));

            String from = "", to = "";

            String updatedBy = "";

            if (usersUpdateDto.getFirstName() == null || usersUpdateDto.getFirstName().isEmpty()) {
                errorStructure = new ErrorStructure(usersUpdateDto.getFirstName(), Constants.FIRST_NAME_SHOULD_NOT_BE_EMPTY, Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (usersUpdateDto.getFirstName().length() < Constants.MIN_VALUE) {
                errorStructure = new ErrorStructure(usersUpdateDto.getFirstName(), Constants.FIRST_NAME_LESS_MESSAGE, Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (usersUpdateDto.getFirstName().equals(userModelRepository.findByName(usersUpdateDto.getFirstName()).get().getFirstName())) {
                errorStructure = new ErrorStructure(usersUpdateDto.getFirstName(), Constants.DUPLICATE_NAME, Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getFirstName().equals(userModel.getFirstName())) {

                from = userModel.getFirstName();
                to = usersUpdateDto.getFirstName();

                UsersAudit auditHistory = createAuditHistory(Constants.FIRST_NAME, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                userModel.setFirstName(usersUpdateDto.getFirstName());
                erpUser.setFirstName(usersUpdateDto.getFirstName());
            }

            if (!usersUpdateDto.getLastName().equals(userModel.getLastName())) {
                from = userModel.getLastName();
                to = usersUpdateDto.getLastName();

                UsersAudit usersAudit = createAuditHistory(Constants.LAST_NAME, Constants.USER, from, to, updatedBy);
                auditList.add(usersAudit);

                userModel.setLastName(usersUpdateDto.getLastName());
                erpUser.setLastName(usersUpdateDto.getLastName());
            }

            if (usersUpdateDto.getEmail() == null || usersUpdateDto.getEmail().isEmpty() || !Constants.EMAIL_PATTERN.matcher(usersUpdateDto.getEmail()).matches()) {
                errorStructure = new ErrorStructure(usersUpdateDto.getEmail(), Constants.INVALID_EMAIL, Constants.EMAIL);
                errors.add(errorStructure);
            } else if (usersUpdateDto.getEmail().equals(usersRepository.findByEmail(usersUpdateDto.getEmail()).get().getEmail())) {
                errorStructure = new ErrorStructure(usersUpdateDto.getEmail(), Constants.EMAIL_ALREADY_EXIST, Constants.EMAIL);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getEmail().equals(userModel.getEmail())) {

                from = userModel.getEmail();
                to = usersUpdateDto.getEmail();

                UsersAudit auditHistory = createAuditHistory(Constants.EMAIL, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setEmail(usersUpdateDto.getEmail());
                userModel.setEmail(usersUpdateDto.getEmail());
            }

            if (usersUpdateDto.getPhoneNumber() == null || usersUpdateDto.getPhoneNumber().length() != 10) {
                errorStructure = new ErrorStructure(usersUpdateDto.getPhoneNumber(), Constants.INVALID_PHONE_NUMBER, Constants.PHONE_NUMBER);
                errors.add(errorStructure);
            } else if (usersUpdateDto.getPhoneNumber().equals(usersRepository.findByPhoneNumber(usersUpdateDto.getPhoneNumber()).get().getPhoneNumber())) {
                errorStructure = new ErrorStructure(usersUpdateDto.getPhoneNumber(), Constants.PHONE_NUMBER_ALREADY_EXIST, Constants.PHONE_NUMBER);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getPhoneNumber().equals(userModel.getPhoneNumber())) {

                from = userModel.getPhoneNumber();
                to = usersUpdateDto.getPhoneNumber();

                UsersAudit auditHistory = createAuditHistory(Constants.PHONE_NUMBER, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setPhoneNumber(usersUpdateDto.getPhoneNumber());
                userModel.setPhoneNumber(usersUpdateDto.getPhoneNumber());
            }

            //after authentication need to check Admin role for change DateOfBirth
            if (usersUpdateDto.getDateOfBirth() == null) {
                errorStructure = new ErrorStructure(null, Constants.DATE_OF_BIRTH_SHOULD_NOT_BE_NULL, Constants.DATE_OF_BIRTH);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getDateOfBirth().equals(erpUser.getDateOfBirth())) {
                erpUser.setDateOfBirth(usersUpdateDto.getDateOfBirth());
            }

            if (!usersUpdateDto.getGender().equals(erpUser.getGender()))
                erpUser.setGender(usersUpdateDto.getGender());

            AddressInformation addressInformation = userModel.getAddressInformation();

            if (usersUpdateDto.getAddressLine1() == null || usersUpdateDto.getAddressLine1().length() < Constants.MIN_VALUE) {
                errorStructure = new ErrorStructure(usersUpdateDto.getAddressLine1(), Constants.ADDRESS_LINE_1_ERROR_MESSAGE, Constants.ADDRESS_LINE_1);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getAddressLine1().equals(addressInformation.getAddressLine1())) {

                from = addressInformation.getAddressLine1();
                to = usersUpdateDto.getAddressLine1();

                UsersAudit auditHistory = createAuditHistory(Constants.ADDRESS_LINE_1, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setAddressLine1(usersUpdateDto.getAddressLine1());
                addressInformation.setAddressLine1(usersUpdateDto.getAddressLine1());
            }

            if (usersUpdateDto.getAddressLine2() == null || !usersUpdateDto.getAddressLine2().equals(userModel.getAddressInformation().getAddressLine2())) {

                from = addressInformation.getAddressLine2() != null ? addressInformation.getAddressLine2() : "";
                to = usersUpdateDto.getAddressLine2() != null ? usersUpdateDto.getAddressLine2() : "";

                UsersAudit auditHistory = createAuditHistory(Constants.ADDRESS_LINE_2, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setAddressLine2(usersUpdateDto.getAddressLine2());
                addressInformation.setAddressLine2(usersUpdateDto.getAddressLine2());
            }

            if (usersUpdateDto.getCity() == null || usersUpdateDto.getCity().isEmpty()) {
                errorStructure = new ErrorStructure(usersUpdateDto.getCity(), Constants.CITY_SHOULD_NOT_BE_EMPTY, Constants.CITY);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getCity().equals(addressInformation.getCity())) {

                var newCityDoc = cityStateLocationRepository.findById(new ObjectId(usersUpdateDto.getCity()))
                        .orElseThrow(() -> new NoSuchElementException("City not found"));

                var oldCityDoc = cityStateLocationRepository.findById(new ObjectId(addressInformation.getCity()));

                from = oldCityDoc.get().getCityName();
                to = newCityDoc.getCityName();

                UsersAudit auditHistory = createAuditHistory(Constants.CITY, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setCityId(newCityDoc.getErpId());
                addressInformation.setCity(usersUpdateDto.getCity());
            }

            if (usersUpdateDto.getState() == null || usersUpdateDto.getState().isEmpty()) {
                errorStructure = new ErrorStructure(usersUpdateDto.getState(), Constants.STATE_SHOULD_NOT_BE_EMPTY, Constants.STATE);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getState().equals(addressInformation.getState())) {
                var newStateDoc = cityStateLocationRepository.findById(new ObjectId(usersUpdateDto.getState()))
                        .orElseThrow((() -> new NoSuchElementException("State Not found")));

                var oldStateDoc = cityStateLocationRepository.findById(new ObjectId(addressInformation.getState()));

                from = oldStateDoc.get().getStateName();
                to = newStateDoc.getStateName();

                UsersAudit auditHistory = createAuditHistory(Constants.STATE, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setStateId(newStateDoc.getErpId());
                addressInformation.setState(usersUpdateDto.getState());
            }

            if (usersUpdateDto.getCountry() == null || usersUpdateDto.getCountry().isEmpty()) {
                errorStructure = new ErrorStructure(usersUpdateDto.getCountry(), Constants.COUNTRY_SHOULD_NOT_BE_EMPTY, Constants.COUNTRY);
                errors.add(errorStructure);
            } else if (!usersUpdateDto.getCountry().equals(addressInformation.getCountry())) {
                var newCountryDoc = cityStateLocationRepository.findById(new ObjectId(usersUpdateDto.getCountry()))
                        .orElseThrow((() -> new NoSuchElementException("Country Not found")));

                var oldCountryCode = cityStateLocationRepository.findById(new ObjectId(addressInformation.getCountry()));

                from = oldCountryCode.get().getCountryName();
                to = newCountryDoc.getCountryName();

                UsersAudit auditHistory = createAuditHistory(Constants.COUNTRY, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setCountryId(newCountryDoc.getErpId());
                addressInformation.setState(usersUpdateDto.getCountry());
            }

            if (!Integer.valueOf(usersUpdateDto.getPinCode()).equals(addressInformation.getPinCode())) {

                from = addressInformation.getPinCode() != null ? String.valueOf(addressInformation.getPinCode()) : "";
                to = usersUpdateDto.getPinCode() != null ? usersUpdateDto.getPinCode() : "";

                UsersAudit auditHistory = createAuditHistory(Constants.PIN_CODE, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setPinCode(Integer.valueOf(usersUpdateDto.getPinCode()));
                addressInformation.setPinCode(Integer.valueOf(usersUpdateDto.getPinCode()));
            }

            userModel.setAddressInformation(addressInformation);

            erpUser.setUpdatedAt(new Date());
            userModel.setUpdatedAt(new Date());

            if (!errors.isEmpty()) {
                response = new ResponseDTO(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), Constants.UNABLE_TO_UPDATE_USER);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response, httpStatus);
            }

            if (!auditList.isEmpty()){
                userAuditRepository.saveAll(auditList);
            }

            usersRepository.save(erpUser);

            userModelRepository.save(userModel);

            response = new ResponseDTO(Boolean.TRUE, Constants.UPDATED_SUCCESS, HttpStatus.OK.value(), Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_UPDATE_USER, e);

            response = new ResponseDTO(Boolean.FALSE, HttpStatus.UNPROCESSABLE_ENTITY.value(), Constants.UNABLE_TO_UPDATE_USER);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return new ResponseEntity<>(response, httpStatus);
    }


    @Override
    public ResponseEntity<ResponseDTO> addAdditionalDetails(AdditionalDetailsDto additionalDetailsDto) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {

            ErrorStructure errorStructure;
            var errors = new ArrayList<>();

            UserModel userModel = userModelRepository.findById(additionalDetailsDto.getId())
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_ID_USER_NOT_FOUND));

            Users erpUser = usersRepository.findById(userModel.getUserId()).get();

            Salary salary = new Salary();

            AdditionalDetails additionalDetails = userModel.getAdditionalDetails();

            if (additionalDetails == null) {
                additionalDetails = new AdditionalDetails();
            }

            JobModel jobModel = new JobModel();

            if (additionalDetailsDto.getDesignation() == null || additionalDetailsDto.getDesignation().isEmpty()) {
                errorStructure = new ErrorStructure(additionalDetailsDto.getDesignation(), Constants.DESIGNATION_SHOULD_NOT_BE_EMPTY, Constants.DESIGNATION);
                errors.add(errorStructure);
            } else {
                jobModel.setDesignation(additionalDetailsDto.getDesignation());
            }

            if (additionalDetailsDto.getJoiningDate() == null || String.valueOf(additionalDetailsDto.getJoiningDate()).isEmpty()) {
                errorStructure = new ErrorStructure(String.valueOf(additionalDetailsDto.getJoiningDate()), Constants.JOINING_DATE_SHOULD_NOT_BE_EMPTY, Constants.JOINING_DATE);
                errors.add(errorStructure);
            } else {
                jobModel.setJoiningDate(additionalDetailsDto.getJoiningDate());
            }

            if (additionalDetailsDto.getEmploymentType() == null || additionalDetailsDto.getEmploymentType().isEmpty()) {
                errorStructure = new ErrorStructure(additionalDetailsDto.getEmploymentType(), Constants.EMPLOYEE_TYPE_SHOULD_NOT_BE_EMPTY, Constants.EMPLOYEE_TYPE);
                errors.add(errorStructure);
            } else {
                jobModel.setEmploymentType(additionalDetailsDto.getEmploymentType());
            }

            additionalDetails.setJobDetails(jobModel);

            if (additionalDetailsDto.getSkills() == null || additionalDetailsDto.getSkills().isEmpty()) {
                errorStructure = new ErrorStructure(List.of().toString(), Constants.SKILLS_SHOULD_NOT_BE_EMPTY, Constants.SKILLS);
                errors.add(errorStructure);
            } else {
                additionalDetails.setSkills(additionalDetailsDto.getSkills());
            }

            additionalDetails.setExperienceYears(Integer.valueOf(additionalDetailsDto.getExperienceYears()));

            additionalDetails.setCertifications(additionalDetailsDto.getCertifications());

            if (additionalDetailsDto.getSalary() == null || additionalDetailsDto.getSalary().isEmpty()) {
                errorStructure = new ErrorStructure(additionalDetailsDto.getSalary(), Constants.SALARY_MESSAGE, Constants.SALARY);
                errors.add(errorStructure);
            } else {
                salary.setSalary(Double.valueOf(additionalDetailsDto.getSalary()));
            }

            salary.setSalaryType(additionalDetailsDto.getSalaryType());

            if (additionalDetailsDto.getBankAccountNumber() == null || additionalDetailsDto.getBankAccountNumber().isEmpty()) {
                errorStructure = new ErrorStructure(additionalDetailsDto.getBankAccountNumber(), Constants.BANK_NUMBER_MESSAGE, Constants.BANK_NUMBER);
                errors.add(errorStructure);
            } else if (additionalDetailsDto.getBankAccountNumber().length() < 9) {
                errorStructure = new ErrorStructure(additionalDetailsDto.getBankAccountNumber(),Constants.BANK_NUMBER_MESSAGE_MIN_LENGTH,Constants.BANK_NUMBER);
                errors.add(errorStructure);
            } else {
                salary.setBankAccountNumber(additionalDetailsDto.getBankAccountNumber());
            }

            if (additionalDetailsDto.getIfscCode() == null || additionalDetailsDto.getIfscCode().isEmpty()){
                errorStructure = new ErrorStructure(additionalDetailsDto.getIfscCode(), Constants.IFSC_CODE_SHOULD_NOT_BE_EMPTY,Constants.IFSC_CODE);
                errors.add(errorStructure);
            } else if (additionalDetailsDto.getIfscCode().length() != 11 || !Constants.PATTERN_IFSC.matcher(additionalDetailsDto.getIfscCode()).matches()){
                errorStructure = new ErrorStructure(additionalDetailsDto.getIfscCode(), Constants.INVALID_IFSC,Constants.IFSC_CODE);
                errors.add(errorStructure);
            } else {
                salary.setIfscCode(additionalDetailsDto.getIfscCode());
            }

            salary.setPfNumber(additionalDetailsDto.getPfNumber());

            if (!errors.isEmpty()){
                response = new ResponseDTO(Boolean.FALSE,HttpStatus.BAD_REQUEST.value(),Constants.UNABLE_TO_VALIDATE_DATA);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response,httpStatus);
            }

            Salary save = salaryRepository.save(salary);

            userModel.setAdditionalDetails(additionalDetails);

            erpUser.setSalaryId(save.getId());

            erpUser.setDepartmentId(Integer.valueOf(additionalDetailsDto.getDepartmentId()));

            userModelRepository.save(userModel);

            usersRepository.save(erpUser);

            response = new ResponseDTO(Boolean.TRUE,Constants.ADDITIONAL_DETAILS_ADDED,HttpStatus.OK.value(), Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_VALIDATE_DATA,e);

            response = new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.UNABLE_TO_VALIDATE_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return new ResponseEntity<>(response,httpStatus);
    }

    @Override
    public ResponseEntity<ResponseDTO> updateAdditionalDetails(AdditionDetailsUpdateDto additionDetailsUpdateDto) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {

            ErrorStructure errorStructure;
            var errors = new ArrayList<>();

            if (additionDetailsUpdateDto.getId() == null || additionDetailsUpdateDto.getId().isEmpty()){
                errorStructure = new ErrorStructure(additionDetailsUpdateDto.getId(), Constants.ID_SHOULD_NOT_BE_EMPTY,Constants.ID);
                errors.add(errorStructure);
            }

            UserModel userModel = userModelRepository.findById(additionDetailsUpdateDto.getId())
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_ID_USER_NOT_FOUND));

            Users erpUser = usersRepository.findById(userModel.getUserId()).get();

            AdditionalDetails additionalDetails = userModel.getAdditionalDetails();

            JobModel jobDetails = additionalDetails.getJobDetails();

            Optional<Salary> salary = salaryRepository.findById(erpUser.getSalaryId());

            Salary salaryDetails = null;

            if (additionDetailsUpdateDto.getDesignation() == null || additionDetailsUpdateDto.getDesignation().isEmpty()) {
                errorStructure = new ErrorStructure(additionDetailsUpdateDto.getDesignation(), Constants.DESIGNATION_SHOULD_NOT_BE_EMPTY, Constants.DESIGNATION);
                errors.add(errorStructure);
            } else if (!additionDetailsUpdateDto.getDesignation().equals(jobDetails.getDesignation())){
                jobDetails.setDesignation(additionDetailsUpdateDto.getDesignation());
            }

            if (additionDetailsUpdateDto.getJoiningDate() == null || String.valueOf(additionDetailsUpdateDto.getJoiningDate()).isEmpty()) {
                errorStructure = new ErrorStructure(String.valueOf(additionDetailsUpdateDto.getJoiningDate()), Constants.JOINING_DATE_SHOULD_NOT_BE_EMPTY, Constants.JOINING_DATE);
                errors.add(errorStructure);
            } else if (!additionDetailsUpdateDto.getJoiningDate().equals(jobDetails.getJoiningDate())){
                jobDetails.setJoiningDate(additionDetailsUpdateDto.getJoiningDate());
            }

            if (additionDetailsUpdateDto.getEmploymentType() == null || additionDetailsUpdateDto.getEmploymentType().isEmpty()) {
                errorStructure = new ErrorStructure(additionDetailsUpdateDto.getEmploymentType(), Constants.EMPLOYEE_TYPE_SHOULD_NOT_BE_EMPTY, Constants.EMPLOYEE_TYPE);
                errors.add(errorStructure);
            } else if (!additionDetailsUpdateDto.getEmploymentType().equals(jobDetails.getEmploymentType())){
                jobDetails.setEmploymentType(additionDetailsUpdateDto.getEmploymentType());
            }

            additionalDetails.setJobDetails(jobDetails);

            if (additionDetailsUpdateDto.getSkills() == null || additionDetailsUpdateDto.getSkills().isEmpty()) {
                errorStructure = new ErrorStructure(List.of().toString(), Constants.SKILLS_SHOULD_NOT_BE_EMPTY, Constants.SKILLS);
                errors.add(errorStructure);
            } else if (!additionDetailsUpdateDto.getSkills().equals(additionalDetails.getSkills())){
                additionalDetails.setSkills(additionDetailsUpdateDto.getSkills());
            }

            if (!additionDetailsUpdateDto.getExperienceYears().equals(additionalDetails.getExperienceYears().toString()))
                additionalDetails.setExperienceYears(Integer.valueOf(additionDetailsUpdateDto.getExperienceYears()));

            if (!additionDetailsUpdateDto.getCertifications().equals(additionalDetails.getCertifications()))
                additionalDetails.setCertifications(additionDetailsUpdateDto.getCertifications());

            if (salary.isPresent()) {

                salaryDetails = salary.get();

                if (additionDetailsUpdateDto.getSalary() == null || additionDetailsUpdateDto.getSalary().isEmpty()) {
                    errorStructure = new ErrorStructure(additionDetailsUpdateDto.getSalary(), Constants.SALARY_MESSAGE, Constants.SALARY);
                    errors.add(errorStructure);
                } else if (!additionDetailsUpdateDto.getSalary().equals(salaryDetails.getSalary().toString())){
                    salaryDetails.setSalary(Double.valueOf(additionDetailsUpdateDto.getSalary()));
                }

                if (!additionDetailsUpdateDto.getSalaryType().equals(salaryDetails.getSalaryType()))
                    salaryDetails.setSalaryType(additionDetailsUpdateDto.getSalaryType());

                if (additionDetailsUpdateDto.getBankAccountNumber() == null || additionDetailsUpdateDto.getBankAccountNumber().isEmpty()) {
                    errorStructure = new ErrorStructure(additionDetailsUpdateDto.getBankAccountNumber(), Constants.BANK_NUMBER_MESSAGE, Constants.BANK_NUMBER);
                    errors.add(errorStructure);
                } else if (additionDetailsUpdateDto.getBankAccountNumber().length() < 9) {
                    errorStructure = new ErrorStructure(additionDetailsUpdateDto.getBankAccountNumber(), Constants.BANK_NUMBER_MESSAGE_MIN_LENGTH, Constants.BANK_NUMBER);
                    errors.add(errorStructure);
                } else if (additionDetailsUpdateDto.getBankAccountNumber().equals(salaryDetails.getBankAccountNumber())){
                    salaryDetails.setBankAccountNumber(additionDetailsUpdateDto.getBankAccountNumber());
                }

                if (additionDetailsUpdateDto.getIfscCode() == null || additionDetailsUpdateDto.getIfscCode().isEmpty()) {
                    errorStructure = new ErrorStructure(additionDetailsUpdateDto.getIfscCode(), Constants.IFSC_CODE_SHOULD_NOT_BE_EMPTY, Constants.IFSC_CODE);
                    errors.add(errorStructure);
                } else if (additionDetailsUpdateDto.getIfscCode().length() != 11 || !Constants.PATTERN_IFSC.matcher(additionDetailsUpdateDto.getIfscCode()).matches()) {
                    errorStructure = new ErrorStructure(additionDetailsUpdateDto.getIfscCode(), Constants.INVALID_IFSC, Constants.IFSC_CODE);
                    errors.add(errorStructure);
                } else if (!additionDetailsUpdateDto.getIfscCode().equals(salaryDetails.getIfscCode())){
                    salaryDetails.setIfscCode(additionDetailsUpdateDto.getIfscCode());
                }

                if (!additionDetailsUpdateDto.getPfNumber().equals(salaryDetails.getPfNumber()))
                    salaryDetails.setPfNumber(additionDetailsUpdateDto.getPfNumber());
            }

            userModel.setAdditionalDetails(additionalDetails);

            if (!errors.isEmpty()){
                response = new ResponseDTO(Boolean.FALSE,HttpStatus.BAD_REQUEST.value(),Constants.UNABLE_TO_UPDATE_DATA);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response,httpStatus);
            }

            if (salaryDetails != null)
                salaryRepository.save(salaryDetails);

            userModelRepository.save(userModel);

            usersRepository.save(erpUser);

            response = new ResponseDTO(Boolean.TRUE,Constants.UPDATED_SUCCESS,HttpStatus.OK.value(), Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_UPDATE_DATA);

            response = new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.UNABLE_TO_UPDATE_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return new ResponseEntity<>(response,httpStatus);
    }

    @Override
    public ResponseEntity<ResponseDTO> addEducationDetails(EducationDto educationDto) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {

            ErrorStructure errorStructure;
            var errors = new ArrayList<>();

            if (educationDto.getId() == null || educationDto.getId().isEmpty()){
                errorStructure = new ErrorStructure(educationDto.getId(), Constants.ID_SHOULD_NOT_BE_EMPTY,Constants.ID);
                errors.add(errorStructure);
            }
            UserModel userModel = userModelRepository.findById(educationDto.getId())
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_ID_USER_NOT_FOUND));

            AdditionalDetails additionalDetails = userModel.getAdditionalDetails();

            if (additionalDetails == null){
                additionalDetails = new AdditionalDetails();
            }

            List<EducationDetails> educationDetails = additionalDetails.getEducationDetails();

            EducationDetails educationDetail = new EducationDetails();

            if (educationDetails != null && educationDetails.size() > 4) {
                errorStructure = new ErrorStructure(educationDetails.toString(), Constants.EDUCATION_DETAILS_REACHED_MAX_LIMIT, Constants.EDUCATION_DETAILS);
                errors.add(errorStructure);
            }

            if (educationDto.getEducationLevel() == null || educationDto.getEducationLevel().isEmpty()){
                errorStructure = new ErrorStructure(educationDto.getEducationLevel(), Constants.EDUCATIONAL_LEVEL_MESSAGE,Constants.EDUCATION_LEVEL);
                errors.add(errorStructure);
            } else {
                if (educationDto.getEducationLevel().equals(Constants.SSLC)) {
                    if (educationDto.getMedium() != null) {
                        educationDetail.setMedium(educationDto.getMedium());
                    }

                    if (educationDto.getPercentage() == null || educationDto.getPercentage().isEmpty()){
                        errorStructure = new ErrorStructure(educationDto.getPercentage(), Constants.PERCENTAGE_SHOULD_NOT_BE_EMPTY,Constants.PERCENTAGE);
                        errors.add(errorStructure);
                    } else {
                        educationDetail.setPercentage(Double.valueOf(educationDto.getPercentage()));
                    }

                } else {
                    educationDetail.setStream(educationDto.getStream());

                    if (educationDto.getGrade() == null || educationDto.getGrade().isEmpty()){
                        errorStructure = new ErrorStructure(educationDto.getGrade(), Constants.GRADE_SHOULD_NOT_BE_EMPTY,Constants.GRADE);
                        errors.add(errorStructure);
                    } else {
                        educationDetail.setGrade(educationDto.getGrade());
                    }
                }
                educationDetail.setEducationLevel(educationDto.getEducationLevel());
            }
            educationDetail.setBoardName(educationDto.getBoardName());

            educationDetail.setUniversity(educationDto.getUniversity());

            if (educationDto.getSchoolOrCollegeName() == null || educationDto.getSchoolOrCollegeName().isEmpty()){
                errorStructure = new ErrorStructure(educationDto.getSchoolOrCollegeName(), Constants.SCHOOL_OR_COLLEGE_NAME_SHOULD_NOT_BE_EMPTY,Constants.SCHOOL_OR_COLLEGE_NAME);
                errors.add(errorStructure);
            } else {
                educationDetail.setSchoolOrCollegeName(educationDto.getSchoolOrCollegeName());
            }

            if (educationDto.getLocation() == null || educationDto.getLocation().isEmpty()){
                errorStructure = new ErrorStructure(educationDto.getLocation(), Constants.LOCATION_SHOULD_NOT_BE_EMPTY,Constants.LOCATION);
                errors.add(errorStructure);
            } else {
                educationDetail.setLocation(educationDto.getLocation());
            }

            if (educationDto.getPassingYear() == null || educationDto.getPassingYear().isEmpty()){
                errorStructure = new ErrorStructure(educationDto.getPassingYear(), Constants.PASSING_YEAR_SHOULD_NOT_BE_EMPTY,Constants.PASSING_YEAR);
                errors.add(errorStructure);
            } else {
                educationDetail.setPassOut(educationDto.getPassingYear());
            }

            if (!errors.isEmpty()){
                response = new ResponseDTO(Boolean.FALSE,HttpStatus.BAD_REQUEST.value(),Constants.UNABLE_TO_UPDATE_DATA);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response,httpStatus);
            }

            educationDetails.add(educationDetail);

            additionalDetails.setEducationDetails(educationDetails);

            userModel.setAdditionalDetails(additionalDetails);

            userModelRepository.save(userModel);

            response = new ResponseDTO(Boolean.TRUE,Constants.UPDATED_SUCCESS,HttpStatus.OK.value(), Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_UPDATE_DATA,e);

            response = new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.UNABLE_TO_UPDATE_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return new ResponseEntity<>(response,httpStatus);
    }

    @Override
    public ResponseEntity<ResponseDTO> getUserByPhone(String phone) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {
            Users userModel = usersRepository.findByPhoneNumber(phone)
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_PHONE_USER_NOT_FOUND));

            return new ResponseEntity<>(new ResponseDTO(Boolean.TRUE,userModel,HttpStatus.OK.value(),Constants.SUCCESS),HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.INVALID_PHONE_USER_NOT_FOUND),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @Override
    public String addLocation(List<CityStateLocationDto> cityStateLocations) {

        cityStateLocations.forEach(
                cityStateLocationDto -> {
                    CityStateLocation cityStateLocation = new CityStateLocation();
                    Location location = new Location();

                    cityStateLocation.setCityName(cityStateLocationDto.getCityName());
                    location.setCityName(cityStateLocationDto.getCityName());

                    cityStateLocation.setStateName(cityStateLocationDto.getStateName());
                    location.setStateName(cityStateLocationDto.getStateName());

                    cityStateLocation.setStateIsoCode(cityStateLocationDto.getStateIsoCode());
                    location.setStateIsoCode(cityStateLocationDto.getStateIsoCode());

                    cityStateLocation.setCountryCode(cityStateLocationDto.getCountryCode());
                    location.setCountryCode(cityStateLocationDto.getCountryCode());

                    cityStateLocation.setCountryName(cityStateLocationDto.getCountryName());
                    location.setCountryName(cityStateLocationDto.getCountryName());

                    cityStateLocation.setTimeZone(cityStateLocationDto.getTimeZone());
                    location.setTimeZone(cityStateLocationDto.getTimeZone());

                    cityStateLocation.setType(cityStateLocationDto.getType());
                    location.setType(cityStateLocationDto.getType());

                    Location save = locationRepository.save(location);

                    cityStateLocation.setErpId(save.getId());

                    cityStateLocationRepository.save(cityStateLocation);

                }
        );
        return "Success";
    }

    protected UsersAudit createAuditHistory(String param, String type, String from, String to, String updatedBy) {

        UsersAudit usersAudit = new UsersAudit();

        usersAudit.setParam(param);
        usersAudit.setType(type);
        usersAudit.setFrom(from);
        usersAudit.setTo(to);
        usersAudit.setUpdatedBy(updatedBy);
        usersAudit.setCreatedAt(new Date());
        usersAudit.setUpdatedAt(new Date());

        return usersAudit;
    }


}
