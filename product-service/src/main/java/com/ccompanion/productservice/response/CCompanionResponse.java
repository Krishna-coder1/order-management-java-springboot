package com.ccompanion.productservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class CCompanionResponse {
     public static ResponseEntity successResponse(Object body){
         HashMap responseObject =new HashMap();
         responseObject.put("success",true);
         responseObject.put("body",body);

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    public static ResponseEntity failedResponse(Object body){
        HashMap responseObject =new HashMap();
        responseObject.put("success",false);
        responseObject.put("body",body);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }

}
