package com.shop.model;

import com.shop.utils.IdGenerator;

public class Product {
    private String id;        // 상품 ID (자동 생성)
    private String name;      // 상품명
    private int price;        // 가격
    private int stock;        // 재고
    private String category;  // 카테고리

    public Product(String name, int price, int stock, String category) {
        this.id = IdGenerator.getInstance().generateProductId();
        setName(name);     // setter를 통한 유효성 검증
        setPrice(price);
        setStock(stock);
        setCategory(category);
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }
        this.name = name;
    }

    public void setPrice(int price) {
        if(price < 0){
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        this.price = price;
    }

    public void setStock(int stock) {
        if(stock < 0){
            throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        }
        this.stock = stock;
    }

    public void setCategory(String category) {
        if(category == null || category.isEmpty()){
            throw new IllegalArgumentException("카테고리는 필수입니다.");
        }
        this.category = category;
    }


    public void decreaseStock(int quantity){
        if(quantity < 0 || stock < quantity){
            System.out.println("재고가 충분하지 않습니다.");
            return;
        }
        stock -= quantity;
    }
    public void increaseStock(int quantity){
        if(stock < 0){
            System.out.println("재고 수량이 0 미만입니다.");
            return;
        }
        stock += quantity;
    }

    public boolean isAvailable(int quantity){
        return stock >= quantity;
    }



}
