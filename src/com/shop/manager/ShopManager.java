package com.shop.manager;

import com.shop.model.Order;
import com.shop.model.Product;

import java.util.Arrays;

public class ShopManager {
    private Product[] products;  // 상품 배열 (크기 50)
    private int productCount;    // 현재 등록된 상품 수
    private Order[] orders;      // 주문 배열 (크기 50)
    private int orderCount;      // 현재 주문 수

    public ShopManager() {
        products = new Product[50];
        productCount = 0;
        orders = new Order[50];
        orderCount = 0;
    }

    public void addProduct(Product product){
        if(productCount >= products.length ){
            System.out.println("상품이 가득 찼습니다.");
            return;
        }

        products[productCount++] = product;

        System.out.println("[상품 등록] " + product.getName() + " - " + product.getPrice() + "원");

    }

    public Product findProductById(String id){
        for(int i = 0; i < productCount; i++){
            if(products[i].getId().equals(id)){
                return products[i];
            }
        }
        return null;
    }
    public Product[] searchProductsByName(String keyword){
        Product[]  temp = new Product[50];
        int tempCount = 0;
        for(int i = 0; i < productCount; i++){
            boolean check = products[i].getName().toLowerCase().contains(keyword.toLowerCase());
            if(check){
                temp[tempCount]= products[i];
                tempCount++;
            }
        }
        Product[] searchedProducts = new Product[tempCount];
        for(int i = 0; i < searchedProducts.length; i++){
            searchedProducts[i] = temp[i];
        }
        return searchedProducts;
    }

    public Product[] searchProductsByCategory(String category){
        Product[]  temp = new Product[50];
        int tempCount = 0;

        for(int i = 0; i < productCount; i++){
            boolean check = products[i].getCategory().equalsIgnoreCase(category);
            if(check){
                temp[tempCount] = products[i];
                tempCount++;
            }
        }
        Product[] searchedProducts = new Product[tempCount];
        for(int i = 0; i < searchedProducts.length; i++){
            searchedProducts[i] = temp[i];
        }
        System.out.println(Arrays.toString(searchedProducts));
        return searchedProducts;
    }

    public void printAllProducts(){
        for(int i = 0; i < productCount; i++){
            Product p = products[i];
            System.out.println((i+1) + ". [" + p.getId() + "] " + p.getName() + " - " + p.getPrice() + "원 (재고: " + p.getStock() + "개)");
        }
    }
    //주문 관리
    public Order createOrder(){
        Order order = new Order();
        orders[orderCount] = order;
        orderCount++;
        System.out.println("새로운 주문이 생성 되었습니다.");
        return order;
    }

    public void addOrderItem(Order order, String productId, int quantity){
        Product product = findProductById(productId);
        if(product == null){
            System.out.println("주문 하신 상품이 없습니다.");
            return;
        }

        if(!product.isAvailable(quantity)){
            System.out.println("재고 부족.");
            return;
        }

        order.addItem(productId, quantity);
        System.out.println("주문이 추가 되었습니다. ");

    }

    public void processOrder(Order order) {
        order.calculateTotal(this);



        String[] productIds = order.getProductIds();
        int[] quantities = order.getQuantities();
        int itemCount = order.getItemCount();

        for(int i = 0; i < itemCount; i++){

            String productId = productIds[i];

            Product product = findProductById(productId);

            product.decreaseStock(quantities[i]);
        }
        order.complete();

        orders[orderCount] = order;
        System.out.println("===주문 내역===");

        System.out.println("결제가 완료되었습니다. 총 금액: " + order.getTotalAmount() + "원");
    }
    public Order findOrderById(String orderId){
        for(int i = 0; i < orderCount; i++){
            if(orders[i].getOrderId().equals(orderId)){
                return orders[i];
            }
        }
        return null;
    }

    public void printAllOrders(){
        for(int i = 0; i < orderCount; i++){
            System.out.println((i+1) + ". [" + orders[i].getOrderId() + "] " + orders[i].getTotalAmount() + "원 (" + orders[i].getStatus() + ")");
        }
    }

}
