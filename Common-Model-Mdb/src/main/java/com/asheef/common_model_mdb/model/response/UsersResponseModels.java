package com.asheef.common_model_mdb.model.response;

import com.asheef.common_model_mdb.model.utils.CountModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponseModels {

    private ArrayList<UserResponseModel> userResponseModels;

    private ArrayList<CountModel> countModels;
}
