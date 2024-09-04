package com.moondog.labs;

import java.util.UUID;

public interface OrderClient {
    String getOrderById(UUID orderId);
}
