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

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class OrderBookImpl implements OrderBook {
    private String tradingSymbol;
    private PriorityQueue<BuyOrder> buyQueue;
    private PriorityQueue<SellOrder> sellQueue;

    public OrderBookImpl(String tradingSymbol) {
        this.tradingSymbol = tradingSymbol;
        buyQueue = new PriorityQueue<>();
        sellQueue = new PriorityQueue<>();
    }

    /**
     * Adds the order to the OrderBook
     */
    @Override
    public void addOrder(Order order) {
        order.setTimestamp(LocalDateTime.now());

        if (order instanceof BuyOrder) {
            buyQueue.add((BuyOrder) order);
        } else {
            sellQueue.add((SellOrder) order);
        }
    }

    @Override
    public boolean amendOrder(Order order) {
        //Since the Orders are matched by OrderID, remove the old order, and add the new order. All things same it will be added to the end of the queue

        if (isOrderPending(order)) {
            if (order instanceof BuyOrder) {
                buyQueue.remove(order);
            } else {
                buyQueue.remove(order);
            }

            addOrder(order);
        }

        return false;
    }

    @Override
    public boolean cancelOrder(Order order) {

        if (isOrderPending(order)) {
            if (order instanceof BuyOrder) {
                buyQueue.remove(order);
            } else {
                buyQueue.remove(order);
            }
        }

        return false;
    }

    @Override
    public void runMatch() {

    }

    @Override
    public BuyOrder getBestBidOffer() {
        return buyQueue.peek();
    }

    @Override
    public SellOrder getBestAskOffer() {
        return sellQueue.peek();
    }

    @Override
    public BuyOrder getAndRemoveBestBidOffer() {
        return buyQueue.poll();
    }

    @Override
    public SellOrder getAndRemoveBestAskOffer() {
        return sellQueue.poll();
    }

    //Check if the Order is still pending in the Order Book!
    private boolean isOrderPending(Order order) {
        if (order instanceof BuyOrder) {
            return buyQueue.contains(order);
        } else {
            return sellQueue.contains(order);
        }
    }

    public String getTradingSymbol() {
        return tradingSymbol;
    }
}
