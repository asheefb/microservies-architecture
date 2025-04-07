package com.asheef.users.service.service;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UsersDropDownService {

    public ResponseEntity<ResponseDTO> getCountries();

    public ResponseEntity<ResponseDTO> getStates(String type,String countryCode);

    public ResponseEntity<ResponseDTO> getCities(String type,String stateIsoCode);
}
