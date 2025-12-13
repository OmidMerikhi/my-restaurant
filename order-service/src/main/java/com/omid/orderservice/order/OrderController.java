package com.omid.orderservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @PostMapping
    public String createOrder() {
        return "سفارش ثبت شد";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "این کالا در انبار موجود میباشد.";
    }


}
