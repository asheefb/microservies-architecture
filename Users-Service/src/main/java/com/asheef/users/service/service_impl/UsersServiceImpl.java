package com.asheef.users.service.service_impl;

import com.asheef.common_model_mdb.model.employee.AddressInformation;
import com.asheef.common_model_mdb.model.employee.UserModel;
import com.asheef.common_model_mdb.model.utils.ErrorStructure;
import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.common_model_mdb.repository.CityStateLocationRepository;
import com.asheef.common_model_mdb.repository.UserModelRepository;
import com.asheef.common_model_ms.model.employee.Users;
import com.asheef.users.service.constants.Constants;
import com.asheef.users.service.dto.UsersDto;
import com.asheef.users.service.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    @Autowired
    private final UserModelRepository userModelRepository;

    @Autowired
    private final CityStateLocationRepository cityStateLocationRepository;

    public UsersServiceImpl(UserModelRepository userModelRepository, CityStateLocationRepository cityStateLocationRepository) {
        this.userModelRepository = userModelRepository;
        this.cityStateLocationRepository = cityStateLocationRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO> addUsers(UsersDto usersDto) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {
            Users user = new Users();
            UserModel userModel = new UserModel();

            ErrorStructure errorStructure ;

            var errors = new ArrayList<>();

            if (usersDto.getFirstName() == null || usersDto.getFirstName().isEmpty()){
                errorStructure = new ErrorStructure(usersDto.getFirstName(), Constants.FIRST_NAME_SHOULD_NOT_BE_EMPTY,Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (usersDto.getFirstName().length() < Constants.MIN_VALUE){
                errorStructure = new ErrorStructure(usersDto.getFirstName(),Constants.FIRST_NAME_LESS_MESSAGE,Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else if (usersDto.getFirstName().equals(userModelRepository.findByName(usersDto.getFirstName()).get().getFirstName())) {
                errorStructure = new ErrorStructure(usersDto.getFirstName(),Constants.DUPLICATE_NAME,Constants.FIRST_NAME);
                errors.add(errorStructure);
            } else {
                user.setFirstName(usersDto.getFirstName());
                userModel.setFirstName(usersDto.getFirstName());
            }

            user.setLastName(usersDto.getLastName());
            userModel.setLastName(usersDto.getLastName());

            if (usersDto.getEmail() == null || usersDto.getEmail().isEmpty() || !Constants.EMAIL_PATTERN.matcher(usersDto.getEmail()).matches()){
                errorStructure = new ErrorStructure(usersDto.getEmail(),Constants.INVALID_EMAIL,Constants.EMAIL);
                errors.add(errorStructure);
            } else {
                user.setEmail(usersDto.getEmail());
                userModel.setEmail(usersDto.getEmail());
            }

            if (usersDto.getPhoneNumber() == null || usersDto.getPhoneNumber().length() != 10){
                errorStructure = new ErrorStructure(usersDto.getPhoneNumber(), Constants.INVALID_PHONE_NUMBER,Constants.PHONE_NUMBER);
                errors.add(errorStructure);
            } else {
                user.setPhoneNumber(usersDto.getPhoneNumber());
                userModel.setPhoneNumber(usersDto.getPhoneNumber());
            }

            if (usersDto.getDateOfBirth() == null){
                errorStructure = new ErrorStructure(null,Constants.DATE_OF_BIRTH_SHOULD_NOT_BE_NULL,Constants.DATE_OF_BIRTH);
                errors.add(errorStructure);
            } else {
                user.setDateOfBirth(usersDto.getDateOfBirth());
            }

            user.setGender(usersDto.getGender());

            AddressInformation addressInformation = new AddressInformation();

            if (usersDto.getAddressLine1() == null || usersDto.getAddressLine1().length() < Constants.MIN_VALUE){
                errorStructure = new ErrorStructure(usersDto.getAddressLine1(),Constants.ADDRESS_LINE_1_ERROR_MESSAGE,Constants.ADDRESS_LINE_1);
                errors.add(errorStructure);
            } else {
                user.setAddressLine1(usersDto.getAddressLine1());
                addressInformation.setAddressLine1(usersDto.getAddressLine1());
            }

            user.setAddressLine2(usersDto.getAddressLine2());
            addressInformation.setAddressLine2(usersDto.getAddressLine2());

            if (usersDto.getCity() == null || usersDto.getCity().isEmpty()){
                errorStructure = new ErrorStructure(usersDto.getCity(),Constants.CITY_SHOULD_NOT_BE_EMPTY,Constants.CITY);
                errors.add(errorStructure);
            } else {
                var cityDoc = cityStateLocationRepository.findById(new ObjectId(usersDto.getCity()))
                        .orElseThrow(()->new NoSuchElementException("City not found"));
                user.setCityId(cityDoc.getErpId());
                addressInformation.setCity(usersDto.getCity());
            }

            if (usersDto.getState() == null || usersDto.getState().isEmpty()){
                errorStructure = new ErrorStructure(usersDto.getState(),Constants.STATE_SHOULD_NOT_BE_EMPTY,Constants.STATE);
                errors.add(errorStructure);
            } else {
                var stateDoc = cityStateLocationRepository.findById(new ObjectId(usersDto.getState()))
                        .orElseThrow((()->new NoSuchElementException("State Not found")));
                user.setStateId(stateDoc.getErpId());
                addressInformation.setState(usersDto.getState());
            }

            if (usersDto.getCountry() == null || usersDto.getCountry().isEmpty()){
                errorStructure = new ErrorStructure(usersDto.getCountry(),Constants.COUNTRY_SHOULD_NOT_BE_EMPTY,Constants.COUNTRY);
                errors.add(errorStructure);
            } else {
                var countryDoc = cityStateLocationRepository.findById(new ObjectId(usersDto.getCountry()))
                        .orElseThrow((()->new NoSuchElementException("Country Not found")));
                user.setStateId(countryDoc.getErpId());
                addressInformation.setState(usersDto.getCountry());
            }

            user.setPinCode(Integer.valueOf(usersDto.getPinCode()));
            addressInformation.setPinCode(Integer.valueOf(usersDto.getPinCode()));

            response = new ResponseDTO(Boolean.TRUE,Constants.ADDED_SUCCESS,HttpStatus.OK.value(),Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_VALIDATE_DATA,e);
            response = new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.UNABLE_TO_VALIDATE_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return new ResponseEntity<>(response,httpStatus);
    }
}
