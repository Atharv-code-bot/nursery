package com.sakshi.nursery.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakshi.nursery.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private  String address;
    private PaymentStatus paymentStatus;
}
