package com.dreamers.object;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BollingerBand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("exchange") String exchange;
	@JsonProperty("symbol") String symbol;
	@JsonProperty("currencySymbol") String currencySymbol;
	@JsonProperty("baseCurrencySymbol") String baseCurrencySymbol;
	@JsonProperty("lastPrice") Double lastPrice;
	@JsonProperty("upperBB") Double upperBB;
	@JsonProperty("sma") Double sma;
	@JsonProperty("lowerBB") Double lowerBB;
	@JsonProperty("percentage") Double percentage;
	@JsonProperty("comparativePercentage") Double comparativePercentage;
	@JsonProperty("interval") String interval;
	@JsonProperty("timestamp") Long timestamp;
	@JsonProperty("price_usd") Double usdPrice;
	@JsonProperty("price_base_currency") Double baseCurrencyPrice;
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public static Long NUMBER_OF_CANDLESTICK_BARS = 20L;
	public static Long FACTOR = 2L;
	
	private Long SATOSHI = 100000000L; // 1 btc = 10^8 sat
	
	private Long satLastPrice;
	private Long satUpperBB;
	private Long satSma;
	private Long satLowerBB;
	
	public BollingerBand() {
		
	}
	
	public BollingerBand(String exchange, Double lastPrice) {
//		setInterval(interval);
		setExchange(exchange);
//		setSymbol(symbol);
		setLastPrice(lastPrice);
	}
	
	public BollingerBand(String exchange, String symbol, String interval, Double lastPrice) {
		setInterval(interval);
		setExchange(exchange);
		setSymbol(symbol);
		setLastPrice(lastPrice);
	}
	
	public boolean isOutOfUpperBollingerBand() {
		return lastPrice > upperBB;
	}
	
	public boolean isOutOfLowerBollingerBand() {
		return lastPrice < lowerBB;
	}
	
	public boolean isOutOfBands() {
		return isOutOfLowerBollingerBand() || isOutOfUpperBollingerBand();
	}
	
	public Double getPercentage() {
		if(percentage != null)
			return percentage;
		else if(isOutOfLowerBollingerBand())
			percentage = (double)(satLowerBB - satLastPrice) / satLowerBB;
		else if(isOutOfUpperBollingerBand())
			percentage = (double)(satLastPrice - satUpperBB) / satUpperBB;
		percentage *= 100.;
		return percentage;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getBaseCurrencySymbol() {
		return baseCurrencySymbol;
	}

	public void setBaseCurrencySymbol(String baseCurrencySymbol) {
		this.baseCurrencySymbol = baseCurrencySymbol;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
		this.satLastPrice = (long)(lastPrice * SATOSHI);
		this.percentage = null;
		this.comparativePercentage = null;
	}

	public Double getSma() {
		return sma;
	}

	public void setSma(Double sma) {
		this.sma = sma;
		this.satSma = (long)(sma * SATOSHI);
		this.percentage = null;
		this.comparativePercentage = null;
	}

	public Double getLowerBB() {
		return lowerBB;
	}

	public void setLowerBB(Double lowerBB) {
		this.lowerBB = lowerBB;
		this.satLowerBB = (long)(lowerBB * SATOSHI);
		this.percentage = null;
		this.comparativePercentage = null;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
		if(symbol.endsWith("BTC")) {
			baseCurrencySymbol = "BTC";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
		else if(symbol.endsWith("ETH")) {
			baseCurrencySymbol = "ETH";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
		else if(symbol.endsWith("BNB")) {
			baseCurrencySymbol = "BNB";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
		else if(symbol.endsWith("USDT")) {
			baseCurrencySymbol = "USDT";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
	}

	public Double getUpperBB() {
		return upperBB;
	}

	public void setUpperBB(Double upperBB) {
		this.upperBB = upperBB;
		this.satUpperBB = (long)(upperBB * SATOSHI);
		this.percentage = null;
		this.comparativePercentage = null;
	}

	public Double getComparativePercentage() {
		if(comparativePercentage != null) {
			return comparativePercentage;
		}
		if(isOutOfLowerBollingerBand()) {
			comparativePercentage = (double)(satLowerBB - satLastPrice) / (satSma - satLowerBB);
		}
		if(isOutOfUpperBollingerBand()) {
			comparativePercentage = (double)(satLastPrice - satUpperBB) / (satUpperBB - satSma);
		}
		comparativePercentage *= 100;
		return comparativePercentage;
		
	}

	public void setComparativePercentage(Double comparativePercentage) {
		this.comparativePercentage = comparativePercentage;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public Double getUsdPrice() {
		return usdPrice;
	}

	public void setUsdPrice(Double usdPrice) {
		this.usdPrice = usdPrice;
	}

	public Double getBaseCurrencyPrice() {
		return baseCurrencyPrice;
	}

	public void setBaseCurrencyPrice(Double baseCurrencyPrice) {
		this.baseCurrencyPrice = baseCurrencyPrice;
	}
}
