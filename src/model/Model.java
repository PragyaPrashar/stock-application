package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.portfolios.IPortfolioContainer;
import model.portfolios.PortfolioContainer;
import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserStock;

/**
 * Model class that represents model in an MVC architecture.
 */
public class Model implements IModel {

  private final IPortfolioContainer portfolioContainer;

  /**
   * Constructor to initialize the model.
   */
  public Model() {
    this.portfolioContainer = new PortfolioContainer();
  }

  /**
   * Function that creates a portfolio.
   *
   * @param nameOfPortfolio Name of portfolio to be created
   * @param pathName        absolute path where the portfolio will be stored
   * @param dateOfCreation  Date when portfolio needs to be created
   * @param isFlexible      Indicates if the portfolio is flexible or inflexible
   * @return IUserPortfolio object
   */
  @Override
  public IUserPortfolio createPortfolio(String nameOfPortfolio, String pathName,
                                        String dateOfCreation, Boolean isFlexible) {
    return portfolioContainer.createPortfolio(nameOfPortfolio, pathName, dateOfCreation,
            isFlexible);
  }

  /**
   * Function that adds stocks to portfolio.
   *
   * @param nameOfPortfolio Name of the portfolio where stock has to be added
   * @param userStock       The stock to be added
   * @return UserStock object
   */
  @Override
  public UserStock addStockToPortfolio(String nameOfPortfolio, UserStock userStock) {
    return portfolioContainer.addStockToPortfolio(nameOfPortfolio, userStock);
  }

  /**
   * Function that sells stock from the portfolio.
   *
   * @param nameOfPortfolio Name of the portfolio from which stock will be sold
   * @param userStock       The stock that will be sold
   * @return The stock that was sold
   */
  @Override
  public UserStock deleteStockFromPortfolio(String nameOfPortfolio, UserStock userStock) {
    return portfolioContainer.deleteStockFromPortfolio(nameOfPortfolio, userStock);
  }

  /**
   * Function that get set of all portfolios.
   *
   * @return set of portfolios
   */
  @Override
  public Set<String> getAllPortfolios() {
    return portfolioContainer.getAllPortfolios();
  }

  /**
   * Function that returns composition of portfolio.
   *
   * @param portfolioName name of the portfolio
   * @return composition of the portfolio
   */
  @Override
  public Map<String, Long> compositionOfPortfolio(String portfolioName, String date) {
    return portfolioContainer.compositionOfPortfolio(portfolioName, date);
  }

  /**
   * Function that returns total value of a particular portfolio on a given date.
   *
   * @param nameOfPortfolio name of the portfolio
   * @param date            The total value till the given date
   * @return total value of the portfolio as a double
   */
  @Override
  public double totalValueOfPortfolio(String nameOfPortfolio, String date) {
    return portfolioContainer.totalValueOfPortfolio(nameOfPortfolio, date);
  }

  /**
   * Function that returns cost basis of a particular portfolio on a given date.
   *
   * @param portfolioName portfolio name
   * @param date          The cost basis till the given date
   * @return cost basis of a portfolio
   */
  @Override
  public double getCostBasisOfPortfolio(String portfolioName, String date) {
    return portfolioContainer.getCostBasisOfPortfolio(portfolioName, date);
  }

  /**
   * Function that returns performance of a particular portfolio for a particular period.
   *
   * @param startDate     start date of the performance
   * @param endDate       end date for the performance
   * @param portfolioName name of the portfolio whose performance will be calculated
   * @return performance of the portfolio as a string
   */
  @Override
  public String getPerformanceOfPortfolio(String startDate, String endDate, String portfolioName) {
    return portfolioContainer.getPerformanceOfPortfolio(startDate, endDate, portfolioName);
  }

  /**
   * Function that returns portfolio by name.
   *
   * @param portfolioName The name of the portfolio to be fetched
   * @return user portfolio
   */
  @Override
  public IUserPortfolio getPortfolioByName(String portfolioName) {
    return portfolioContainer.getPortfolioByName(portfolioName);
  }

  /**
   * Function that add all stocks to portfolio.
   *
   * @param portfolioName portfolio name
   * @param stocks        stocks added to the specific portfolio
   * @return list of stocks added to the portfolio
   */
  @Override
  public List<UserStock> addAllStocksToPortfolio(String portfolioName, List<UserStock> stocks) {
    return portfolioContainer.addAllStocksToPortfolio(portfolioName, stocks);
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
    portfolioContainer.dollarCostAveraging(portfolioName, startDateString, endDateString, stocks,
            periodicInterval, timeUnit);
  }
}
