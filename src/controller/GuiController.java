package controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import enums.TickerSymbol;
import model.IModel;
import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserStock;
import view.GuiView;

/**
 * Class that represents features that graphical user interface supports.
 */
public class GuiController implements IController, Features {
  GuiView view;

  IModel model;

  /**
   * The class that represents features that the graphical user interface supports.
   */

  public GuiController(GuiView view, IModel model) {
    this.view = view;
    view.addFeatures(this);
    this.model = model;
    TickerSymbol tickerSymbol = new TickerSymbol();
  }

  /**
   * Execute the GUI controller.
   *
   * @param model model
   */
  @Override
  public void execute(IModel model) {
    //

  }

  /**
   * Creates a portfolio with the specified arguments.
   *
   * @param portfolioName  Name of portfolio to be created
   * @param filePath       absolute path where the portfolio will be stored
   * @param dateOfCreation Date when portfolio needs to be created
   * @param isFlexible     Indicates if the portfolio is flexible or inflexible
   * @return The portfolio that was created
   */
  @Override
  public IUserPortfolio createPortfolio(String portfolioName, String filePath,
                                        String dateOfCreation, boolean isFlexible) {
    return model.createPortfolio(portfolioName, filePath, dateOfCreation, isFlexible);
  }

  /**
   * Get the names of all portfolios for a user.
   *
   * @return A set of portfolio names
   */
  @Override
  public Set<String> getAllPortfolios() {
    return model.getAllPortfolios();
  }


  /**
   * Returns the composition from the specific portfolio.
   *
   * @param portfolioName name of the portfolio
   * @param date          date till which composition is required
   * @return map consisting composition of the portfolio
   */
  @Override
  public Map<String, Long> compositionOfPortfolio(String portfolioName, String date) {
    Map<String, Long> compositionOfPortfolio = model.compositionOfPortfolio(portfolioName, date);
    view.showComposition(compositionOfPortfolio);
    return compositionOfPortfolio;
  }

  /**
   * Returns the total value of the specified portfolio.
   *
   * @param nameOfPortfolio name of the portfolio
   * @param date            The total value till the given date
   * @return total value of the portfolio
   */
  public double totalValueOfPortfolio(String nameOfPortfolio, String date) {
    double totalValue = model.totalValueOfPortfolio(nameOfPortfolio, date);

    view.showTotalValue(totalValue);
    return totalValue;
  }


  /**
   * Adds a stock to the specified portfolio.
   *
   * @param nameOfPortfolio Name of the portfolio where stock has to be added
   * @param userStock       The stock to be added
   */
  public void addStockToPortfolio(String nameOfPortfolio, UserStock userStock) {
    model.addStockToPortfolio(nameOfPortfolio, userStock);
  }

  /**
   * Decrements a stock's balance from the portfolio. This function is used to sell stocks.
   *
   * @param nameOfPortfolio Name of the portfolio from which stock will be sold
   * @param userStock       The stock that will be sold
   */
  public void deleteStockFromPortfolio(String nameOfPortfolio, UserStock userStock) {
    model.deleteStockFromPortfolio(nameOfPortfolio, userStock);
  }

  /**
   * The cost basis of the portfolio till the given date.
   *
   * @param portfolioName portfolio name
   * @param date          The cost basis till the given date
   */
  public void getCostBasisOfPortfolio(String portfolioName, String date) {
    double costBasis = model.getCostBasisOfPortfolio(portfolioName, date);
    view.showCostBasis(costBasis);
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
    model.dollarCostAveraging(portfolioName, startDateString, endDateString,
            stocks, periodicInterval, timeUnit);
  }

  @Override
  public void addAllStocksToPortfolio(String portfolioName, List<UserStock> stocks) {
    model.addAllStocksToPortfolio(portfolioName,stocks);
  }

  /**
   * Function to validate date.
   *
   * @param date date
   * @return true when the date is valid or false when the date is invalid
   */
  public boolean validateDate(String date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
            Locale.US);
    try {
      dateTimeFormatter.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Get all the ticker symbols supported by the api.
   *
   * @return set of all ticker symbols
   */
  public Set<String> getAllTickerSymbols() {
    return TickerSymbol.getAllTickerSymbols();
  }
}
