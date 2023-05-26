package model.portfolios;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserStock;

/**
 * A container for performing operations on portfolios for a user.
 */
public interface IPortfolioContainer {

  /**
   * Creates a portfolio with the specified arguments.
   *
   * @param nameOfPortfolio Name of portfolio to be created
   * @param pathName        absolute path where the portfolio will be stored
   * @param dateOfCreation  Date when portfolio needs to be created
   * @param isFlexible      Indicates if the portfolio is flexible or inflexible
   * @return The portfolio that was created
   */
  IUserPortfolio createPortfolio(String nameOfPortfolio, String pathName,
                                 String dateOfCreation, Boolean isFlexible);

  /**
   * Adds a stock to the specified portfolio.
   *
   * @param nameOfPortfolio Name of the portfolio where stock has to be added
   * @param userStock       The stock to be added
   * @return The stock that was added to the portfolio
   */
  UserStock addStockToPortfolio(String nameOfPortfolio, UserStock userStock);

  /**
   * Decrements a stock's balance from the portfolio. This function is used to sell stocks.
   *
   * @param nameOfPortfolio Name of the portfolio from which stock will be sold
   * @param userStock       The stock that will be sold
   * @return The stock that was sold
   */
  UserStock deleteStockFromPortfolio(String nameOfPortfolio, UserStock userStock);

  /**
   * Get the names of all portfolios for a user.
   *
   * @return A set of portfolio names
   */
  Set<String> getAllPortfolios();

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
   * The cost basis of the portfolio till the given date.
   *
   * @param portfolioName portfolio name
   * @param date          The cost basis till the given date
   * @return cost basis of the portfolio
   */
  double getCostBasisOfPortfolio(String portfolioName, String date);

  /**
   * Get the performance of portfolio as a bar graph.
   *
   * @param duration      start date of the performance
   * @param endDate       end date for the performance
   * @param portfolioName name of the portfolio whose performance will be calculated
   * @return A bar graph of the given portfolio that indicates the performance
   */
  String getPerformanceOfPortfolio(String duration, String endDate, String portfolioName);

  /**
   * Helper to get portfolio object by name.
   *
   * @param portfolioName The name of the portfolio to be fetched
   * @return The portfolio if it exists otherwise returns null
   */
  IUserPortfolio getPortfolioByName(String portfolioName);

  /**
   * Returns the list of stocks added to the specific portfolio.
   *
   * @param portfolioName portfolio name
   * @param stocks        stocks added to the specific portfolio
   * @return list of stocks added to the specific portfolio
   */
  List<UserStock> addAllStocksToPortfolio(String portfolioName, List<UserStock> stocks);

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

}
