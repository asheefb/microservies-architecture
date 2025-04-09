package com.asheef.users.service.service_impl;

import com.asheef.common_model_mdb.model.CityStateLocation;
import com.asheef.common_model_mdb.model.employee.AdditionalDetails;
import com.asheef.common_model_mdb.model.employee.AddressInformation;
import com.asheef.common_model_mdb.model.employee.UserModel;
import com.asheef.common_model_mdb.model.employee.UsersAudit;
import com.asheef.common_model_mdb.model.utils.ErrorStructure;
import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.common_model_mdb.repository.CityStateLocationRepository;
import com.asheef.common_model_mdb.repository.UserModelRepository;
import com.asheef.common_model_ms.model.Location;
import com.asheef.common_model_ms.model.employee.Users;
import com.asheef.common_model_ms.repository.LocationRepository;
import com.asheef.common_model_ms.repository.UsersRepository;
import com.asheef.users.service.constants.Constants;
import com.asheef.users.service.dto.CityStateLocationDto;
import com.asheef.users.service.dto.UsersDto;
import com.asheef.users.service.dto.UsersUpdateDto;
import com.asheef.users.service.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    @Autowired
    private final UserModelRepository userModelRepository;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final CityStateLocationRepository cityStateLocationRepository;

    @Autowired
    private final LocationRepository locationRepository;

    public UsersServiceImpl(UserModelRepository userModelRepository, UsersRepository usersRepository, CityStateLocationRepository cityStateLocationRepository, LocationRepository locationRepository) {
        this.userModelRepository = userModelRepository;
        this.usersRepository = usersRepository;
        this.cityStateLocationRepository = cityStateLocationRepository;
        this.locationRepository = locationRepository;
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
            } else if (usersDto.getFirstName().equals(userModelRepository.findByName(usersDto.getFirstName()).get().getFirstName())) {
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
            } else if (usersDto.getEmail().equals(usersRepository.findByEmail(usersDto.getEmail()).get().getEmail())) {
                errorStructure = new ErrorStructure(usersDto.getEmail(), Constants.EMAIL_ALREADY_EXIST, Constants.EMAIL);
                errors.add(errorStructure);
            } else {
                user.setEmail(usersDto.getEmail());
                userModel.setEmail(usersDto.getEmail());
            }

            if (usersDto.getPhoneNumber() == null || usersDto.getPhoneNumber().length() != 10) {
                errorStructure = new ErrorStructure(usersDto.getPhoneNumber(), Constants.INVALID_PHONE_NUMBER, Constants.PHONE_NUMBER);
                errors.add(errorStructure);
            } else if (usersDto.getPhoneNumber().equals(usersRepository.findByPhoneNumber(usersDto.getPhoneNumber()).get().getPhoneNumber())) {
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
                addressInformation.setState(usersDto.getCountry());
            }

            user.setPinCode(Integer.valueOf(usersDto.getPinCode()));
            addressInformation.setPinCode(Integer.valueOf(usersDto.getPinCode()));

            userModel.setAddressInformation(addressInformation);

            user.setActive(true);
            userModel.setActive(true);

            user.setStatus(Constants.ACTIVE);
            user.setStatus(Constants.ACTIVE);

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

            var auditList = new ArrayList<>();

            UserModel userModel = userModelRepository.findById(usersUpdateDto.getId())
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
            } else if (!usersUpdateDto.getAddressLine1().equals(userModel.getAddressInformation().getAddressLine1())){

                from = userModel.getAddressInformation().getAddressLine1();
                to = usersUpdateDto.getAddressLine1();

                UsersAudit auditHistory = createAuditHistory(Constants.ADDRESS_LINE_1, Constants.USER, from, to, updatedBy);
                auditList.add(auditHistory);

                erpUser.setAddressLine1(usersUpdateDto.getAddressLine1());
                addressInformation.setAddressLine1(usersUpdateDto.getAddressLine1());
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
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

    private UsersAudit createAuditHistory(String param,String type, String from, String to, String updatedBy) {

        UsersAudit usersAudit = new UsersAudit();

        usersAudit.setParam(param);
        usersAudit.setFrom(from);
        usersAudit.setTo(to);
        usersAudit.setUpdatedBy(updatedBy);
        usersAudit.setCreatedAt(new Date());
        usersAudit.setUpdatedAt(new Date());

        return usersAudit;
    }
}
