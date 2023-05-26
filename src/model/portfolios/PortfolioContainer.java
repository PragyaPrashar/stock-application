package model.portfolios;

import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import model.portfoliotype.FlexiblePortfolio;
import model.portfoliotype.InflexiblePortfolio;
import model.portfoliotype.PortfolioType;
import model.userportfolio.UserStock;
import utils.FileUtils;

/**
 * A class for performing operations on portfolio for a user.
 */
public class PortfolioContainer implements IPortfolioContainer {


  protected static String PORTFOLIO_FILE_PATH = "resources/allportfolios.txt";
  Map<String, PortfolioType> map;
  Set<String> portfoliosInFile;

  /**
   * Constructor that initialises portfolio container, it loads the existing portfolios if present
   * from a file and stores it in a cache.
   */
  public PortfolioContainer() {
    map = loadPortFolios(PORTFOLIO_FILE_PATH);
    portfoliosInFile = new LinkedHashSet<>();
  }

  /**
   * Helper function that loads portfolios from the specified file.
   *
   * @param pathName file pathname
   * @return map consisting of portfolio name and the portfolio object
   */
  private Map<String, PortfolioType> loadPortFolios(String pathName) {
    List<String> portFolios = FileUtils.readFromFile(Path.of(pathName));
    if (portFolios.isEmpty()) {
      return new HashMap<>();
    }
    String dateOfCreation = portFolios.get(0).split("~")[2];

    Map<String, PortfolioType> map = portFolios.stream().collect(Collectors
            .toMap(content -> content.split("~")[0], content -> {
              String[] elements = content.split("~");
              PortfolioType portfolioType;
              if (Boolean.parseBoolean(elements[3])) {
                portfolioType = new FlexiblePortfolio(elements[1], elements[0], dateOfCreation,
                Boolean.parseBoolean(elements[3]));
              } else {
                portfolioType = new InflexiblePortfolio(elements[1], elements[0], dateOfCreation,
                Boolean.parseBoolean(elements[3]));
              }
              return portfolioType;
            }, (first, second) -> second));
    return map;
  }

  /**
   * Creates a portfolio with the specified arguments.
   *
   * @param nameOfPortfolio Name of portfolio to be created
   * @param pathName        absolute path where the portfolio will be stored
   * @param dateOfCreation  Date when portfolio needs to be created
   * @param isFlexible      Indicates if the portfolio is flexible or inflexible
   * @return The portfolio that was created
   */
  public PortfolioType createPortfolio(String nameOfPortfolio, String pathName,
                                       String dateOfCreation, Boolean isFlexible) {

    if (map.containsKey(nameOfPortfolio)) {
      return map.get(nameOfPortfolio);
    }
    PortfolioType portfolioType;
    if (isFlexible) {
      portfolioType = new FlexiblePortfolio(pathName,
              nameOfPortfolio, dateOfCreation, isFlexible);
    } else {
      portfolioType = new InflexiblePortfolio(pathName,
              nameOfPortfolio, dateOfCreation, isFlexible);
    }

    map.put(portfolioType.getNameOfPortfolio(), portfolioType);
    return portfolioType;
  }

  /**
   * Get the names of all portfolios for a user.
   *
   * @return A set of portfolio names
   */

  @Override
  public Set<String> getAllPortfolios() {
    return map.keySet();
  }

  /**
   * Returns the total value of the specified portfolio.
   *
   * @param nameOfPortfolio name of the portfolio
   * @param date            The total value till the given date
   * @return total value of the portfolio
   */
  @Override
  public double totalValueOfPortfolio(String nameOfPortfolio, String date) {
    PortfolioType portfolio = map.get(nameOfPortfolio);
    return portfolio.totalValueOfPortfolio(date);
  }


  /**
   * Adds a stock to the specified portfolio.
   *
   * @param nameOfPortfolio Name of the portfolio where stock has to be added
   * @param userStock       The stock to be added
   * @return The stock that was added to the portfolio
   */
  @Override
  public UserStock addStockToPortfolio(String nameOfPortfolio, UserStock userStock) {

    PortfolioType userPortfolio = map.get(nameOfPortfolio);

    return userPortfolio.addUserPortfolio(userStock);
  }

