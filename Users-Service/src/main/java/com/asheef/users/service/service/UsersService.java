package com.asheef.users.service.service;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.users.service.dto.CityStateLocationDto;
import com.asheef.users.service.dto.UsersDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {

    public ResponseEntity<ResponseDTO> addUsers(UsersDto usersDto);

    String addLocation(List<CityStateLocationDto> cityStateLocation);
}
