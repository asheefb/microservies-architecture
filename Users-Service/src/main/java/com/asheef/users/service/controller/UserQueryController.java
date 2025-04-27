package com.asheef.users.service.controller;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.users.service.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserQueryController {

    @Autowired
    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO>  getUserList(Integer pageNo, Integer pageSize ,String search){
        return userQueryService.getUserList(pageNo, pageSize ,search);
    }
}
