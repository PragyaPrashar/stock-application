package enums;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.FileUtils;

import static utils.APIUtils.fetchAllTickerSymbolsFromAPI;

/**
 * Class representing ticker symbols.
 */
public class TickerSymbol {
  public static String DEFAULT = "";
  private static String TICKER_FILE_PATH = "resources/alltickers.txt";

  private static Map<String, String> map;

  /**
   * Creates the object of ticker symbol and initialises a map which will contain
   * all the ticker symbol from the api.
   */
  public TickerSymbol() {
    map = new LinkedHashMap<>();
    Path createFile;
    try {
      createFile = FileUtils.createFile(TICKER_FILE_PATH);
      map = fetchAllTickerSymbolsFromAPI();
      StringBuffer sb = new StringBuffer();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        sb.append(entry.getKey()).append("~").append(entry.getValue()).append("\n");
      }
      FileUtils.appendContentToFile(createFile, sb.toString());
    } catch (Exception e) {
      map = getTickersFromFile();
    }

  }

  /**
   * Function returns set of ticker symbol.
   *
   * @return all the ticker symbols
   */
  public static Set<String> getAllTickerSymbols() {
    return map.keySet();
  }

  /**
   * Returns a map of ticker symbol and company name of that ticker symbol.
   *
   * @return map of ticker symbol and company name of that ticker symbol
   */
  public Map<String, String> getTickersFromFile() {
    Map<String, String> map = new LinkedHashMap<>();
    List<String> contentFromFile = FileUtils.readFromFile(Path.of(TICKER_FILE_PATH));
    for (String content : contentFromFile) {
      String[] tickerDetails = content.split("~");
      if (tickerDetails.length >= 2) {
        map.put(tickerDetails[0], tickerDetails[1]);
      }
    }
    return map;
  }

  /**
   * Function returns company name.
   *
   * @return company name as a string representation
   */
  public String getCompanyName(String symbol) {
    return map.get(symbol);
  }
}
