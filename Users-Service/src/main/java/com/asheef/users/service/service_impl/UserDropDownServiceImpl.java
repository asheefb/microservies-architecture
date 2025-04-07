package com.asheef.users.service.service_impl;

import com.asheef.common_model_mdb.model.CityStateLocation;
import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.common_model_mdb.repository.CityStateLocationRepository;
import com.asheef.users.service.constants.Constants;
import com.asheef.users.service.service.UsersDropDownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDropDownServiceImpl implements UsersDropDownService {

    @Autowired
    private final CityStateLocationRepository cityStateLocationRepository;

    public UserDropDownServiceImpl(CityStateLocationRepository cityStateLocationRepository) {
        this.cityStateLocationRepository = cityStateLocationRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO> getCountries() {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {
            List<CityStateLocation> countries = cityStateLocationRepository.findByType(Constants.COUNTRY_TYPE);

            if (countries.isEmpty()){
                response = new ResponseDTO(Boolean.FALSE,List.of(),HttpStatus.OK.value(),Constants.NO_DATA_FOUND);
                httpStatus = HttpStatus.OK;
                return new ResponseEntity<>(response,httpStatus);
            }

            response = new ResponseDTO(Boolean.TRUE,countries,HttpStatus.OK.value(), Constants.SUCCESS);
            httpStatus = HttpStatus.OK;

        } catch (Exception e) {
            log.error(Constants.UNABLE_TO_VALIDATE_DATA,e);

            response = new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.UNABLE_TO_VALIDATE_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return new ResponseEntity<>(response,httpStatus);
    }

    @Override
    public ResponseEntity<ResponseDTO> getStates(String type, String countryCode) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> getCities(String type, String stateIsoCode) {
        return null;
    }
}
