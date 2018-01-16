package com.cryptocurrency.watcher.controller;

import com.cryptocurrency.watcher.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gkalra on 1/13/18.
 */
@Controller
public class WebController {

  @Autowired
  HistoryRepository repository;

  @RequestMapping("/")
  public String home() {
    return "redirect:index.html";
  }

  @RequestMapping("/price")
  @ResponseBody
  public List<Currency> findAllCurrencyPrices1() {
    return repository.getLatestPricesForAllCurrencies()
        .stream()
        .map(historyEntity -> new Currency(historyEntity.getCode(), historyEntity.getPrice()))
        .collect(Collectors.toList());
  }

  @RequestMapping("/history/{code}")
  @ResponseBody
  public List<History> findByCurrencyName(@PathVariable String code) {
    return repository.findByCodeOrderByDateDesc(code)
        .stream()
        .map(historyEntity -> new History(historyEntity.getDate(), historyEntity.getTxVolume(), historyEntity.getPrice()))
        .collect(Collectors.toList());
  }

  @Getter
  @Setter
  @AllArgsConstructor
  class Currency {
    String name;
    Double price;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  class History {
    Date date;
    Double txVolume;
    Double price;
  }

}
