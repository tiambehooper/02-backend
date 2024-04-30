package com.luv2code.ecommerce.dto;

import lombok.Data;
import lombok.NonNull;



@Data
public class PurchaseResponse {

    @NonNull
    private String orderTrackingNumber;


    public PurchaseResponse(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }
}