  /**
   * Decrements a stock's balance from the portfolio. This function is used to sell stocks.
   *
   * @param nameOfPortfolio Name of the portfolio from which stock will be sold
   * @param userStock       The stock that will be sold
   * @return The stock that was sold
   */
  @Override
  public UserStock deleteStockFromPortfolio(String nameOfPortfolio, UserStock userStock) {
    PortfolioType userPortfolio = map.get(nameOfPortfolio);
    return userPortfolio.deleteUserPortfolio(userStock);
  }

  /**
   * Returns the composition from the specific portfolio.
   *
   * @param portfolioName name of the portfolio
   * @return map consisting composition of the portfolio
   */
  @Override
  public Map<String, Long> compositionOfPortfolio(String portfolioName, String date) {
    if (map.containsKey(portfolioName)) {
      PortfolioType portfolio = map.get(portfolioName);
      return portfolio.userPortfolioComposition(date);
    }
    return null;

  }

  /**
   * The cost basis of the portfolio till the given date.
   *
   * @param portfolioName portfolio name
   * @param date          The cost basis till the given date
   * @return cost basis of the portfolio
   */
  @Override
  public double getCostBasisOfPortfolio(String portfolioName, String date) {
    if (map.containsKey(portfolioName)) {
      PortfolioType portfolio = map.get(portfolioName);
      return portfolio.getCostBasisOfPortfolio(date);
    }
    return 0;
  }


  /**
   * Get the performance of portfolio as a bar graph.
   *
   * @param startDate     start date of the performance
   * @param endDate       end date for the performance
   * @param portfolioName name of the portfolio whose performance will be calculated
   * @return A bar graph of the given portfolio that indicates the performance
   */
  @Override
  public String getPerformanceOfPortfolio(String startDate, String endDate, String portfolioName) {
    if (map.containsKey(portfolioName)) {
      PortfolioType portfolio = map.get(portfolioName);
      return portfolio.getPerformanceOfPortfolio(startDate, endDate);
    }
    return "";

  }

  /**
   * Helper to get portfolio object by name.
   *
   * @param portfolioName The name of the portfolio to be fetched
   * @return The portfolio if it exists otherwise returns null
   */
  @Override
  public PortfolioType getPortfolioByName(String portfolioName) {
    return map.get(portfolioName);
  }

  /**
   * Returns the list of stocks added to the specific portfolio.
   *
   * @param portfolioName portfolio name
   * @param stocks        stocks added to the specific portfolio
   * @return list of stocks added to the specific portfolio
   */
  @Override
  public List<UserStock> addAllStocksToPortfolio(String portfolioName, List<UserStock> stocks) {
    if (map.containsKey(portfolioName)) {
      PortfolioType portfolioType = map.get(portfolioName);
      return portfolioType.addAllStocksToPortfolio(stocks);
    }
    return stocks;
  }

  /**
   * Applies dollar cost averaging strategy to the portfolio.
   *
   * @param portfolioName    portfolio name in which the strategy needs to be applied
   * @param startDateString  start date of the strategy
   * @param endDateString    end date of the strategy
   * @param stocks           stocks on which the strategy needs to be applied
   * @param periodicInterval time after which strategy should be applied again
   * @param timeUnit         unit of periodic interval in terms of seconds, days, hours, etc.
   */
  @Override
  public void dollarCostAveraging(String portfolioName, String startDateString,
                                  String endDateString, List<UserStock> stocks,
                                  long periodicInterval, String timeUnit) {


    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
            Locale.US);
    long delay = 0;
    try {
      TemporalAccessor startDateTemporal = dateTimeFormatter.parse(startDateString);
      delay = Date.from(Instant.from(startDateTemporal)).getTime() - Date.from(Instant.now())
              .getTime();

    } catch (Exception e) {
      //
    }

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    if (endDateString != null && !endDateString.isEmpty()
            && ((LocalDate.parse(endDateString).compareTo(LocalDate.now()) == 0)
            || (LocalDate.parse(endDateString)
            .compareTo(LocalDate.parse(startDateString)) == -1))) {
      executorService.shutdown();
      return;
    }
    Runnable task = () -> {
      addAllStocksToPortfolio(portfolioName, stocks);
    };
    try {
      executorService.scheduleAtFixedRate(task, delay, periodicInterval,
              TimeUnit.valueOf(timeUnit));
    } catch (RejectedExecutionException e) {
      System.out.println("End date cannot be same as today");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
