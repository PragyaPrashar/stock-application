package model.portfoliotype;

import model.userportfolio.UserStock;

/**
 * A class that represents a inflexible portfolio.
 */
public class InflexiblePortfolio extends PortfolioType {
  /**
   * Constructor that initialises UserPortfolio Object.
   *
   * @param pathName        pathname
   * @param nameOfPortfolio name of the portfolio
   * @param dateOfCreation  date of creation
   * @param isFlexible      isFlexible that specify whether the portfolio is flexible or inflexible
   */
  public InflexiblePortfolio(String pathName, String nameOfPortfolio, String dateOfCreation,
                             Boolean isFlexible) {
    super(pathName, nameOfPortfolio, dateOfCreation, isFlexible);

  }

  /**
   * sells a stock from this portfolio.
   *
   * @param userStock stock to be sold
   * @return stock that was sold
   */
  @Override
  public UserStock deleteUserPortfolio(UserStock userStock) {
    return null;
  }

  /**
   * adds a stock to this portfolio.
   *
   * @param userStock A user stock object that contains information about stock that a user holds
   * @return the stock that was added
   */
  @Override
  public UserStock addUserPortfolio(UserStock userStock) {
    return super.addUserPortfolio(userStock);

  }

  /**
   * check if the portfolio is flexible or not.
   *
   * @return true if the portfolio is flexible otherwise returns false
   */
  @Override
  public boolean isFlexible() {
    return false;
  }
}
