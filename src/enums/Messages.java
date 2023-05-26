package enums;

/**
 * Enum for displaying messages.
 */
public enum Messages {

  WELCOME_MESSAGE("Welcome to Stock Application"),
  USER_STATUS("Are you a 1.'FIRST TIME USER'? OR 2. 'REGISTERED USER'? Enter your choice."),
  FILL_REGISTRATION_FORM("Please fill in your details and get yourself registered with us."),
  FIRST_NAME("Please enter your First Name"),
  LAST_NAME("Please enter your Last Name"),
  EMAIL_ID("Please enter your Email Id"),
  PHONE_NUMBER("Please enter your Phone Number"),
  ADDRESS("Please enter your Address"),
  PASSWORD("Please enter password"),
  LOGIN("Please login by entering your email id and password"),

  TICKER_MSG("Please enter the ticker symbol of the company in which you want to invest"),
  TICKER_MSG_SEll("Please enter the ticker symbol of the company from which you want to sell"),

  TICKER_MSG_INVALID("The input is not a valid ticker symbol. Please enter the valid ticker "
          + "symbol of the company in which you want to invest"),
  TICKER_MSG_INVALID_STOCK_SOLD("The input is not a valid ticker symbol. "
          + "Please enter the valid ticker "
          + "symbol of the company from which you want to sell stocks"),

  NUMBER_OF_SHARES("Please enter the number of shares you want to buy"),
  NUMBER_OF_SHARES_SOLD("Please enter the number of shares you want to sell"),
  NUMBER_OF_SHARES_INVALID("The input is not a valid number. Please enter a valid number of "
          + "shares you want to invest"),
  NUMBER_OF_SHARES_SOLD_INVALID("The input is not a valid number. Please enter a valid number of "
          + "shares you want to invest"),

  MAIN_MENU("Please select your choice from the menu below"),
  CANNOT_ADD_MORE_STOCKS("Stocks could not be added. Either the portfolio is inflexible or "
          + "closing price is not available"),
  CREATE_PORTFOLIO("1. Create flexible portfolio"),
  VIEW_ALL_PORTFOLIOS("2. View all portfolios"),
  COMPOSITION("3. Get composition of portfolio"),
  TOTAL_VALUE("4. Get total value of portfolio"),

  BUY_STOCK("5. Buy shares"),

  SELL_STOCK("6. Sell shares"),

  GET_PERFORMANCE_OF_PORTFOLIO("7. Get performance of a portfolio"),
  CREATE_INFLEXIBLE_PORTFOLIO("8. Create inflexible portfolio"),

  COST_BASIS("9. Get cost basis of portfolio by date"),

  ADD_STOCKS_WITH_WEIGHTS("10. Invest fixed amount in a portfolio"),

  DOLLAR_COST_AVERAGING("11. Create portfolio using dollar cost averaging"),

  EXIT("12. Exit MAIN MENU"),

  START_DATE_OF_PORTFOLIO("Enter the start date to view performance of portfolio"),
  END_DATE_OF_PORTFOLIO("Enter the end date to view performance of portfolio"),

  PORTFOLIO_DOES_NOT_EXIST("Portfolio does not exist."
          + " Please enter a portfolio name from the choice below"),

  PORTFOLIO_ALREADY_EXIST("The portfolio already exists. Please enter a "
          + "different name to portfolio"),

  ENTER_COMMISSION_FEE("Enter commission fee. Minimum value is 0"),

  DATE_OF_COST_BASIS("Enter date for which you want to get cost basis"),

  ENTER_NAME_OF_THE_PORTFOLIO("Please enter the name of the portfolio"),
  ENTER_PATH_FOR_PORTFOLIO("Enter pathname where you want to create a portfolio."),

  PORTFOLIO_SUCCESSFULLY_CREATED("Your portfolio is successfully created!"),
  CORRECT_PORTFOLIO_TOTAL_VALUE("Please choose the portfolio name correctly from "
          + "the choice below whose total value is required"),
  INCORRECT_PORTFOLIO_NAME("Please choose the portfolio name correctly "
          + "from the choice below to get the composition"),
  CORRECT_PORTFOLIO_NAME("Please choose the portfolio name correctly from the choice "
          + "below where stocks have to be added"),
  CORRECT_PORTFOLIO_NAME_TO_SELL_STOCKS("Please choose the portfolio name correctly from "
          + "the choice below where stocks have to be sold"),
  DONT_HAVE_ENOUGH_STOCKS("You don't have enough stocks to sell. Please enter a "
          + "valid number of stocks you want to sell after selecting OPTION 5 from the main menu"),

  ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES("Enter the date on which you want to buy"),
  ENTER_DATE_WHEN_YOU_WANT_TO_SELL_SHARES("Enter the date on which you want to sell. "
          + "Please enter date in YYYY-MM-DD format."),

