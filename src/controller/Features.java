package controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserStock;

/**
 * Interface that represents features that the graphical user interface supports.
 */
public interface Features {


  /**
   * Creates a portfolio with the specified arguments.
   *
   * @param portfolioName  Name of portfolio to be created
   * @param filePath       absolute path where the portfolio will be stored
   * @param dateOfCreation Date when portfolio needs to be created
   * @param isFlexible     Indicates if the portfolio is flexible or inflexible
   * @return The portfolio that was created
   */
  IUserPortfolio createPortfolio(String portfolioName, String filePath,
                                 String dateOfCreation, boolean isFlexible);


  /**
   * Returns the composition from the specific portfolio.
   *
   * @param portfolioName name of the portfolio
   * @param date          date till which composition is required
   * @return map consisting composition of the portfolio
   */
  Map<String, Long> compositionOfPortfolio(String portfolioName, String date);


  /**
   * Returns the total value of the specified portfolio.
   *
   * @param nameOfPortfolio name of the portfolio
   * @param date            The total value till the given date
   * @return total value of the portfolio
   */
  double totalValueOfPortfolio(String nameOfPortfolio, String date);

  /**
   * Adds a stock to the specified portfolio.
   * @param nameOfPortfolio Name of the portfolio where stock has to be added
   * @param userStock The stock to be added
   */
  void addStockToPortfolio(String nameOfPortfolio, UserStock userStock);

  /**
   * Decrements a stock's balance from the portfolio. This function is used to sell stocks.
   * @param nameOfPortfolio Name of the portfolio from which stock will be sold
   * @param userStock The stock that will be sold
   */
  void deleteStockFromPortfolio(String nameOfPortfolio, UserStock userStock);


  /**
   * Get the names of all portfolios for a user.
   *
   * @return A set of portfolio names
   */

  Set<String> getAllPortfolios();

  /**
   * The cost basis of the portfolio till the given date.
   * @param portfolioName portfolio name
   * @param date The cost basis till the given date
   */

  void getCostBasisOfPortfolio(String portfolioName, String date);


  /**
   * Validates if the date is in the correct format.
   *
   * @param date date that needs to be validated
   * @return true if the date is valid or false if the date is invalid
   */

  boolean validateDate(String date);

  /**
   * Get all the ticker symbols supported by the api.
   *
   * @return set of all ticker symbols
   */
  Set<String> getAllTickerSymbols();

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
  void dollarCostAveraging(String portfolioName, String startDateString, String endDateString,
                           List<UserStock> stocks, long periodicInterval, String timeUnit);

  /**
   * Adds stocks to an existing portfolio.
   * @param portfolioName name of the portfolio in which stock has to be added
   * @param stocks stocks to be added
   */
  void addAllStocksToPortfolio(String portfolioName,List<UserStock> stocks);
}
