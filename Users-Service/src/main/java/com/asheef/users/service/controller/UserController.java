package com.asheef.users.service.controller;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.users.service.dto.*;
import com.asheef.users.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private final UserService usersService;

    public UserController(UserService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("add")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UsersDto usersDto){
        return usersService.addUsers(usersDto);
    }

    @PostMapping("add/location/data")
    public String  addLocation(@RequestBody List<CityStateLocationDto> cityStateLocation){
        return usersService.addLocation(cityStateLocation);
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UsersUpdateDto usersUpdateDto){
        return usersService.updateUser(usersUpdateDto);
    }

    @PostMapping("add/additional-details")
    public ResponseEntity<ResponseDTO> addAdditionalDetails(@RequestBody AdditionalDetailsDto additionalDetailsDto){
        return usersService.addAdditionalDetails(additionalDetailsDto);
    }

    @PutMapping("update/additional-details")
    public ResponseEntity<ResponseDTO> updateAdditionalDetails(@RequestBody AdditionDetailsUpdateDto additionDetailsUpdateDto){
        return usersService.updateAdditionalDetails(additionDetailsUpdateDto);
    }

    @PostMapping("add/education-details")
    public ResponseEntity<ResponseDTO> addEducationDetails(@RequestBody EducationDto educationDto){
        return usersService.addEducationDetails(educationDto);
    }


}
