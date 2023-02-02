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

import core.enums.OrderType;
import core.enums.TransactionType;

public class SellOrder extends Order {

    public SellOrder(String tradingSymbol, OrderType orderType, double price, int quantity) {
        super(tradingSymbol, orderType, price, quantity);
        this.transactionType = TransactionType.SELL;
    }

    /**
     * Sell Orders are sorted by Market Orders, Ascending Price for Limit Orders and then by increasing timestamp
     */
    @Override
    public int compareTo(Order o) {
        if (getOrderType().equals(OrderType.MARKET) ^ o.getOrderType().equals(OrderType.MARKET)) {
            if (getOrderType().equals(OrderType.MARKET)) {
                return -1;
            }

            return 1;
        } else {
            int signum = (int) Math.signum(getPrice() - o.getPrice());

            if (signum != 0) {
                return signum;
            }
        }

        return getTimestamp().compareTo(o.getTimestamp());
    }
}
