package com.asheef.users.service.service;

import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserQueryService {

    public ResponseEntity<ResponseDTO> getUserByPhone(String phone);

    public ResponseEntity<ResponseDTO> getUserBasicDetails(String id);

    public ResponseEntity<ResponseDTO> getUserList(Integer pageNo, Integer pageSize ,String search);

    public ResponseEntity<ResponseDTO> getAuditHistory(String userId);
}
