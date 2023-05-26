package model.portfoliotype;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import model.userportfolio.UserPortfolio;
import model.userportfolio.UserStock;
import utils.FileUtils;

/**
 * An abstract class that represents a portfolio.
 */
public abstract class PortfolioType extends UserPortfolio {

  protected static String PORTFOLIO_FILE_PATH = "resources/allportfolios.txt";
  protected Set<String> portfoliosInFile;

  /**
   * Constructor that initialises UserPortfolio Object.
   *
   * @param pathName        pathname
   * @param nameOfPortfolio name of the portfolio
   * @param dateOfCreation  date of creation
   * @param isFlexible      isFlexible that specify whether the portfolio is flexible or inflexible
   */
  public PortfolioType(String pathName, String nameOfPortfolio, String dateOfCreation,
                       Boolean isFlexible) {
    super(pathName, nameOfPortfolio, dateOfCreation, isFlexible);
    portfoliosInFile = new LinkedHashSet<>();
  }

  /**
   * adds a list of stocks to the portfolio.
   *
   * @param stocks stocks to be added to the portfolio
   * @return a list of stocks that was addded
   */
  @Override
  public List<UserStock> addAllStocksToPortfolio(List<UserStock> stocks) {
    if (!portfoliosInFile.contains(this.getNameOfPortfolio()) || this.isFlexible()) {
      for (UserStock u : stocks) {
        addUserPortfolio(u);
      }
      StringBuffer sb = new StringBuffer();
      if (!portfoliosInFile.contains(this.getNameOfPortfolio())) {
        portfoliosInFile.add(this.getNameOfPortfolio());
        sb.append(this.getNameOfPortfolio()) // 0
                .append("~")
                .append(this.getFilePathOfPortfolio()) // 1
                .append("~")
                .append(this.getDateOfCreationOfPortfolio()) // 2
                .append("~")
                .append(false) // 3
                .append("\n");
        FileUtils.appendContentToFile(Path.of(PORTFOLIO_FILE_PATH), sb.toString());
      }
    }
    return stocks;
  }
}