  STOCKS_SUCCESSFULLY_BOUGHT("The stock is successfully bought"),
  STOCKS_SUCCESSFULLY_SOLD("The stock is successfully sold"),
  STOCKS_UNSUCCESSFULLY_BOUGHT_SINCE_NO_PORTFOLIO_EXIST("There is no portfolio with this name. "
          + "Please create a portfolio"),


  CURRENTLY_PROCESSING_FILE("Currently processing file "),

  ENTER_PORTFOLIO_NAME("Please enter the name of the portfolio from the choice below for "
          + "which you want to know composition"),
  SEE_MORE_COMPOSITION("You want to see composition of other portfolios? Please enter yes or no"),

  COST_BASIS_OF_PORTFOLIO("Cost basis of the portfolio is"),

  VIEW_MORE_VALID("Please enter either yes or no"),
  LIST_OF_PORTFOLIOS("List of portfolios are"),
  ENTER_NUMBER_OF_STOCKS("Enter number of companies in which you want to buy shares"),
  ENTER_MULTIPLE_STOCKS("Enter the name of the company followed by number of shares "
          + "you want to buy in that company"),


  COMPOSITION_IS_EMPTY("You hold no stocks at the moment. Please invest in a stock."),
  COMPOSITION_MSG("Composition of portfolio is as follows:"),

  NO_PORTFOLIOS("You don't have any portfolios. Please "
          + "create one by choosing the option 4 from the main menu below"),

  NO_PORTFOLIO_YET("There are no portfolios yet. "
          + "Please create one by choosing option 1 from the menu below"),
  NO_OF_STOCKS_YOU_WANT_TO_BUY("Enter the number of stocks you want to buy"),

  COMPOSITION_COMPANY_NAME("Name of the company in which you have stock is "),
  COMPOSITION_COMPANY_SHARE("Number of shares you have is "),

  REGISTERED_SUCCESSFULLY("Successfully Registered"),

  TOTAL_VALUE_OF_PORTFOLIO("The total value of portfolio is: "),
  ENTER_USER_FILE_PATH("Enter the filepath from where portfolio needs to be loaded or saved"),

  ALL_TICKER_SYMBOLS("You can invest in the following companies"),

  VALID_INPUT("Input is not valid. Please enter a valid number"),

  ENTER_DATE_TOTAL_VALUE("Please enter the date for which you want to see total value"),

  ENTER_DATE_CREATE_PORTFOLIO("Please enter the date on which you want to create portfolio"),
  VALID_DATE("Date entered is not in the correct format. Please enter date in YYYY-MM-DD format."),

  ADD_MULTIPLE_STOCKS("6. Add multiple stocks to portfolio"),

  NUMBER_OF_STOCKS_TO_BUY("Enter the number of stocks you want to buy"),


  UNABLE_TO_ADD_STOCKS_SINCE_CURRENTLY_NO_PORTFOLIOS("You cannot buy stocks since you don't have "
          + "any existing portfolios. Please create a portfolio first by selecting OPTION 1 from "
          + "the main menu below."),
  UNABLE_TO_DELETE_STOCKS_SINCE_CURRENTLY_NO_PORTFOLIOS("You cannot sell stocks since you don't "
          + "have any existing portfolios from which you can sell stocks. Please create a "
          + "portfolio first by selecting OPTION 1 from the main menu.");


  String message;

  /**
   * Constructor that initialises messages.
   *
   * @param message message
   */
  Messages(String message) {
    this.message = message;
  }

  /**
   * Function that returns all ticker symbols.
   *
   * @return all ticker symbols
   */
  public static Messages[] getAllTickerSymbols() {
    return values();
  }

  /**
   * Function that returns the message as a string.
   *
   * @return message as a string
   */
  public String getMessage() {
    return message;
  }
}
