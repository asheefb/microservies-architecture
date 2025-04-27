package com.asheef.common_model_mdb.model.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomPageable<T> {
    public Map<String , Object> getPaginatedData(List<T> data,Integer pageNo,Integer pageSize,Integer count) {
        var paginatedData = new HashMap<String , Object>();

        int totalPages = count / pageSize;
        if (count % pageSize != 0) {
            totalPages +=1;
        }
        var pagination = new HashMap<String ,Object>();
        pagination.put("pageSize",pageSize);
        pagination.put("pageNo",pageNo);
        pagination.put("totalPages",totalPages);
        pagination.put("totalCount",count);

        paginatedData.put("data",data);
        paginatedData.put("paginator",pagination);
        return paginatedData;
    }
}
