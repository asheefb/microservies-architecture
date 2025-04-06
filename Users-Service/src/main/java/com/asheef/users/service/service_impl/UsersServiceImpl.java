package com.asheef.users.service.service_impl;

import com.asheef.common_model_mdb.model.employee.UserModel;
import com.asheef.common_model_mdb.model.utils.ErrorStructure;
import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.common_model_mdb.repository.UserModelRepository;
import com.asheef.common_model_ms.model.employee.Users;
import com.asheef.users.service.constants.Constants;
import com.asheef.users.service.dto.UsersDto;
import com.asheef.users.service.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserModelRepository userModelRepository;

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
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
