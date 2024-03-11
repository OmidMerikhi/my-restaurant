package com.omid.restaurantclientservice;

import lombok.Getter;

@Getter
public enum Category {
    PIZZA("پیتزا"),
    WARM_SANDWICH(" ساندویچ گرم"),
    COLD_SANDWICH("ساندویچ سرد"),
    SPECIAL("ویژه"),
    CHICKEN("مرغ"),
    DONER_KABAB("کباب ترکی"),
    DRINK("نوشیدنی"),
    STARTER("پیش غذا");

    String value;

    Category(String value) {
        this.value = value;
    }
}
