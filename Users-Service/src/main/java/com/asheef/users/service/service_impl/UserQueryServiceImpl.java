package com.asheef.users.service.service_impl;

import com.asheef.common_model_mdb.model.response.UserResponseModel;
import com.asheef.common_model_mdb.model.response.UsersResponseModels;
import com.asheef.common_model_mdb.model.utils.CountModel;
import com.asheef.common_model_mdb.model.utils.CustomPageable;
import com.asheef.common_model_mdb.model.utils.ResponseDTO;
import com.asheef.common_model_mdb.repository.UserAuditRepository;
import com.asheef.common_model_mdb.repository.UserModelRepository;
import com.asheef.common_model_ms.model.employee.Users;
import com.asheef.common_model_ms.repository.UsersRepository;
import com.asheef.users.service.constants.Constants;
import com.asheef.users.service.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.FacetOperation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@Slf4j
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final UserModelRepository userModelRepository;

    @Autowired
    private final UserAuditRepository userAuditRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;

    public UserQueryServiceImpl(UsersRepository usersRepository, UserModelRepository userModelRepository, UserAuditRepository userAuditRepository, MongoTemplate mongoTemplate) {
        this.usersRepository = usersRepository;
        this.userModelRepository = userModelRepository;
        this.userAuditRepository = userAuditRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ResponseEntity<ResponseDTO> getUserByPhone(String phone) {

        try {
            if (phone == null || phone.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,HttpStatus.BAD_REQUEST.value(),Constants.INVALID_PHONE_NUMBER),HttpStatus.BAD_REQUEST);
            }
            Users users = usersRepository.findByPhoneNumber(phone)
                    .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_PHONE_USER_NOT_FOUND));

            return new ResponseEntity<>(new ResponseDTO(Boolean.TRUE,users, HttpStatus.OK.value(),Constants.SUCCESS),HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseDTO(Boolean.FALSE,HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> getUserBasicDetails(String id) {

        ResponseDTO response;
        HttpStatus httpStatus;

        try {
            if (id == null || id.isEmpty()) {
                response = new ResponseDTO(Boolean.FALSE,HttpStatus.BAD_REQUEST.value(),Constants.INVALID_ID);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response,httpStatus);
            }

            Optional<UserResponseModel> userBasicDetailsById = userModelRepository.findUserBasicDetailsById(new ObjectId(id));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> getUserList(Integer pageNo , Integer pageSize,String searchText) {

        ResponseDTO response;
        HttpStatus httpStatus;
        try {
            if (searchText != null && !searchText.isEmpty() && searchText.length() < 3) {
                response = new ResponseDTO(Boolean.FALSE,HttpStatus.BAD_REQUEST.value(),Constants.INVALID_SEARCH_TEXT);
                httpStatus = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(response,httpStatus);
            }

            if (pageNo <= 0){
                pageNo =1;
            }

            pageNo -= 1;

            Criteria criteria;
            if (searchText == null || searchText.isEmpty()) {
                criteria = new Criteria(); // No filtering if search is empty
            } else {
                criteria = new Criteria().andOperator(
                        Criteria.where("first_name").regex(searchText, "i")
                );
            }


            var skipPage = (pageNo) * pageSize;

            FacetOperation facetOperation = Aggregation.facet(
                            Aggregation.skip(skipPage),
                            Aggregation.limit(pageSize)).as("userResponseModels")
                    .and(Aggregation.count().as("count"))
                    .as("countModels");

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.lookup("city_state_location", "address_info.city", "_id", "cityDetails"),
                    Aggregation.unwind("cityDetails"),
                    Aggregation.project(
                            Fields.from(
                                    Fields.field("userId","$user_id"),
                                    Fields.field("firstName","$first_name"),
                                    Fields.field("lastName","$last_name"),
                                    Fields.field("email","$email"),
                                    Fields.field("phone","$phone_number"),
                                    Fields.field("addressLine1","$address_info.address_line_1"),
                                    Fields.field("addressLine2","$address_info.address_line_2"),
                                    Fields.field("cityName","$cityDetails.city_name"),
                                    Fields.field("stateName","$cityDetails.state_name"),
                                    Fields.field("countryName","$cityDetails.country_name"),
                                    Fields.field("pinCode","$address_info.pinCode")
                            )
                    ),
                    facetOperation
            );

            var result = mongoTemplate.aggregate(aggregation, "users", UsersResponseModels.class);

            List<UsersResponseModels> mappedResults = result.getMappedResults();

            if (mappedResults.isEmpty()){
                response = new ResponseDTO(Boolean.TRUE,List.of(),HttpStatus.OK.value(),Constants.NO_DATA_FOUND);
                httpStatus = HttpStatus.OK;
                return new ResponseEntity<>(response,httpStatus);
            } else {
                ArrayList<UserResponseModel> userResponseModels = mappedResults.get(0).getUserResponseModels();
                ArrayList<CountModel> countModels = mappedResults.get(0).getCountModels();

                if (!countModels.isEmpty()){
                    Integer count = countModels.get(0).getCount();

                    var paginateData = new CustomPageable<UserResponseModel>().getPaginatedData(userResponseModels, pageNo, pageSize, count);
                    response = new ResponseDTO(Boolean.TRUE,paginateData,HttpStatus.OK.value(),Constants.SUCCESS);
                    httpStatus = HttpStatus.OK;
                } else {
                    Map<String, Object> paginatedData = new CustomPageable<>().getPaginatedData(List.of(), pageNo, pageSize, 0);

                    response = new ResponseDTO(Boolean.TRUE,paginatedData,HttpStatus.OK.value(),Constants.SUCCESS);
                    httpStatus = HttpStatus.OK;
                }
            }
        } catch (Exception e) {
            response = new ResponseDTO(Boolean.FALSE,HttpStatus.UNPROCESSABLE_ENTITY.value(),Constants.UNABLE_TO_FETCH_DATA);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return new ResponseEntity<>(response,httpStatus);
    }

    @Override
    public ResponseEntity<ResponseDTO> getAuditHistory(String userId) {
        return null;
    }
}
