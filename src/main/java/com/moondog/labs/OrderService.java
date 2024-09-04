package com.moondog.labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moondog.labs.hydration.BookClient;
import com.moondog.labs.hydration.HydrationDelegate;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OrderService {
    private final OrderClient orderClient;
    private final BookClient bookClient;
    private final ObjectMapper objectMapper;

    public Order getOrderById(UUID orderId) {
        Order order = getPrehydratedOrderById(orderId);
        HydrationDelegate hydrationDelegate = (ids) -> bookClient.getBooksById(ids);
        order.hydrate((ids) -> bookClient.getBooksById(ids));
        return order;
    }

    private Order getPrehydratedOrderById(UUID orderId) {
        String ordersAsJson = orderClient.getOrderById(orderId);
        return deserialize(ordersAsJson);
    }

    private Order deserialize(String json) {
        try {
            return objectMapper.readValue(json, Order.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse orders JSON", e);
        }
    }

}
