package com.omid.client_service.food;

import lombok.Getter;

@Getter
public enum FoodType {
    PIZZA("فست فود"),
    COLD("غذای سرد"),
    HARM("غذای گرم"),
    DONNER("دنر کباب"),
    IRANI("غذای ایرانی"),
    PRE_FOOD("پیش غذا"),
    DRINK("نوشیدنی");

    private final  String value;

    FoodType(String value) {
        this.value = value;
    }
}
