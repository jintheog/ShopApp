package com.shop.manager;

import com.shop.model.Order;
import com.shop.model.Product;

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
        int totalAmount = 0; // 상세 계산을 위해 초기화

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== 주문 내역 ===\n");
        sb.append("주문번호: ").append(order.getOrderId()).append("\n");
        sb.append("----------------------------\n");

        // 주문 항목 순회 및 출력
        for(int i = 0; i < itemCount; i++){
            String productId = productIds[i];
            int quantity = quantities[i];

            // ShopManager의 findProductById()를 사용해 상품 정보 조회
            Product product = findProductById(productId);

            if (product != null) {
                int itemPrice = product.getPrice();
                long subtotal = (long)itemPrice * quantity; // 개별 금액 계산
                totalAmount += subtotal;

                // 출력 형식에 맞춰 문자열 생성
                sb.append(product.getName())
                        .append(" x ").append(quantity)
                        .append(" = ").append(subtotal).append("원\n");
            } else {
                // 상품 정보를 찾을 수 없을 때의 처리
                sb.append("알 수 없는 상품 ID [").append(productId).append("] (계산 제외)\n");
            }
        }

        sb.append("----------------------------\n");
        // calculateTotal에서 갱신된 order.getTotalAmount() 값을 사용하거나,
        // 위에서 계산한 totalAmount 값을 사용합니다. (둘 다 동일해야 함)
        sb.append("총 금액: ").append(order.getTotalAmount()).append("원");

        // 최종 출력
        System.out.println(sb.toString());

        // 3. 재고 차감 (반복문 재사용)
        for(int i = 0; i < itemCount; i++){
            String productId = productIds[i];
            Product product = findProductById(productId);

            if (product != null) {
                // product.decreaseStock(quantities[i]); // 주석 처리
            }
        }

        // 4. 주문 완료 처리
        order.complete();

        // 5. 중복 추가 로직 제거 (이전 수정 반영)
        // orders 배열에 추가하는 로직은 createOrder()에서 이미 수행되었으므로 제거함.

        System.out.println("\n✅ 결제 완료");
    }



    public Order findOrderById(String orderId){
        for (int i = 0; i < orderCount; i++){
            if (orders[i].getOrderId().equals(orderId)){
                return orders[i];
            }
        }
        return null;
    }

    public void printAllOrders() {
        for(int i = 0; i < orderCount; i++){
            System.out.println((i+1) + ". [" + orders[i].getOrderId() + "] " + orders[i].getTotalAmount() + "원 (" + orders[i].getStatus() + ")");

        }
    }
}
