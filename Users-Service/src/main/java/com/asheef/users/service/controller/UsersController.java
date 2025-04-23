package com.asheef.users.service.controller;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.users.service.dto.*;
import com.asheef.users.service.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("*")
public class UsersController {

    @Autowired
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
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

}
