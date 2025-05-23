package com.asheef.users.service.service;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.users.service.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public ResponseEntity<ResponseDTO> addUsers(UsersDto usersDto);

    String addLocation(List<CityStateLocationDto> cityStateLocation);

    public ResponseEntity<ResponseDTO> updateUser(UsersUpdateDto usersUpdateDto);

    public ResponseEntity<ResponseDTO> addAdditionalDetails(AdditionalDetailsDto additionalDetailsDto);

    public ResponseEntity<ResponseDTO> updateAdditionalDetails(AdditionDetailsUpdateDto additionDetailsUpdateDto);

    public ResponseEntity<ResponseDTO> addEducationDetails(EducationDto educationDto);
}
