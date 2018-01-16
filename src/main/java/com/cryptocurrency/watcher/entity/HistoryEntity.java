package com.cryptocurrency.watcher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by gkalra on 1/13/18.
 */
@Entity
@Table(name = "history",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "date_recorded"})
    })
public class HistoryEntity implements Serializable {

  private static final long serialVersionUID = -2566283232211074560L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "code")
  private String code;

  @Column(name = "date_recorded")
  private Date date;

  @Column(name = "tx_Volume")
  private double txVolume;

  @Column(name = "tx_Count")
  private double txCount;

  @Column(name = "marketcap")
  private double marketcap;

  @Column(name = "price")
  private double price;

  @Column(name = "exchange_Volume")
  private double exchangeVolume;

  @Column(name = "generated_Coins")
  private double generatedCoins;

  @Column(name = "fees")
  private double fees;

  public HistoryEntity() {
  }

  public HistoryEntity(String code, Date date, double txVolume, double txCount, double marketcap, double price, double exchangeVolume, double generatedCoins, double fees) {
    this.code = code;
    this.date = date;
    this.txVolume = txVolume;
    this.txCount = txCount;
    this.marketcap = marketcap;
    this.price = price;
    this.exchangeVolume = exchangeVolume;
    this.generatedCoins = generatedCoins;
    this.fees = fees;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public double getTxVolume() {
    return txVolume;
  }

  public void setTxVolume(double txVolume) {
    this.txVolume = txVolume;
  }

  public double getTxCount() {
    return txCount;
  }

  public void setTxCount(double txCount) {
    this.txCount = txCount;
  }

  public double getMarketcap() {
    return marketcap;
  }

  public void setMarketcap(double marketcap) {
    this.marketcap = marketcap;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getExchangeVolume() {
    return exchangeVolume;
  }

  public void setExchangeVolume(double exchangeVolume) {
    this.exchangeVolume = exchangeVolume;
  }

  public double getGeneratedCoins() {
    return generatedCoins;
  }

  public void setGeneratedCoins(double generatedCoins) {
    this.generatedCoins = generatedCoins;
  }

  public double getFees() {
    return fees;
  }

  public void setFees(double fees) {
    this.fees = fees;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("HistoryEntity{");
    sb.append("code='").append(code).append('\'');
    sb.append(", date=").append(date);
    sb.append(", txVolume=").append(txVolume);
    sb.append(", txCount=").append(txCount);
    sb.append(", marketcap=").append(marketcap);
    sb.append(", price=").append(price);
    sb.append(", exchangeVolume=").append(exchangeVolume);
    sb.append(", generatedCoins=").append(generatedCoins);
    sb.append(", fees=").append(fees);
    sb.append('}');
    return sb.toString();
  }
}
