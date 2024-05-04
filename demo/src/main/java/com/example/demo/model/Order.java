package com.example.demo.model;

public class Order {

    private long orderNumber;
    private String orderTime;
    private String bookName;
    private int quantity;
    private String shippingAddress;
    private String totalPrice;

    public Order(long orderNumber, String orderTime, String bookName, int quantity, String shippingAddress,
            String totalPrice) {
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
        this.bookName = bookName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "\n下单时间：" + orderTime + '\n' +
                "书名：" + bookName + '\n' +
                "数量：" + quantity + '\n' +
                "收货地址：" + shippingAddress + '\n' +
                "总价：" + totalPrice;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
