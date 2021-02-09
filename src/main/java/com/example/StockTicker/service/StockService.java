package com.example.StockTicker.service;

import com.example.StockTicker.model.StockWrapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

@AllArgsConstructor
@Service
public class StockService {

  private final RefreshService refreshService;

  public StockWrapper findStock (final String ticker) {
    try {
      return new StockWrapper(YahooFinance.get(ticker));
    } catch (IOException e) {
      System.out.println("Error");
    }
    return null;
  }

  public List<StockWrapper> findStock (final List<String> tickers) {

    return tickers.stream().
        map(this::findStock)
        .filter(Objects::isNull)
        .collect(Collectors.toList());
  }

  public BigDecimal findPrice (final StockWrapper stock) throws IOException {
    return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPrice();
  }

  public BigDecimal findLastPercentChange(final StockWrapper stock) throws IOException {

    return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeInPercent();
  }

  public BigDecimal findChangeFrom200MeanPercent(final StockWrapper stock ) throws IOException {

    return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg200();
  }

}
