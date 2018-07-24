package com.dreamers.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.binance.api.client.domain.market.Candlestick;
import com.dreamers.object.BollingerBand;

public class Utilities {
	public static final int NONE_ROLE = 0;
	public static final int FREE_ROLE = 1;
	public static final int GOLD_ROLE = 2;
	public static final int DIAMOND_ROLE = 3;
	public static final int SUPERVIP_ROLE = 4;
	public static final int ADMIN_ROLE = 5;
	public static final int ROOT_ROLE = 10;
	
	public static final int MILLIS = 0;
	public static final int SECOND = 1;
	public static final int MINUTE = 2;
	public static final int HOUR = 3;
	public static final int DAY = 4;
	public static final int WEEK = 5;
	public static final int MONTH = 6;
	public static final int YEAR = 7;

	public static ToStringStyle TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;
	
	public static Double average(List<Double> arr) {
		if(arr.isEmpty())
			return null;
		Double average = null;
		Double sum = 0.;
		for(Double obj : arr) {
			sum += obj;
		}
		average = sum / arr.size();
		return average;
	}
	
	public static Double stdDeviation(List<Double> arr) {
		if(arr.isEmpty())
			return null;
		
		Double average = Utilities.average(arr);
		Double tmp = 0.;
		for(Double obj : arr) {
			tmp += (obj - average) * (obj - average);
		}
		tmp /= arr.size();
		return Math.sqrt(tmp);
	}
	
	public static BollingerBand calcBollingerBand(List<Candlestick> candlestickBars) {
		if (candlestickBars == null)
			return null;

		List<Double> closePrices = new ArrayList<>();
		for (Candlestick cdstBar : candlestickBars) {
			closePrices.add(Double.valueOf(cdstBar.getClose()));
		}
		
		BollingerBand bb = new BollingerBand("binance", Double.valueOf(candlestickBars.get(0).getClose()));
		bb.setTimestamp(System.currentTimeMillis());
		bb.setSma(Utilities.average(closePrices));
		Double stddev = Utilities.stdDeviation(closePrices);
		bb.setUpperBB(bb.getSma() + (stddev * BollingerBand.FACTOR));
		bb.setLowerBB(bb.getSma() - (stddev * BollingerBand.FACTOR));

		return bb;
	}
	
	public static boolean isTimestampOfTheDay(long timestamp, Calendar calendar) {
		Calendar timeToCheck = Calendar.getInstance();
		timeToCheck.setTimeInMillis(timestamp);
		
		if (calendar.get(Calendar.YEAR) == timeToCheck.get(Calendar.YEAR)
				&& calendar.get(Calendar.DAY_OF_YEAR) == timeToCheck.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}
	
	public static List<Double> calcRSI(List<Double> closePrices) {
		List<Double> rsis = new ArrayList<>();
		int j=0;
		Double avgGain = 0.;
		Double avgLoss = 0.;
		for (int i=closePrices.size()-2; i>=0; --i) {
			Double change = closePrices.get(i) - closePrices.get(i+1);
			
			if (j<14) {
				if (change > 0) {
					avgGain += change;
				}
				else {
					avgLoss += -change;
				}
				++j;
				continue;
			}
			
			if (j==14) {
				avgGain = avgGain / 14.;
				avgLoss = avgLoss / 14.;
				++j;
			}
			else {
				Double currentGain = change > 0. ? change : 0.;
				Double currentLoss = change > 0. ? 0. : -change;
				avgGain = ((avgGain * 13.) + currentGain) / 14.;
				avgLoss = ((avgLoss * 13.) + currentLoss) / 14.;
			}
			
			if (avgLoss == 0.) {
				rsis.add(100.);
				continue;
			}

			Double rs = avgGain / avgLoss;
			Double rsi = 100. - 100. / (1. + rs);
			
			rsis.add(rsi);
		}
		return rsis;
	}
	
	public static long calcTimeframeBetweenTimestamps(long firstTimestamp, long secondTimestamp, int timeFrame) {
		long interval = secondTimestamp - firstTimestamp;
		if (timeFrame == MILLIS) {
			return interval;
		}
		interval /= 1000;
		if (timeFrame == SECOND) {
			return interval;
		}
		interval /= 60;
		if (timeFrame == MINUTE) {
			return interval;
		}
		interval /= 60;
		if (timeFrame == HOUR) {
			return interval;
		}
		interval /= 24;
		if (timeFrame == DAY) {
			return interval;
		}
		interval /= 7;
		if (timeFrame == WEEK) {
			return interval;
		}
		interval /= 4;
		if (timeFrame == MONTH) {
			return interval;
		}
		interval /= 12;
		if (timeFrame == YEAR) {
			return interval;
		}
		return interval;
	}
}
