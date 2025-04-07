package com.asheef.users.service.controller;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.users.service.service.UsersDropDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("api/v1/users"))
public class UsersDropDownController {

    @Autowired
    private final UsersDropDownService usersDropDownService;

    public UsersDropDownController(UsersDropDownService usersDropDownService) {
        this.usersDropDownService = usersDropDownService;
    }

    @GetMapping("countries")
    public ResponseEntity<ResponseDTO> getCountries(){
        return usersDropDownService.getCountries();
    }

    @GetMapping("states")
    public ResponseEntity<ResponseDTO> getStates(String type, String countryCode) {
        return usersDropDownService.getStates(type,countryCode);
    }

    @GetMapping("cities")
    public ResponseEntity<ResponseDTO> getCities(String type, String stateIsoCode) {
        return usersDropDownService.getCities(type,stateIsoCode);
    }

}
