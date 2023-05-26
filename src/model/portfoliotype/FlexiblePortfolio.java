package model.portfoliotype;

import java.nio.file.Path;

import model.userportfolio.UserStock;
import utils.FileUtils;

/**
 * a class that represents a flexible portfolio.
 */
public class FlexiblePortfolio extends PortfolioType {
  /**
   * Constructor that initialises UserPortfolio Object.
   *
   * @param pathName        pathname
   * @param nameOfPortfolio name of the portfolio
   * @param dateOfCreation  date of creation
   * @param isFlexible      isFlexible that specify whether the portfolio is flexible or inflexible
   */
  public FlexiblePortfolio(String pathName, String nameOfPortfolio, String dateOfCreation,
                           Boolean isFlexible) {
    super(pathName, nameOfPortfolio, dateOfCreation, isFlexible);
  }

  /**
   * Adds a stock to the portfolio.
   *
   * @param userStock A user stock object that contains information about stock that a user holds
   * @return stock that was added
   */
  @Override
  public UserStock addUserPortfolio(UserStock userStock) {
    UserStock userStockAdded = super.addUserPortfolio(userStock);
    StringBuffer sb = new StringBuffer();
    if (!portfoliosInFile.contains(this.getNameOfPortfolio())) {
      portfoliosInFile.add(this.getNameOfPortfolio());
      sb.append(this.getNameOfPortfolio()) // 0
              .append("~")
              .append(this.getFilePathOfPortfolio()) // 1
              .append("~")
              .append(this.getDateOfCreationOfPortfolio()) // 2
              .append("~")
              .append(true) // 3
              .append("\n");
      FileUtils.appendContentToFile(Path.of(PORTFOLIO_FILE_PATH), sb.toString());
    }
    return userStockAdded;
  }

  /**
   * Check if the portfolio is flexible or not.
   *
   * @return true if the portfolio is flexible otherwise returns false
   */
  @Override
  public boolean isFlexible() {
    return true;
  }
}
