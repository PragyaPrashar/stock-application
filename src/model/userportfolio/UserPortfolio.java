package model.userportfolio;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import utils.FileUtils;

import static utils.APIUtils.fetchAPIDataFromJSON;

/**
 * This interface provides operations on portfolio stock.
 * Operations such as userPortfolioComposition, totalValueOfPortfolio, deleteUserPortfolio,
 * addUserPortfolio, getAllStocksInAPortfolio, getPerformanceOfPortfolio,
 * getCostBasisOfPortfolio, etc.
 */
public class UserPortfolio implements IUserPortfolio {

  private final List<UserStock> userStockList;
  private final String pathName;
  private final String nameOfPortfolio;

  private final String dateOfCreation;

  private final Boolean isFlexible;
  private Map<String, Map<String, Double>> apiMap;


  /**
   * Constructor that initialises UserPortfolio Object.
   *
   * @param pathName        pathname
   * @param nameOfPortfolio name of the portfolio
   * @param dateOfCreation  date of creation
   * @param isFlexible      isFlexible that specify whether the portfolio is flexible or inflexible
   */
  public UserPortfolio(String pathName, String nameOfPortfolio,
                       String dateOfCreation, Boolean isFlexible) {
    this.pathName = pathName;
    this.userStockList = getPortfolioFromFile();
    this.nameOfPortfolio = nameOfPortfolio;
    this.isFlexible = isFlexible;
    if (this.apiMap == null) {
      this.apiMap = new HashMap<>();
      userStockList.forEach(u -> {
        this.apiMap.putAll(fetchAPIDataFromJSON(u.getTickerSymbol()));
      });
    }
    this.dateOfCreation = dateOfCreation;
  }

  /**
   * Getter to get the file path of the portfolio.
   *
   * @return file path of the portfolio as a string
   */
  @Override
  public String getFilePathOfPortfolio() {
    return pathName;
  }

  /**
   * Helper function that checks if the portfolio is flexible or not.
   *
   * @return true if the portfolio is flexible and false if the portfolio is inflexible
   */
  @Override
  public boolean isFlexible() {
    return isFlexible;
  }

  /**
   * Get the stocks in a portfolio.
   *
   * @return a list of stocks that are in this portfolio
   */
  @Override
  public List<UserStock> getAllStocksInAPortfolio() {
    return userStockList;
  }

  private List<UserStock> getPortfolioFromFile() {
    List<String> contentFromFile = FileUtils.readFromFile(Path.of(pathName));
    List<UserStock> list = contentFromFile.stream()
            .map(content -> {
              String[] stockData = content.split("~");
              UserStock userStock = UserStock.getUserStockBuilder()
                      .date(stockData[0])
                      .tickerSymbol(stockData[1])
                      .stockBought(Long.parseLong(stockData[2]))
                      .buyingPrice(Float.parseFloat(stockData[3]))
                      .stocksSold(Long.parseLong(stockData[4]))
                      .sellingPrice(Float.parseFloat(stockData[5]))
                      .balance(Long.parseLong(stockData[6]))
                      .commissionFeesForBuying(Float.parseFloat(stockData[7]))
                      .commissionFeesForSelling(Float.parseFloat(stockData[8]))
                      .build();
              return userStock;
            }).collect(Collectors.toList());
    return list;
  }

  /**
   * Get the composition of the portfolio. Composition indicates the companies where a user
   * holds stock and how many shares in each company.
   *
   * @return a map containing ticker symbol where the user holds stock
   *     and number of shares for that ticker symbol
   */
  @Override
  public Map<String, Long> userPortfolioComposition(String date) {


    Map<String, Long> map = userStockList
            .stream()
            .filter(u -> u.getBalance() > 0)
            .filter(u -> {
              LocalDate givenDate = LocalDate.parse(date);
              int differenceBetweenDates = givenDate.compareTo(LocalDate
                      .parse(this.dateOfCreation));
              if (differenceBetweenDates < 0) {
                System.out.println("The date on which you want to see composition is "
                        + "before the date of creation of portfolio.");
                return false;
              }
              if (givenDate.compareTo(LocalDate.parse(u.getDate())) < 0) {
                System.out.println("The date on which you want to see composition is "
                        + "before the date on which stock was bought/sold");
                return false;
              }
              return true;
            })
            .collect(Collectors.toMap(UserStock::getTickerSymbol,
                    UserStock::getBalance, (a, b) -> b));
    return map;

  }

