package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import enums.TickerSymbol;

/**
 * A utility class to fetch data from API.
 */
public class APIUtils {
  /**
   * a constructor to avoid create the object of utility class.
   */
  private APIUtils() {
    throw new IllegalCallerException("Attempting to call constructor of Util class");
  }

  /**
   * Fetch the daily stock data from AplhaVantage API as JSON.
   *
   * @param symbol the ticker symbol of the company for which JSON data needs to be fetched
   * @return map of JSON data
   */
  public static Map<String, Map<String, Double>> fetchAPIDataFromJSON(String symbol) {
    Map<String, Map<String, Double>> map = new LinkedHashMap<>();
    String apiKey = "TQEYVSSUFEDJAQU5";
    URL url;


    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + symbol + "&apikey=" + apiKey);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in;
    StringBuilder output = new StringBuilder();
    try {
      /*
       * The block below should be COMMENTED for testing purpose only.
       */
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        char ch = (char) b;
        output.append(ch);
      }

      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(output.toString());
      Iterator<JsonNode> stockDataArrayValueIterator = jsonNode
              .get("Time Series (Daily)").elements();
      Iterator<String> stockDataArrayKeyIterator = jsonNode
              .get("Time Series (Daily)").fieldNames();
      while (stockDataArrayKeyIterator.hasNext()) {
        String date = stockDataArrayKeyIterator.next().split(" ")[0];
        JsonNode stockDataOfADate = stockDataArrayValueIterator.next();
        double closingPrice = stockDataOfADate.get("4. close").asDouble();
        if (map.containsKey(symbol)) {

          Map<String, Double> tmp = map.get(symbol);
          tmp.put(date, closingPrice);
        } else {
          Map<String, Double> tmp = new LinkedHashMap<>();
          tmp.put(date, closingPrice);
          map.put(symbol, tmp);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return map;
  }

  /**
   * Fetch the data from the Alphavantage API. Closing price and TickerSymbol
   * are fetched and returned in the form of hashmap.
   *
   * @return A hashmap containing closing price for each ticker symbol.
   */
  public static Map<TickerSymbol, Double> fetchAPIDataFromCSV(TickerSymbol symbol) {
    Map<TickerSymbol, Double> map = new HashMap<>();
    String apiKey = "TQEYVSSUFEDJAQU5";
    URL url;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + symbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");

    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {

      in = url.openStream();


      while (in.read() != '\n') {
      }
      int b;
      while ((b = in.read()) != '\n') {
        char ch = (char) b;
        output.append(ch);
      }

      String[] latestValue = output.toString().split(",");
      int length = latestValue.length;
      double priceOfShare = 0.0;
      try {
        priceOfShare = Double.parseDouble(latestValue[length - 2]);
      } catch (Exception e) {
        for (int k = 0; k < latestValue.length; k++) {
          System.out.println("Exception occurred while parsing latestValue " + latestValue[k]);
        }

      }
      map.put(symbol, priceOfShare);
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + symbol);
    }

    return map;
  }

  /**
   * Get all ticker symbols from the API.
   * @return a map of ticker symbol and the name of the company
   */
  public static Map<String, String> fetchAllTickerSymbolsFromAPI() {
    Map<String, String> map = new LinkedHashMap<>();
    String apiKey = "TQEYVSSUFEDJAQU5";
    URL url;
    try {
      url = new URL("https://www.alphavantage.co/query?function=LISTING_STATUS"
              + "&apikey=" + apiKey);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in;
    StringBuffer output = new StringBuffer();
    try {
      /*
       * The block below should be COMMENTED for testing purpose only.
       */
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        char ch = (char) b;
        output.append(ch);
      }
      String[] lines = output.toString().split("\n");
      for (int i = 1; i < lines.length; i++) {
        String[] companyDetails = lines[i].split(",");
        String tickerSymbol = companyDetails[0];

        if (!map.containsKey(tickerSymbol)) {
          map.put(tickerSymbol, companyDetails[1]);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return map;
  }
}
