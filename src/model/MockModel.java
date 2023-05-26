package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserPortfolio;
import model.userportfolio.UserStock;

/**
 * A Mock model that is used for testing controller in isolation.
 * This mock model keeps track of input that controller receives so that
 * in test class we can test if the controller received the input correctly.
 */
public class MockModel implements IModel {

  private final StringBuilder log;

  private final IUserPortfolio userPortfolio;

  /**
   * Contructor to initialize MockModel object.
   *
   * @param log a StringBuilder to keep track of input given to the controller
   */
  public MockModel(StringBuilder log, boolean isFlexible) {
    this.log = log;
    userPortfolio = new UserPortfolio("test",
            "test/resources/test.txt", "2022-10-27", isFlexible);
  }

  /**
   * Creates a portfolio object using the specified parameters.
   *
   * @param nameOfPortfolio name of portfolio to be created
   * @param pathName        absolute path where the portfolio will be stored
   * @param dateOfCreation  Date when portfolio needs to be created
   * @param isFlexible      indicates if the portfolio is flexible or inflexible
   * @return Portfolio that was created
   */
  @Override
  public IUserPortfolio createPortfolio(String nameOfPortfolio, String pathName,
                                        String dateOfCreation, Boolean isFlexible) {
    log.append(nameOfPortfolio).append(pathName).append(dateOfCreation);
    return new UserPortfolio(nameOfPortfolio, pathName, dateOfCreation, isFlexible);
  }

  /**
   * Adds a stock to the specified portfolio.
   *
   * @param nameOfPortfolio name of the portfolio where stock has to be added
   * @param userStock       the stock to be added
   * @return the stock that was added
   */
  @Override
  public UserStock addStockToPortfolio(String nameOfPortfolio, UserStock userStock) {
    return userStock;
  }

  /**
   * Getter for getting all portfolios of a user.
   *
   * @return set of portfolios
   */
  @Override
  public Set<String> getAllPortfolios() {
    return Set.of("test");
  }

  /**
   * Get the composition of the portfolio with the specified name.
   *
   * @param portfolioName name of the portfolio
   * @return A map depicting composition of the portfolio.
   */
  @Override
  public Map<String, Long> compositionOfPortfolio(String portfolioName, String date) {
    return new HashMap<>();
  }

  /**
   * Function that sells stock from the portfolio.
   *
   * @param nameOfPortfolio name of the portfolio from which stock will be sold
   * @param userStock       the stock that will be sold
   * @return the stock that was sold
   */
  public UserStock deleteStockFromPortfolio(String nameOfPortfolio, UserStock userStock) {
    return userStock;
  }

  /**
   * Calculates the total value of the specified portfolio with the given name and till the given
   * date.
   *
   * @param nameOfPortfolio name of the portfolio
   * @param date            the total value till the given date
   * @return the total value of the portfolio as a double
   */
  @Override
  public double totalValueOfPortfolio(String nameOfPortfolio, String date) {
    return 0;
  }

  /**
   * Cost basis of the portfolio with the specified name and till the specified date.
   *
   * @param portfolioName portfolio name
   * @param date          the cost basis till the given date
   * @return cost basis of the portfolio as a double
   */
  @Override
  public double getCostBasisOfPortfolio(String portfolioName, String date) {
    return 0;
  }

  /**
   * Get the performance of the portfolio as a bar graph.
   *
   * @param startDate     start date of the performance
   * @param endDate       end date for the performance
   * @param portfolioName name of the portfolio whose performance will be calculated
   * @return the bar graph as a string
   */
  @Override
  public String getPerformanceOfPortfolio(String startDate, String endDate, String portfolioName) {
    return "";
  }

  /**
   * Getter to get the portfolio by name.
   *
   * @param portfolioName the name of the portfolio to be fetched
   * @return the portfolio if it exists otherwise returns null
   */
  @Override
  public IUserPortfolio getPortfolioByName(String portfolioName) {
    return userPortfolio;
  }

  /**
   * Adds all the stocks to the given portfolio.
   *
   * @param portfolioName portfolio name
   * @param stocks        stocks added to the specific portfolio
   * @return the list of stocks that were added
   */
  @Override
  public List<UserStock> addAllStocksToPortfolio(String portfolioName, List<UserStock> stocks) {
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
    //
  }
}