  /**
   * Getter to get date of creation of portfolio.
   *
   * @return date as a string representation
   */
  public String getDateOfCreationOfPortfolio() {
    return this.dateOfCreation;
  }


  /**
   * Get the total value of portfolio. Total value of a portfolio is the sum of the values of
   * stocks in the portfolio on a specified date.
   *
   * @param date date
   * @return total value of portfolio as a double
   */
  @Override
  public double totalValueOfPortfolio(String date) {
    int differenceBetweenDates = LocalDate.parse(date)
            .compareTo(LocalDate.parse(this.dateOfCreation));
    if (differenceBetweenDates < 0) {
      System.out.println("The date till which you want to see total value is before the date "
              + "of creation of portfolio. Hence total value of portfolio is zero");
      return Double.MIN_VALUE;
    }
    double totalValue = userStockList.stream()
            .map((u) -> {
              Map<String, Double> tmp = this.apiMap.get(u.getTickerSymbol());
              if (tmp != null && tmp.get(date) != null) {
                return u.getBalance() * tmp.get(date);
              } else {
                System.out.println("We do not have closing price data yet for the " + date + "."
                        + "Please give a different date for stock " + u.getTickerSymbol());
              }
              return 0.0;
            })
            .reduce((u, v) -> u + v)
            .orElse(0.0);

    return totalValue;
  }

  /**
   * helper to get distinct stocks from the list of stocks.
   *
   * @return a map of ticker symbol to user stock
   */
  private Map<String, UserStock> getUniqueStocks() {
    Map<String, UserStock> userStockMap = new LinkedHashMap<>();
    Set<String> companiesVisited = new LinkedHashSet<>();

    for (int i = userStockList.size() - 1; i >= 0; i--) {
      String companyName = userStockList.get(i).getTickerSymbol();
      if (!companiesVisited.contains(companyName)) {
        companiesVisited.add(companyName);
        userStockMap.put(companyName, userStockList.get(i));
      }
    }
    return userStockMap;
  }

  /**
   * Adds a stock to the portfolio.
   *
   * @param userStock A user stock object that contains information about stock that a user holds
   * @return The stock that was added to the portfolio
   */
  @Override
  public UserStock addUserPortfolio(UserStock userStock) {
    /*
    Whenever a stock of a new company is purchased, make an API call to get the closing price for
    buying a stock of that company.
     */
    if (!apiMap.containsKey(userStock.getTickerSymbol())) {
      Map<String, Map<String, Double>> apiMapOfNewTicker = fetchAPIDataFromJSON(userStock
              .getTickerSymbol());
      apiMap.putAll(apiMapOfNewTicker);
    }
    Map<String, Double> stringDoubleMap = apiMap.get(userStock.getTickerSymbol());
    if (stringDoubleMap == null || stringDoubleMap.isEmpty()) {
      return null;
    }
    Double closingPrice = stringDoubleMap.get(userStock.getDate());
    if (closingPrice == null) {
      return null;
    }
    /*
    Set the buying and selling price to the closing price obtained from the API.
     */
    userStock.setBuyingPrice(closingPrice.floatValue());
    userStock.setSellingPrice(closingPrice.floatValue());
    Map<String, UserStock> userStockMap = getUniqueStocks();
    UserStock mostRecentUserStock = userStockMap.get(userStock.getTickerSymbol());
    if (mostRecentUserStock == null) {
      userStock.setBalance(userStock.getStocksBought());
    } else {
      userStock.setBalance(mostRecentUserStock.getBalance() + userStock.getStocksBought());
    }
    this.userStockList.add(userStock);
    /*
    Persist buying transaction in a file. If the file already exists then now new file is created.
    Buying transaction is appended to the end of the file.
     */
    Path createFile = null;
    try {
      createFile = FileUtils.createFile(pathName);
    } catch (Exception e) {
      createFile = Paths.get(pathName);
    } finally {
      FileUtils.appendContentToFile(createFile, userStock.toString().concat("\n"));
    }
    return userStock;
  }

