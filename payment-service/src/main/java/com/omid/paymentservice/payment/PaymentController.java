package com.omid.paymentservice.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @PostMapping
    public String pay(@RequestParam("amount") Long amount){
        DecimalFormat df = new DecimalFormat("###,###");
        return String.format("پرداخت شما به مبلغ %s تومان با موفقیت انجام پذیرفت.", df.format(amount));
    }
}
