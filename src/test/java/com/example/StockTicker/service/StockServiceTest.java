package com.example.StockTicker.service;

import com.example.StockTicker.model.StockWrapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
class StockServiceTest {

  @Autowired
  private StockService stockService;

  @Test
  void invoke() throws IOException {
    final StockWrapper stock = stockService.findStock("PD");
    System.out.println(stock.getStock());

    final BigDecimal findPrice = stockService.findPrice(stock);
    System.out.println(findPrice);

    final BigDecimal change = stockService.findLastPercentChange(stock);
    System.out.println(change);

    final BigDecimal mean200DayPercent = stockService.findChangeFrom200MeanPercent(stock);
    System.out.println(mean200DayPercent);
  }

  @Test
  void multipleStocks() throws IOException, InterruptedException {

    final List<StockWrapper> stocks = stockService.findStock(Arrays.asList("PD", "PLTR","PS", "ITM","WORK","YOU", "UKDV", "AO", "UKW", "APHA", "BB", "SNAP"));
    findPrices(stocks);

    Thread.sleep(15000);
    
    final StockWrapper aa = stockService.findStock("AA.L");
    stocks.add(aa);

    System.out.println(stockService.findPrice(aa));

    findPrices(stocks);
  }

  private void findPrices(List<StockWrapper> stocks) {
    stocks.forEach(stock -> {
      try {
        System.out.println(stockService.findPrice(stock));
      } catch (IOException e) {
        // ignore
      }
    });
  }
}