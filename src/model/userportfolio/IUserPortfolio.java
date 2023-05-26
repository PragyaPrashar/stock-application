package model.userportfolio;

import java.util.List;
import java.util.Map;

/**
 * This interface provides operations on portfolio stock.
 * Operations such as userPortfolioComposition, totalValueOfPortfolio, deleteUserPortfolio,
 * addUserPortfolio, getAllStocksInAPortfolio, getPerformanceOfPortfolio,
 * getCostBasisOfPortfolio, etc.
 */
public interface IUserPortfolio {

  String getDateOfCreationOfPortfolio();

  /**
   * Get the composition of the portfolio. Composition indicates the companies where a user
   * holds stock and how many shares in each company.
   *
   * @return a map containing ticker symbol where the user holds stock
   *     and number of shares for that ticker symbol
   */
  Map<String, Long> userPortfolioComposition(String date);

  /**
   * Get the total value of portfolio. Total value of a portfolio is the sum of the values of
   * stocks in the portfolio on a specified date.
   *
   * @param date date
   * @return total value of portfolio as a double
   */
  double totalValueOfPortfolio(String date);

  /**
   * The function is used to sell a stock.
   *
   * @param userStock stock to be sold
   * @return sold stock
   */
  UserStock deleteUserPortfolio(UserStock userStock);

  /**
   * Adds a stock to the portfolio.
   *
   * @param userStock A user stock object that contains information about stock that a user holds
   * @return The stock that was added to the portfolio
   */
  UserStock addUserPortfolio(UserStock userStock);

  List<UserStock> addAllStocksToPortfolio(List<UserStock> stocks);

  /**
   * Helper function that checks if the portfolio is flexible or not.
   *
   * @return true if the portfolio is flexible and false if the portfolio is inflexible
   */
  boolean isFlexible();

  /**
   * Get the stocks in a portfolio.
   *
   * @return a list of stocks that are in this portfolio
   */
  List<UserStock> getAllStocksInAPortfolio();


  /**
   * Plot the performance of a portfolio as a bar chart for a specified period.
   *
   * @param startDate start date
   * @param endDate   end date
   * @return performance of the portfolio as a string
   */
  String getPerformanceOfPortfolio(String startDate, String endDate);

  /**
   * Getter to get name of portfolio.
   *
   * @return Portfolio name as a string
   */
  String getNameOfPortfolio();

  /**
   * Getter to get the cost basis of the portfolio on a specified date.
   *
   * @param date date till which cost basis needs to be calculated
   * @return cost basis as a double
   */
  double getCostBasisOfPortfolio(String date);

  /**
   * Getter to get the file path of the portfolio.
   *
   * @return file path of the portfolio as a string
   */
  String getFilePathOfPortfolio();
}
