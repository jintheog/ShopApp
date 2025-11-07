package com.shop.utils;

public class IdGenerator {
    private static IdGenerator instance;  // 싱글톤 인스턴스
    private int productIdCounter;         // 상품 ID 카운터
    private int orderIdCounter;            // 주문 ID 카운터

    private IdGenerator() {
        this.productIdCounter = 1;
        this.orderIdCounter = 1;
    }

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    public String generateProductId()
    {
        return "P" + productIdCounter++;
    }

    public String generateOrderId()
    {
        return "O" + orderIdCounter++;
    }

}
