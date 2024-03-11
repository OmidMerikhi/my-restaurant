package com.omid.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final FoodServiceClient foodServiceClient;
    private final MongoTemplate mongoTemplate;

    public List<Food> loadAll() {
        return foodServiceClient.loadAllFoods();
    }

    public Order createOrder(List<OrderItem> items, String customerName) {
        Double finalPrice = 0d;
        for (OrderItem i : items) {
            i.setTotalPrice((i.getFood().getPrice()) * i.getQuantity());
            finalPrice += i.getTotalPrice();
        }
        Order order = new Order();
        order.setOrderNumber("1");
        order.setId(UUID.randomUUID().toString());
        order.setOrderItemList(items);
        order.setCustomerFullName(customerName);
        order.setFinalPrice(finalPrice);
        order.setOrderDate(LocalDateTime.now());
        return mongoTemplate.insert(order);
    }

    public List<Order> loadAllOrders() {
        return mongoTemplate.findAll(Order.class);
    }

    public List<Order> loadByDate(Date date) {

        if (date.getYear() == null) {

        }
        int year = date.getYear() == null ? LocalDateTime.now().getYear() : date.getYear();
        int month = date.getMonth() == null ? LocalDateTime.now().getMonthValue() : date.getMonth();
        int day = date.getDay() == null ? LocalDateTime.now().getDayOfMonth() : date.getDay();
        int hour = date.getHour() == null ? LocalDateTime.now().getHour() : date.getHour();
        int min = date.getMin() == null ? LocalDateTime.now().getMinute() : date.getMin();
        int sec = date.getSec() == null ? LocalDateTime.now().getSecond() : date.getSec();

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, min, sec);
        List<Order> res = loadAllOrders().stream().filter(o ->
                o.getOrderDate().getYear() == year &&
                        o.getOrderDate().getMonthValue() == month &&
                        o.getOrderDate().getDayOfMonth() == day &&
                        o.getOrderDate().getHour() == hour).toList();
        Query query = new Query();
        query.addCriteria(Criteria.where("orderDate").regex(dateTime.toString()));
        return res;
    }

}
