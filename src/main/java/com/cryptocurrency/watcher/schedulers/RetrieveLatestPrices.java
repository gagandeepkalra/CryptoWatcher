package com.cryptocurrency.watcher.schedulers;

import com.cryptocurrency.watcher.entity.HistoryEntity;
import com.cryptocurrency.watcher.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RetrieveLatestPrices {

  private final static String format_url = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_INTRADAY&symbol=%s&market=USD&apikey=M5O2VJYR2K31OWPY";

  private final static String regex = "\"(\\d+-\\d+-\\d+).+?\": \\{\\s*\"1a\\. price \\(USD\\)\": \"(.+?)\",\\s*\"1b\\. price \\(USD\\)\": \"(.+?)\",\\s*\"2\\. volume\": \"(.+?)\",\\s*\"3\\. market cap \\(USD\\)\": \"(.+?)\"\\s*}";

  private RestTemplate restTemplate;

  private HistoryRepository repository;

  @Autowired
  public RetrieveLatestPrices(RestTemplate restTemplate, HistoryRepository repository) {
    this.restTemplate = restTemplate;
    this.repository = repository;
  }

  /**
   * Runs every two minutes and updates the Database with the latest values
   */
  @Scheduled(fixedDelay = 120000)
  public void updateDb() {
    String[] coins = {"btc", "eth", "doge", "ltc"};
    Arrays.stream(coins).forEach(s -> saveOrUpdateDb(s, getLatestPrices(s)));
  }

  public void saveOrUpdateDb(String code, String data) {

    final Matcher matcher = Pattern.compile(regex)
        .matcher(data);

    if (matcher.find()) {
      final HistoryEntity historyEntity = repository.getLastUpdatedRecord(new Date(System.currentTimeMillis()), code)
          .orElseGet(HistoryEntity::new);

      historyEntity.setCode(code);
      historyEntity.setDate(new Date(System.currentTimeMillis()));
      historyEntity.setPrice(Double.valueOf(matcher.group(2)));
      historyEntity.setTxVolume(Double.valueOf(matcher.group(4)));
      historyEntity.setMarketcap(Double.valueOf(matcher.group(5)));

      repository.save(historyEntity);
    }
  }

  private String getLatestPrices(String code) {
    return restTemplate.getForEntity(String.format(format_url, code.toUpperCase()), String.class).getBody();
  }
}
