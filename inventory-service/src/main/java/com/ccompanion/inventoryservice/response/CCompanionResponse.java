package com.ccompanion.inventoryservice.response;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Component
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