  /**
   * Adds all stocks to this portfolio.
   *
   * @param stocks stocks to be added to the portfolio
   * @return list of stocks that were added
   */
  @Override
  public List<UserStock> addAllStocksToPortfolio(List<UserStock> stocks) {
    return null;
  }


  /**
   * Getter to get name of portfolio.
   *
   * @return Portfolio name as a string
   */
  public String getNameOfPortfolio() {
    return nameOfPortfolio;
  }

  /**
   * Getter to get the cost basis of the portfolio on a specified date.
   *
   * @param date date till which cost basis needs to be calculated
   * @return cost basis as a double
   */
  @Override
  public double getCostBasisOfPortfolio(String date) {
    double costBasis = userStockList.stream()
            .map((u) -> {
              LocalDate givenDate = LocalDate.parse(date);
              int differenceBetweenDates = givenDate.compareTo(LocalDate
                      .parse(this.dateOfCreation));
              if (differenceBetweenDates < 0) {
                System.out.println("The date on which you want to buy is "
                        + "before the date of creation of portfolio. Hence cost basis  "
                        + "of portfolio is zero");
                return 0.0;
              }
              if (givenDate.compareTo(LocalDate.parse(u.getDate())) < 0) {
                return 0.0;
              }
              float costBasisForBuy = u.getStocksBought() * u.getBuyingPrice()
                      + u.getCommissionFeesForBuying();
              float costBasisForSell = u.getCommissionFeesForSelling();
              return (double) (costBasisForBuy + costBasisForSell);

            })
            .reduce((u, v) -> u + v)
            .orElse(0.0);

    return costBasis;
  }

  /**
   * The function is used to sell a stock.
   *
   * @param userStock stock to be sold
   * @return sold stock
   */
  public UserStock deleteUserPortfolio(UserStock userStock) {
    Map<String, Double> stringDoubleMap = apiMap.get(userStock.getTickerSymbol());
    if (stringDoubleMap == null || stringDoubleMap.isEmpty()) {
      return null;
    }
    Double closingPrice = stringDoubleMap.get(userStock.getDate());
    if (closingPrice == null) {
      return null;
    }
    userStock.setBuyingPrice(closingPrice.floatValue());
    userStock.setSellingPrice(closingPrice.floatValue());
    Map<String, UserStock> userStockMap = getUniqueStocks();
    UserStock mostRecentUserStock = userStockMap.get(userStock.getTickerSymbol());
    if (mostRecentUserStock != null
            && mostRecentUserStock.getBalance() >= userStock.getStocksSold()) {

      userStock.setBalance(mostRecentUserStock.getBalance() - userStock.getStocksSold());
      this.userStockList.add(userStock);
    } else {
      return null;
    }
    Path createFile = null;
    try {
      createFile = FileUtils.createFile(pathName);
    } catch (Exception e) {
      createFile = Paths.get(pathName);
    } finally {
      FileUtils.appendContentToFile(createFile, userStock.toString().concat("\n"));
    }
    return userStock;
  }

  /**
   * Plot the performance of a portfolio as a bar chart for a specified period.
   *
   * @param startDate start date
   * @param endDate   end date
   * @return performance of the portfolio as a string
   */
  public String getPerformanceOfPortfolio(String startDate, String endDate) {
    return "";
  }

  /**
   * Equals method to compare two portfolios.
   *
   * @param o the other portfolio object
   * @return true if the objects are equals else false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserPortfolio portfolio = (UserPortfolio) o;

    if (!userStockList.equals(portfolio.userStockList)) {
      return false;
    }
    if (!pathName.equals(portfolio.pathName)) {
      return false;
    }
    if (!nameOfPortfolio.equals(portfolio.nameOfPortfolio)) {
      return false;
    }
    if (!dateOfCreation.equals(portfolio.dateOfCreation)) {
      return false;
    }
    return isFlexible.equals(portfolio.isFlexible);
  }

  /**
   * Hash code of a portfolio.
   *
   * @return hashcode of the portfolio
   */
  @Override
  public int hashCode() {
    int result = userStockList.hashCode();
    result = 31 * result + pathName.hashCode();
    result = 31 * result + nameOfPortfolio.hashCode();
    result = 31 * result + dateOfCreation.hashCode();
    result = 31 * result + isFlexible.hashCode();
    return result;
  }

}