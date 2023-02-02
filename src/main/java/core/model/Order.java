/*
 * MIT License
 *
 * Copyright (c) [2019] [Sudeep Batra] [sudeep.batra@gmail.com] [+91.9325487506]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package core.model;

import core.enums.OrderStatus;
import core.enums.OrderType;
import core.enums.TransactionType;
import core.utils.Assert;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public abstract class Order implements Comparable<Order> {
    private UUID orderId;
    private String userID;
    protected String tradingSymbol;
    protected OrderType orderType;
    protected TransactionType transactionType;
    protected double price;
    protected int quantity;
    protected LocalDateTime timestamp;
    protected OrderStatus orderStatus;

    public Order(String tradingSymbol, OrderType orderType, double price, int quantity) {
        this.orderId = UUID.randomUUID();
        this.tradingSymbol = tradingSymbol;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getTradingSymbol() {
        return tradingSymbol;
    }

    public void setTradingSymbol(String tradingSymbol) {
        this.tradingSymbol = tradingSymbol;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        Assert.state(price <= 0, "Price must be postive");
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        Assert.state(quantity <= 0, "Order quantity must be postive");
        this.quantity = quantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("orderId=" + orderId)
                .add("tradingSymbol='" + tradingSymbol + "'")
                .add("orderType=" + orderType)
                .add("transactionType=" + transactionType)
                .add("price=" + price)
                .add("quantity=" + quantity)
                .add("userID='" + userID + "'")
                .add("timestamp=" + timestamp)
                .add("orderStatus=" + orderStatus)
                .toString();
    }
}
