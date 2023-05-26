package controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import enums.Messages;
import enums.TickerSymbol;
import model.IModel;
import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserStock;
import view.IView;

/**
 * The class represents controller. The controller takes inputs from the user through text
 * based interface and tells the model what to do and the view what to show.
 */
public class Controller implements IController {
  private final IView view;
  private final Scanner sc;

  /**
   * Constructor that initialises readable and view.
   *
   * @param readable readable
   * @param view     view
   */
  public Controller(Readable readable, IView view) {
    this.view = view;
    this.sc = new Scanner(readable);
    TickerSymbol tickerSymbol = new TickerSymbol();
  }


  /**
   * Function that triggers the controller to take input from the user through text based interface
   * and based on the input received delegate the work to view and model.
   *
   * @param model model of this application which consists of user portfolio.
   */
  @Override
  public void execute(IModel model) {

    welcomeMessage();

    portfolioMainMenuForm();
    boolean isInputValid = false;
    int option = getValidOption(isInputValid);

    boolean exit = false;
    while (!exit) {
      switch (option) {
        case 1: {
          boolean isFlexible = true;
          view.showString(Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage());
          sc.nextLine();
          boolean isPortfolioNameAlreadyPresent = false;
          while (!isPortfolioNameAlreadyPresent) {
            String portfolioName = sc.nextLine();
            view.showString(Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage());
            String portfolioPath = sc.nextLine();
            boolean isDateValid = false;
            String date = "";
            view.showString(Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage());
            while (!isDateValid) {
              date = sc.nextLine();
              if (!validateDate(date)) {
                view.showString(Messages.VALID_DATE.getMessage());
              } else {
                isDateValid = true;
              }
            }
            IUserPortfolio createdPortfolio = model.createPortfolio(portfolioName,
                    portfolioPath, date, isFlexible);
            if (createdPortfolio == null) {
              view.showString(Messages.PORTFOLIO_ALREADY_EXIST.getMessage());
            } else {
              isPortfolioNameAlreadyPresent = true;
              view.showString(Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage());
            }
          }

          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 2: {
          Set<String> allPortfolios = model.getAllPortfolios();
          if (allPortfolios == null || allPortfolios.isEmpty()) {
            view.showString(Messages.NO_PORTFOLIOS.getMessage());
          } else {
            view.showString(Messages.LIST_OF_PORTFOLIOS.getMessage());
            displayAllPortfolioNames(allPortfolios);
          }
          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 3: {
          boolean backToMainMenu = false;
          int count = 0;
          Set<String> allPortfolios = model.getAllPortfolios();
          if (allPortfolios.isEmpty()) {
            view.showString(Messages.NO_PORTFOLIO_YET.getMessage());
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }
          while (!backToMainMenu) {
            view.showString(Messages.ENTER_PORTFOLIO_NAME.getMessage());
            if (count == 0) {
              sc.nextLine();
            }
            displayAllPortfolioNames(allPortfolios);

            boolean isPortfolioNameValid = false;
            String portfolioName = "";
            while (!isPortfolioNameValid) {
              portfolioName = sc.nextLine();
              if (allPortfolios.contains(portfolioName)) {
                isPortfolioNameValid = true;
                continue;
              }
              view.showString(Messages.CORRECT_PORTFOLIO_NAME.getMessage());
              displayAllPortfolioNames(allPortfolios);
            }
            view.showString("Enter date till which you want to know composition. Date format "
                    + "should be YYYY-MM-DD format.");
            boolean isDateValid = false;
            String date = "";
            while (!isDateValid) {
              date = sc.nextLine();
              if (!validateDate(date)) {
                view.showString(Messages.VALID_DATE.getMessage());
              } else {
                isDateValid = true;
              }
            }
            Map<String, Long> compositionMap = model.compositionOfPortfolio(portfolioName, date);

            if (compositionMap == null || compositionMap.isEmpty()) {
              view.showString(Messages.COMPOSITION_IS_EMPTY.getMessage());
              break;

            } else {
              view.showString(Messages.COMPOSITION_MSG.getMessage());
              for (Map.Entry<String, Long> e : compositionMap.entrySet()) {
                view.showString(Messages.COMPOSITION_COMPANY_NAME.getMessage() + e.getKey());
                view.showString(Messages.COMPOSITION_COMPANY_SHARE.getMessage() + e.getValue());
              }
            }

            view.showString(Messages.SEE_MORE_COMPOSITION.getMessage());
            String viewMore = "";
            boolean isViewMoreValid = false;
            while (!isViewMoreValid) {
              viewMore = sc.nextLine();
              if (viewMore.equalsIgnoreCase("no")) {
                backToMainMenu = true;
                isViewMoreValid = true;
              } else if (viewMore.equalsIgnoreCase("yes")) {
                isViewMoreValid = true;
                count += 1;
              }
              if (!isViewMoreValid) {
                view.showString(Messages.VIEW_MORE_VALID.getMessage());
              }
            }
          }
          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;

        }
        case 4: {
          boolean isPortfolioNameValid = false;
          String portfolioName = "";
          Set<String> allPortfolios = model.getAllPortfolios();
          if (allPortfolios.isEmpty()) {
            view.showString(Messages.NO_PORTFOLIO_YET.getMessage());
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }
          while (!isPortfolioNameValid) {
            portfolioName = sc.nextLine();
            if (allPortfolios.contains(portfolioName)) {
              isPortfolioNameValid = true;
              continue;
            }
            view.showString(Messages.CORRECT_PORTFOLIO_TOTAL_VALUE.getMessage());
            displayAllPortfolioNames(allPortfolios);
          }
          view.showString(Messages.ENTER_DATE_TOTAL_VALUE.getMessage());
          boolean isDateValid = false;
          String date = "";
          while (!isDateValid) {
            date = sc.nextLine();
            if (!validateDate(date)) {
              view.showString(Messages.VALID_DATE.getMessage());
            } else {
              isDateValid = true;
            }
          }
          double totalValue = model.totalValueOfPortfolio(portfolioName, date);
          view.showString(Messages.TOTAL_VALUE_OF_PORTFOLIO.getMessage()
                  + (totalValue == Double.MIN_VALUE ? 0 : totalValue));

          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 5: {
          if (model.getAllPortfolios().isEmpty()) {
            view.showString(Messages
                    .UNABLE_TO_ADD_STOCKS_SINCE_CURRENTLY_NO_PORTFOLIOS.getMessage());
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }


          view.showString(Messages.CORRECT_PORTFOLIO_NAME.getMessage());
          displayAllPortfolioNames(model.getAllPortfolios());
          sc.nextLine();
          String portfolioName = sc.nextLine();


          while (!model.getAllPortfolios().contains(portfolioName)) {
            view.showString(Messages.CORRECT_PORTFOLIO_NAME.getMessage());
            displayAllPortfolioNames(model.getAllPortfolios());
            portfolioName = sc.nextLine();
          }


          IUserPortfolio portfolio = model.getPortfolioByName(portfolioName);
          if (portfolio.isFlexible()) {
            UserStock userStock = portfolioForm();
            UserStock userStock1 = model.addStockToPortfolio(portfolioName, userStock);
            if (userStock1 == null) {
              view.showString(Messages.CANNOT_ADD_MORE_STOCKS.getMessage());
            } else {
              view.showString(Messages.STOCKS_SUCCESSFULLY_BOUGHT.getMessage());
            }
          } else {
            if (portfolio.getAllStocksInAPortfolio().isEmpty()) {
              List<UserStock> stocks = new ArrayList<>();

              view.showString(Messages.NO_OF_STOCKS_YOU_WANT_TO_BUY.getMessage());

              int numberOfStocks = sc.nextInt();
              sc.nextLine();

              while (numberOfStocks != 0) {
                UserStock stock = portfolioForm();
                stocks.add(stock);
                sc.nextLine();
                numberOfStocks--;
              }
              model.addAllStocksToPortfolio(portfolioName, stocks);
            } else {
              view.showString("Portfolio is inflexible. You cannot buy or sell stocks "
                      + "from this portfolio");
            }
          }

          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 6: {
          if (model.getAllPortfolios().isEmpty()) {
            view.showString(Messages
                    .UNABLE_TO_DELETE_STOCKS_SINCE_CURRENTLY_NO_PORTFOLIOS.getMessage());
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }
          view.showString(Messages.CORRECT_PORTFOLIO_NAME_TO_SELL_STOCKS.getMessage());
          displayAllPortfolioNames(model.getAllPortfolios());
          sc.nextLine();
          String portfolioName = sc.nextLine();
          while (!model.getAllPortfolios().contains(portfolioName)) {
            view.showString(Messages.CORRECT_PORTFOLIO_NAME_TO_SELL_STOCKS.getMessage());
            displayAllPortfolioNames(model.getAllPortfolios());
            portfolioName = sc.nextLine();
          }
          if (!model.getPortfolioByName(portfolioName).isFlexible()) {
            view.showString("This portfolio is inflexible. You cannot sell stocks.");
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }
          if (model.getPortfolioByName(portfolioName).getAllStocksInAPortfolio().isEmpty()) {
            view.showString("You do not have any stocks in the portfolio. Please go and add "
                    + "stocks by choosing option 5");
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }
          UserStock userStock = portfolioEntryForm(model, portfolioName);
          if (!userStock.getTickerSymbol().equals(TickerSymbol.DEFAULT)) {
            UserStock userStockSold = model.deleteStockFromPortfolio(portfolioName, userStock);
            if (userStockSold == null) {
              view.showString(Messages.DONT_HAVE_ENOUGH_STOCKS.getMessage());
              portfolioMainMenuForm();
              option = getValidOption(isInputValid);
              break;
            }
            view.showString(Messages.STOCKS_SUCCESSFULLY_SOLD.getMessage());
          }
          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }

        case 7: {
          Set<String> allPortfolios = model.getAllPortfolios();
          if (allPortfolios.isEmpty()) {
            view.showString("Portfolios don't exist. Please go and create one");
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }

          view.showString(Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage());
          sc.nextLine();

          displayAllPortfolioNames(allPortfolios);
          String portfolioName = sc.nextLine();
          while (!allPortfolios.contains(portfolioName)) {
            view.showString(Messages.PORTFOLIO_DOES_NOT_EXIST.getMessage());
            displayAllPortfolioNames(allPortfolios);
            portfolioName = sc.nextLine();
          }
          view.showString(Messages.START_DATE_OF_PORTFOLIO.getMessage());
          sc.nextLine();
          String startDate = sc.nextLine();
          view.showString(Messages.END_DATE_OF_PORTFOLIO.getMessage());
          sc.nextLine();
          String endDate = sc.nextLine();
          model.getPerformanceOfPortfolio(startDate, endDate, portfolioName);

          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 8: {

          boolean isFlexible = false;
          view.showString(Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage());
          sc.nextLine();
          boolean isPortfolioNameAlreadyPresent = false;
          while (!isPortfolioNameAlreadyPresent) {
            String portfolioName = sc.nextLine();
            view.showString(Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage());
            String portfolioPath = sc.nextLine();
            boolean isDateValid = false;
            String date = "";
            view.showString(Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage());
            while (!isDateValid) {
              date = sc.nextLine();
              if (!validateDate(date)) {
                view.showString(Messages.VALID_DATE.getMessage());
              } else {
                isDateValid = true;
              }
            }
            IUserPortfolio createdPortfolio = model.createPortfolio(portfolioName,
                    portfolioPath, date, isFlexible);
            if (createdPortfolio == null) {
              view.showString(Messages.PORTFOLIO_ALREADY_EXIST.getMessage());
            } else {
              isPortfolioNameAlreadyPresent = true;
              view.showString(Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage());
            }
          }
          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 9: {
          Set<String> allPortfolios = model.getAllPortfolios();
          if (allPortfolios.isEmpty()) {
            view.showString("Portfolios don't exist. Please go and create one");
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }

          view.showString(Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage());
          sc.nextLine();

          displayAllPortfolioNames(allPortfolios);
          String portfolioName = sc.nextLine();
          while (!allPortfolios.contains(portfolioName)) {
            view.showString(Messages.PORTFOLIO_DOES_NOT_EXIST.getMessage());
            displayAllPortfolioNames(allPortfolios);
            portfolioName = sc.nextLine();
          }
          String date = "";
          view.showString(Messages.DATE_OF_COST_BASIS.getMessage());
          boolean isDateValid = false;
          while (!isDateValid) {
            date = sc.nextLine();
            if (!validateDate(date)) {
              view.showString(Messages.VALID_DATE.getMessage());
            } else {
              isDateValid = true;
            }
          }

          view.showString(Messages.COST_BASIS_OF_PORTFOLIO.getMessage()
                  + model.getCostBasisOfPortfolio(portfolioName, date));

          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 10: {

          if (model.getAllPortfolios().isEmpty()) {
            view.showString(Messages
                    .UNABLE_TO_ADD_STOCKS_SINCE_CURRENTLY_NO_PORTFOLIOS.getMessage());
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }

          view.showString(Messages.CORRECT_PORTFOLIO_NAME.getMessage());
          displayAllPortfolioNames(model.getAllPortfolios());
          sc.nextLine();
          String portfolioName = sc.nextLine();

          while (!model.getAllPortfolios().contains(portfolioName)) {
            view.showString(Messages.CORRECT_PORTFOLIO_NAME.getMessage());
            displayAllPortfolioNames(model.getAllPortfolios());
            portfolioName = sc.nextLine();
          }
          if (!model.getPortfolioByName(portfolioName).getAllStocksInAPortfolio().isEmpty()
                  && !model.getPortfolioByName(portfolioName).isFlexible()) {
            view.showString("Portfolio is inflexible. You cannot buy or sell "
                    + "stocks from this portfolio");
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }
          view.showString(Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage());
          boolean isDateValid = false;
          String date = "";
          while (!isDateValid) {
            date = sc.next();
            if (!validateDate(date)) {
              view.showString(Messages.VALID_DATE.getMessage());
            } else {
              isDateValid = true;
            }
          }
          List<UserStock> userStocks = buyStocksUsingWeights(date);
          List<UserStock> userStocksAdded = model
                  .addAllStocksToPortfolio(portfolioName, userStocks);
          if (userStocksAdded == null) {
            view.showString(Messages.CANNOT_ADD_MORE_STOCKS.getMessage());
          } else {
            view.showString(Messages.STOCKS_SUCCESSFULLY_BOUGHT.getMessage());
          }
          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 11: {
          boolean isFlexible = true;
          view.showString(Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage());
          sc.nextLine();
          boolean isPortfolioNameAlreadyPresent = false;
          String portfolioName = "";
          while (!isPortfolioNameAlreadyPresent) {
            portfolioName = sc.nextLine();
            view.showString(Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage());
            String portfolioPath = sc.nextLine();
            boolean isDateValid = false;
            String date = "";
            view.showString(Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage());
            while (!isDateValid) {
              date = sc.nextLine();
              if (!validateDate(date)) {
                view.showString(Messages.VALID_DATE.getMessage());
              } else {
                isDateValid = true;
              }
            }
            IUserPortfolio createdPortfolio = model.createPortfolio(portfolioName,
                    portfolioPath, date, isFlexible);
            if (createdPortfolio == null) {
              view.showString(Messages.PORTFOLIO_ALREADY_EXIST.getMessage());
            } else {
              isPortfolioNameAlreadyPresent = true;
              view.showString(Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage());
            }
          }

          boolean isDateValid = false;
          String startDate = "";
          view.showString("Enter the start date of dollar cost averaging strategy");
          while (!isDateValid) {
            startDate = sc.nextLine();
            if (!validateDate(startDate)) {
              view.showString(Messages.VALID_DATE.getMessage());
            } else {
              isDateValid = true;
            }
          }
          view.showString("Do you want to have end date for strategy? Enter yes or no");
          String chooseYesOrNo = sc.next();
          String endDate = "";
          if (chooseYesOrNo.equalsIgnoreCase("yes")) {

            isDateValid = false;
            view.showString("Enter the end date of dollar cost averaging strategy");
            while (!isDateValid) {
              endDate = sc.next();
              if (!validateDate(endDate)) {
                view.showString(Messages.VALID_DATE.getMessage());
              } else {
                isDateValid = true;
              }
            }
          }


          if (model.getAllPortfolios().isEmpty()) {
            view.showString(Messages.UNABLE_TO_ADD_STOCKS_SINCE_CURRENTLY_NO_PORTFOLIOS
                    .getMessage());
            portfolioMainMenuForm();
            option = getValidOption(isInputValid);
            break;
          }


          List<UserStock> userStocks = buyStocksUsingWeights(startDate);
          view.showString("Enter the periodic interval as a number after which the "
                  + "investment should recur");
          long periodicInterval = sc.nextLong();
          view.showString("Choose the time unit from the options below");
          TimeUnit[] timeUnits = TimeUnit.values();
          for (int i = 3; i < timeUnits.length; i++) {
            view.showString(timeUnits[i].name());
          }
          String timeUnit = sc.next();


          model.dollarCostAveraging(portfolioName, startDate, endDate, userStocks,
                  periodicInterval, timeUnit);
          portfolioMainMenuForm();
          option = getValidOption(isInputValid);
          break;
        }
        case 12: {
          exit = true;
          System.exit(0);
          break;
        }
        default: {
          view.showString(Messages.VALID_INPUT.getMessage());
          option = getValidOption(isInputValid);
          break;
        }

      }
    }
  }

  /**
   * Function to validate date.
   *
   * @param date date
   * @return true when the date is valid or false when the date is invalid
   */
  private boolean validateDate(String date) {
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
   * Helper function that displays all the names of the portfolio that are created.
   *
   * @param set set of portfolio names
   */
  private void displayAllPortfolioNames(Set<String> set) {
    for (String portfolioName : set) {
      view.showString(portfolioName);
    }
  }

  /**
   * Helper to check if the option choosen from the menu is valid or not.
   *
   * @param isInputValid flag that is set to trueif the input is valid otherwise is set to false
   * @return The input that was entered as an integer
   */
  private int getValidOption(boolean isInputValid) {
    int option = 0;
    while (!isInputValid) {
      try {
        option = sc.nextInt();
        isInputValid = true;
      } catch (Exception e) {
        view.showString(Messages.VALID_INPUT.getMessage());
        sc.nextLine();
      }
    }
    return option;
  }

  /**
   * Helper function that takes a valid date as input displays several ticker
   * symbols and takes input of a valid ticker
   * symbol and no. of shares in which the user want to invest. The function also
   * delegate view to show invalid message if the input entered by the user is invalid
   * and asks the user to enter valid input.
   *
   * @return UserStock object
   */
  private UserStock portfolioForm() {

    view.showString(Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage());
    boolean isDateValid = false;
    String date = "";
    while (!isDateValid) {
      date = sc.nextLine();
      if (!validateDate(date)) {
        view.showString(Messages.VALID_DATE.getMessage());
      } else {
        isDateValid = true;
      }
    }
    view.showString(Messages.TICKER_MSG.getMessage());
    displayTickerSymbols();
    String tickerSymbol = getValidTickerSymbol();
    boolean isSharesValid = false;
    long numberOfStocksBought = 0;
    view.showString(Messages.NUMBER_OF_SHARES.getMessage());
    while (!isSharesValid) {

      try {
        numberOfStocksBought = sc.nextLong();
        if (numberOfStocksBought >= 0) {
          isSharesValid = true;
        } else {
          view.showString(Messages.NUMBER_OF_SHARES_INVALID.getMessage());
        }
      } catch (Exception e) {
        sc.nextLine();
        view.showString(Messages.NUMBER_OF_SHARES_INVALID.getMessage());
      }
    }
    boolean isCommisionFeeValid = false;
    float commissionFee = 0;
    while (!isCommisionFeeValid) {
      view.showString(Messages.ENTER_COMMISSION_FEE.getMessage());
      sc.nextLine();
      commissionFee = sc.nextFloat();
      if (commissionFee >= 0) {
        isCommisionFeeValid = true;
      }
    }

    UserStock userStock = UserStock.getUserStockBuilder()
            .date(date)
            .tickerSymbol(tickerSymbol)
            .stockBought(numberOfStocksBought)
            .commissionFeesForBuying(commissionFee)
            .build();

    return userStock;
  }

  /**
   * Get a valid ticker sysmbol from the user.
   *
   * @return valid ticker symbol as a string
   */
  private String getValidTickerSymbol() {
    String tickerSymbolString = sc.next();
    boolean isTickerValid = false;
    String tickerSymbol = TickerSymbol.DEFAULT;
    while (!isTickerValid) {
      tickerSymbol = tickerSymbolString.toUpperCase(Locale.US);
      if (TickerSymbol.getAllTickerSymbols().contains(tickerSymbol)) {
        isTickerValid = true;
      } else {
        view.showString(Messages.TICKER_MSG_INVALID.getMessage());
        displayTickerSymbols();
        tickerSymbolString = sc.nextLine();
      }
    }
    return tickerSymbol;
  }

  /**
   * Helper method to take weights from the user in terms of percentage to invest
   * a fixed amount in the portfolio.
   *
   * @param date date on which the user wants to invest shares
   * @return List of user stock
   */
  private List<UserStock> buyStocksUsingWeights(String date) {
    view.showString("Enter the fixed amount that you want to invest");
    double fixedAmount = sc.nextDouble();
    List<UserStock> list = new ArrayList<>();
    displayTickerSymbols();
    view.showString("Enter the number of companies from the list in which you want to invest");
    int numberOfCompanies = getValidOption(false);
    double percentage = 0;
    double remainingPercentage = 100;
    while (numberOfCompanies != 0) {
      view.showString("Enter company name in which you want to invest");
      String tickerSymbolString = getValidTickerSymbol();

      boolean isPercentageValid = false;

      while (!isPercentageValid || remainingPercentage < 0) {

        try {
          view.showString("Enter the percentage of fixed amount that you want to invest the "
                  + "above company. You can choose any percentage less than or equal to "
                  + remainingPercentage);
          percentage = sc.nextDouble();
          if (percentage >= 0 && percentage <= 100 && percentage <= remainingPercentage) {
            remainingPercentage = remainingPercentage - percentage;
            isPercentageValid = true;
          } else {
            view.showString("Percentage entered is not below the remaining percentage or "
                    + "a positive number");
          }
        } catch (Exception e) {
          sc.nextLine();
          view.showString("Percentage entered is not a number");
        }
      }
      UserStock userStock = UserStock.getUserStockBuilder()
              .date(date)
              .tickerSymbol(tickerSymbolString)
              .stockBought((long) ((percentage * fixedAmount) / 100))
              .build();
      list.add(userStock);
      numberOfCompanies--;
    }
    return list;
  }


  /**
   * Helper method that delegates view to display ticker symbols.
   */
  private void displayTickerSymbols() {
    Set<String> tickerSymbolSet = TickerSymbol.getAllTickerSymbols();
    view.showString("Number of companies in this application is " + tickerSymbolSet.size());
    String viewMore = "yes";
    while (viewMore.equalsIgnoreCase("yes")) {

      boolean isNumberOfCompaniesValid = false;
      int companiesPerPage = 0;
      while (!isNumberOfCompaniesValid) {
        view.showString("How many companies you want to see at a time for investing?");
        try {
          companiesPerPage = sc.nextInt();
          if (companiesPerPage < 0) {
            throw new RuntimeException();
          }
          isNumberOfCompaniesValid = true;
        } catch (Exception e) {
          view.showString("Input is not a valid number. Please enter a positive whole number");
        }
      }
      Iterator<String> iterator = tickerSymbolSet.iterator();
      while (iterator.hasNext() && companiesPerPage > 0) {
        view.showString("o" + " " + iterator.next());
        companiesPerPage--;
      }
      view.showString("Do you want to see more companies? Enter yes or no");

      boolean isViewMoreValid = false;
      while (!isViewMoreValid) {
        viewMore = sc.next();
        if (viewMore.equalsIgnoreCase("no")) {

          isViewMoreValid = true;
        } else if (viewMore.equalsIgnoreCase("yes")) {
          isViewMoreValid = true;

        }
        if (!isViewMoreValid) {
          view.showString(Messages.VIEW_MORE_VALID.getMessage());
        }
      }
    }
  }


  /**
   * Helper method that delegates view to display portfolio menu form.
   */
  private void portfolioMainMenuForm() {

    view.showString(Messages.MAIN_MENU.getMessage());
    view.showString(Messages.CREATE_PORTFOLIO.getMessage());
    view.showString(Messages.VIEW_ALL_PORTFOLIOS.getMessage());
    view.showString(Messages.COMPOSITION.getMessage());
    view.showString(Messages.TOTAL_VALUE.getMessage());
    view.showString(Messages.BUY_STOCK.getMessage());
    view.showString(Messages.SELL_STOCK.getMessage());
    view.showString(Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage());
    view.showString(Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage());
    view.showString(Messages.COST_BASIS.getMessage());
    view.showString(Messages.ADD_STOCKS_WITH_WEIGHTS.getMessage());
    view.showString(Messages.DOLLAR_COST_AVERAGING.getMessage());
    view.showString(Messages.EXIT.getMessage());

  }

  /**
   * Helper function that delegates view to display welcome message.
   */
  private void welcomeMessage() {
    view.showString(Messages.WELCOME_MESSAGE.getMessage());
    try {
      Thread.sleep(400);
    } catch (InterruptedException e) {
      //e.printStackTrace();
    }
  }


  /**
   * Helper function that takes a valid date as input displays
   * several ticker symbols and takes input of a valid ticker
   * symbol and no. of shares the user want to sell. The function also
   * delegate view to show invalid message if the input entered by the user is invalid
   * and asks the user to enter valid input.
   *
   * @param model         model
   * @param portfolioName portfolioname
   * @return UserStock object
   */
  private UserStock portfolioEntryForm(IModel model, String portfolioName) {
    view.showString(Messages.ENTER_DATE_WHEN_YOU_WANT_TO_SELL_SHARES.getMessage());

    boolean isDateValid = false;
    String date = "";
    while (!isDateValid) {
      date = sc.nextLine();
      if (!validateDate(date)) {
        view.showString(Messages.VALID_DATE.getMessage());
      } else {
        isDateValid = true;
      }
    }
    view.showString(Messages.TICKER_MSG_SEll.getMessage());
    Map<String, Long> composition = model.compositionOfPortfolio(portfolioName, date);
    Set<Map.Entry<String, Long>> entries = composition.entrySet();
    for (Map.Entry<String, Long> entry : entries) {
      view.showString(entry.getKey() + " - " + entry.getValue() + " shares left");
    }
    String tickerSymbolString = getValidTickerSymbol();
    boolean isSharesValid = false;
    long numberOfStocksSold = 0;
    view.showString(Messages.NUMBER_OF_SHARES_SOLD.getMessage());
    while (!isSharesValid) {

      try {
        numberOfStocksSold = sc.nextLong();
        if (numberOfStocksSold >= 0 && numberOfStocksSold <= composition
                .get(tickerSymbolString.toUpperCase(Locale.US))) {
          isSharesValid = true;
        } else {
          view.showString(Messages.NUMBER_OF_SHARES_SOLD_INVALID.getMessage());
        }

      } catch (Exception e) {
        sc.nextLine();
        view.showString(Messages.NUMBER_OF_SHARES_SOLD_INVALID.getMessage());
      }
    }
    boolean isCommisionFeeValid = false;
    float commissionFee = 0;
    while (!isCommisionFeeValid) {
      view.showString(Messages.ENTER_COMMISSION_FEE.getMessage());
      sc.nextLine();
      commissionFee = sc.nextFloat();
      if (commissionFee >= 0) {
        isCommisionFeeValid = true;
      }
    }
    UserStock userStock = UserStock.getUserStockBuilder().date(date)
            .tickerSymbol(tickerSymbolString)
            .stocksSold(numberOfStocksSold)
            .commissionFeesForSelling(commissionFee)
            .build();

    return userStock;
  }
}
