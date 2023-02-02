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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {
    private static final String tradingSymbol = "abc";
    private OrderBook orderBookABC;

    private BuyOrder buyLimitOrder1;
    private BuyOrder buyLimitOrder2;
    private BuyOrder buyLimitOrder3;
    private BuyOrder buyLimitOrder4;
    private BuyOrder buyMarketOrder1;
    private BuyOrder buyMarketOrder2;

    private SellOrder sellLimitOrder1;
    private SellOrder sellLimitOrder2;
    private SellOrder sellLimitOrder3;
    private SellOrder sellLimitOrder4;
    private SellOrder sellMarketOrder1;
    private SellOrder sellMarketOrder2;

    @BeforeEach
    void setUp() {
        orderBookABC = new OrderBookImpl(tradingSymbol);

        buyLimitOrder1 = new BuyOrder(tradingSymbol, OrderType.LIMIT, 95.0, 3);
        buyLimitOrder2 = new BuyOrder(tradingSymbol, OrderType.LIMIT, 95.0, 1);
        buyLimitOrder3 = new BuyOrder(tradingSymbol, OrderType.LIMIT, 92.5, 20);
        buyLimitOrder4 = new BuyOrder(tradingSymbol, OrderType.LIMIT, 100, 20);
        buyMarketOrder1 = new BuyOrder(tradingSymbol, OrderType.MARKET, Double.NaN, 20);
        buyMarketOrder2 = new BuyOrder(tradingSymbol, OrderType.MARKET, Double.NaN, 25);

        sellLimitOrder1 = new SellOrder(tradingSymbol, OrderType.LIMIT, 95, 3);
        sellLimitOrder2 = new SellOrder(tradingSymbol, OrderType.LIMIT, 95, 1);
        sellLimitOrder3 = new SellOrder(tradingSymbol, OrderType.LIMIT, 92.5, 20);
        sellLimitOrder4 = new SellOrder(tradingSymbol, OrderType.LIMIT, 100, 20);
        sellMarketOrder1 = new SellOrder(tradingSymbol, OrderType.MARKET, Double.NaN, 20);
        sellMarketOrder2 = new SellOrder(tradingSymbol, OrderType.MARKET, Double.NaN, 20);

    }

    @Test
    void testBuyOrderWithHigherPriceHasHigherPriority() {
        orderBookABC.addOrder(buyLimitOrder1);
        orderBookABC.addOrder(buyLimitOrder2);
        orderBookABC.addOrder(buyLimitOrder3);
        orderBookABC.addOrder(buyLimitOrder4);

        Assertions.assertEquals(orderBookABC.getBestBidOffer(), buyLimitOrder4);
    }

    @Test
    void testLimitOrdersTimePrecedence() {
        orderBookABC.addOrder(buyLimitOrder2);
        orderBookABC.addOrder(buyLimitOrder1);

        Assertions.assertEquals(orderBookABC.getBestBidOffer(), buyLimitOrder2);
    }


    @Test
    void testBuyOrderWithMarketPriceHasHigherPriority() {
        orderBookABC.addOrder(buyLimitOrder1);
        orderBookABC.addOrder(buyLimitOrder2);
        orderBookABC.addOrder(buyLimitOrder3);
        orderBookABC.addOrder(buyLimitOrder4);
        orderBookABC.addOrder(buyMarketOrder1);

        Assertions.assertEquals(orderBookABC.getBestBidOffer(), buyMarketOrder1);
    }

    @Test
    void testBuyOrdersWhenMultipleMarketPriceOrderTimePrecedence() {
        orderBookABC.addOrder(buyLimitOrder1);
        orderBookABC.addOrder(buyLimitOrder2);
        orderBookABC.addOrder(buyLimitOrder3);
        orderBookABC.addOrder(buyLimitOrder4);
        orderBookABC.addOrder(buyMarketOrder2);
        orderBookABC.addOrder(buyMarketOrder1);

        Assertions.assertEquals(orderBookABC.getBestBidOffer(), buyMarketOrder2);
    }


    @Test
    void testBuyOrderWithLowerPriceHasHigherPriority() {
        orderBookABC.addOrder(sellLimitOrder1);
        orderBookABC.addOrder(sellLimitOrder2);
        orderBookABC.addOrder(sellLimitOrder3);
        orderBookABC.addOrder(sellLimitOrder4);

        Assertions.assertEquals(orderBookABC.getBestAskOffer(), sellLimitOrder3);
    }
}
