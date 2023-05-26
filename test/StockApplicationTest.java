import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.Controller;
import controller.IController;
import enums.Messages;
import enums.TickerSymbol;
import model.IModel;
import model.MockModel;
import model.Model;
import model.userportfolio.IUserPortfolio;
import model.userportfolio.UserPortfolio;
import model.userportfolio.UserStock;
import view.IView;
import view.MockView;
import view.View;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Test class to test the stock application.
 */
public class StockApplicationTest {

  private static String FILE_PATH = "test/resources/test.txt";
  private static final String AAPL = "AAPL";
  private static final String GOOGL = "GOOGL";
  private static final String META = "META";
  private static final String ORCL = "ORCL";

  /**
   * Helper Function to delete the test file.
   */
  private static void deleteTestFiles() {
    try {
      Files.deleteIfExists(Path.of(FILE_PATH));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      Files.deleteIfExists(Path.of("resources/allportfolios.txt"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Init function for each test case.
   */
  @Before
  public void setUp() {
    deleteTestFiles();
  }

  /**
   * Test model for flexible portfolio creation.
   */
  @Test
  public void testModelWhenFlexiblePortfolioIsCreated() {

    IModel model = new Model();

    IUserPortfolio expectedFlexiblePortfolio = new UserPortfolio(FILE_PATH,
            "test", "2022-11-15", true);
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            FILE_PATH, "2022-11-15", true);
    assertEquals(expectedFlexiblePortfolio, actualFlexiblePortfolio);
  }

  /**
   * Test model for view all flexible portfolio.
   */
  @Test
  public void testModelViewAllPortfoliosForAFlexiblePortfolio() {
    IModel model = new Model();
    Set<String> expectedPortfolioNames = new HashSet<>();
    expectedPortfolioNames.add("test");

    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            FILE_PATH, "2022-11-15", true);
    Set<String> actualPortfolio = model.getAllPortfolios();
    assertEquals(expectedPortfolioNames, actualPortfolio);
  }

  /**
   * Test model for get composition of a flexible portfolio.
   */
  @Test
  public void testModelGetCompositionOfAFlexiblePortfolio() {


    IModel model = new Model();

    Map<String, Long> expectedComposition = new HashMap<>();
    expectedComposition.put(AAPL, 3000L);
    expectedComposition.put(GOOGL, 1000L);

    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            FILE_PATH, "2022-10-27", true);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-27").stockBought(1000).build();
    model.addStockToPortfolio("test", userStock1);
    model.addStockToPortfolio("test", userStock2);

    model.addStockToPortfolio("test", userStock3);
    Map<String, Long> actualComposition = model.compositionOfPortfolio("test","2022-11-30");


    assertEquals(expectedComposition, actualComposition);
  }

  /**
   * Test model for total value of a flexible portfolio.
   */
  @Test
  public void testModelTotalValueOfAFlexiblePortfolio() {

    IModel model = new Model();
    double expectedTotalValue = 540040.0;
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test", FILE_PATH,
            "2022-10-27", true);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-27").stockBought(1000).build();
    model.addStockToPortfolio("test", userStock1);
    model.addStockToPortfolio("test", userStock2);
    model.addStockToPortfolio("test", userStock3);
    double actualTotalValue = model.totalValueOfPortfolio("test", "2022-10-27");
    assertEquals(expectedTotalValue, actualTotalValue, 0.01);
  }

  /**
   * Test model for buying shares in a flexible portfolio.
   */
  @Test
  public void testModelBuySharesInAFlexiblePortfolio() {

    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test", FILE_PATH,
            "2022-10-27", true);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock actualBoughtShares = model.addStockToPortfolio("test", userStock1);
    UserStock expectedBoughtShares = userStock1;
    assertEquals(expectedBoughtShares, actualBoughtShares);
  }

  /**
   * Test model for selling shares in a flexible portfolio.
   */
  @Test
  public void testModelSellSharesInAFlexiblePortfolio() {
    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            FILE_PATH, "2022-10-27", true);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    model.addStockToPortfolio("test", userStock1);
    model.addStockToPortfolio("test", userStock2);
    UserStock expectedSoldShares = userStock2;
    UserStock actualSoldShares = model.deleteStockFromPortfolio("test", userStock2);
    assertEquals(expectedSoldShares, actualSoldShares);
  }

  /**
   * Test model for cost basis of a flexible portfolio by date.
   */
  @Test
  public void testModelCostBasisOfAFlexiblePortfolioByDate() {
    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test", FILE_PATH,
            "2022-10-26", true);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-26").stockBought(1000).build();
    model.addStockToPortfolio("test", userStock1);
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-26").stockBought(2000).build();
    model.addStockToPortfolio("test", userStock2);
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-26").stockBought(1000).build();
    model.addStockToPortfolio("test", userStock3);
    UserStock userStock4 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stocksSold(1000).build();
    model.deleteStockFromPortfolio("test", userStock4);
    double expectedCostBasis = 405210.0;
    double actualCostBasis = model.getCostBasisOfPortfolio("test", "2022-10-27");
    assertEquals(expectedCostBasis, actualCostBasis, 0.01);
  }

  /**
   * Test model for inflexible portfolio creation.
   */
  @Test
  public void testModelWhenInflexiblePortfolioIsCreated() {

    IModel model = new Model();

    IUserPortfolio expectedFlexiblePortfolio = new UserPortfolio(FILE_PATH, "test",
            "2022-11-15", false);
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test", FILE_PATH,
            "2022-11-15", false);
    assertEquals(expectedFlexiblePortfolio, actualFlexiblePortfolio);
  }

  /**
   * Test model for view all in an inflexible portfolio.
   */
  @Test
  public void testModelViewAllPortfoliosForAnInflexiblePortfolio() {

    IModel model = new Model();
    Set<String> expectedPortfolioNames = new HashSet<>();
    expectedPortfolioNames.add("test");

    IUserPortfolio actualInflexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-11-15", false);
    Set<String> actualPortfolio = model.getAllPortfolios();
    assertEquals(expectedPortfolioNames, actualPortfolio);
  }

  /**
   * Test model for get composition in an inflexible portfolio.
   */
  @Test
  public void testModelGetCompositionOfAnInflexiblePortfolio() {
    IModel model = new Model();

    Map<String, Long> expectedComposition = new HashMap<>();
    expectedComposition.put(AAPL, 3000L);
    expectedComposition.put(GOOGL, 1000L);


    IUserPortfolio actualInflexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-27", false);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-27").stockBought(1000).build();
    List<UserStock> listOfSharesAdded = new ArrayList<>();
    listOfSharesAdded.add(userStock1);
    listOfSharesAdded.add(userStock2);
    listOfSharesAdded.add(userStock3);
    model.addAllStocksToPortfolio("test", listOfSharesAdded);
    Map<String, Long> actualComposition = model.compositionOfPortfolio("test","2022-11-30");


    assertEquals(expectedComposition, actualComposition);
  }

  /**
   * Test model for total value of an inflexible portfolio.
   */
  @Test
  public void testModelTotalValueOfAnInflexiblePortfolio() {
    IModel model = new Model();
    double expectedTotalValue = 540040.0;
    IUserPortfolio actualInflexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-27", false);

    List<UserStock> listOfSharesAdded = new ArrayList<>();
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-27").stockBought(1000).build();
    listOfSharesAdded.add(userStock1);
    listOfSharesAdded.add(userStock2);
    listOfSharesAdded.add(userStock3);
    model.addAllStocksToPortfolio("test", listOfSharesAdded);
    double actualTotalValue = model.totalValueOfPortfolio("test", "2022-10-27");

    assertEquals(expectedTotalValue, actualTotalValue, 0.01);


  }

  /**
   * Test model for buying shares in an inflexible portfolio.
   */
  @Test
  public void testModelBuySharesOfAnInflexiblePortfolio() {
    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-27", false);
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    List<UserStock> actualAddedShares = model.addAllStocksToPortfolio("test",
            List.of(userStock1));
    List<UserStock> expectedAddedShares = List.of(userStock1);
    assertEquals(expectedAddedShares, actualAddedShares);

  }

  /**
   * Test model for buying list of shares in an inflexible portfolio.
   */
  @Test
  public void testModelBuyListOfSharesInAnInflexiblePortfolio() {
    IModel model = new Model();

    List<UserStock> listOfSharesAdded = new ArrayList<>();
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-27").stockBought(1000).build();
    listOfSharesAdded.add(userStock1);
    listOfSharesAdded.add(userStock2);
    listOfSharesAdded.add(userStock3);

    List<UserStock> expectedListOfSharesAdded = listOfSharesAdded;

    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-27", false);
    List<UserStock> sharesAdded = new ArrayList<>();
    sharesAdded.add(userStock1);
    sharesAdded.add(userStock2);
    sharesAdded.add(userStock3);
    List<UserStock> actualListOfSharesAdded = model.addAllStocksToPortfolio("test",
            sharesAdded);

    assertEquals(expectedListOfSharesAdded, actualListOfSharesAdded);
  }

  /**
   * Test model for selling shares in an inflexible portfolio.
   */
  @Test
  public void testModelSellSharesOfAnInflexiblePortfolio() {
    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-27", false);
    List<UserStock> userStockList = new ArrayList<>();
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(1000).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-27").stockBought(2000).build();
    userStockList.add(userStock1);
    userStockList.add(userStock2);
    model.addAllStocksToPortfolio("test", userStockList);
    UserStock actualDeletedShares = model.deleteStockFromPortfolio("test",
            userStock2);
    UserStock expectedDeletedShares = null;
    assertEquals(expectedDeletedShares, actualDeletedShares);
  }

  /**
   * Test model for cost basis of an inflexible portfolio.
   */
  @Test
  public void testModelCostBasisOfAnInflexiblePortfolio() {
    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-26", false);
    List<UserStock> userStockList = new ArrayList<>();
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-26").stockBought(1000).commissionFeesForBuying(10).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-26").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-26").stockBought(1000).build();
    userStockList.add(userStock1);
    userStockList.add(userStock2);
    userStockList.add(userStock3);
    model.addAllStocksToPortfolio("test", userStockList);
    double expectedCostBasis = 405220.0;
    double actualCostBasis = model.getCostBasisOfPortfolio("test", "2022-10-26");
    assertEquals(expectedCostBasis, actualCostBasis, 0.01);

  }

  /**
   * Test model for modifying inflexible portfolio after creation.
   */
  @Test
  public void testModelForModifyingInflexiblePortfolioAfterCreation() {
    IModel model = new Model();
    IUserPortfolio actualFlexiblePortfolio = model.createPortfolio("test",
            "test/resources/test.txt", "2022-10-26", false);

    List<UserStock> userStockList = new ArrayList<>();
    UserStock userStock1 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-26").stockBought(1000).commissionFeesForBuying(10).build();
    UserStock userStock2 = UserStock.getUserStockBuilder().tickerSymbol(AAPL)
            .date("2022-10-26").stockBought(2000).build();
    UserStock userStock3 = UserStock.getUserStockBuilder().tickerSymbol(GOOGL)
            .date("2022-10-26").stockBought(1000).build();
    userStockList.add(userStock1);
    userStockList.add(userStock2);
    List<UserStock> actualAddedShares = model.addAllStocksToPortfolio("test",
            userStockList);

    List<UserStock> expectedAddedShares = userStockList;
    model.addAllStocksToPortfolio("test", List.of(userStock3));


    assertEquals(expectedAddedShares, actualAddedShares);

  }

  /**
   * Test controller for cost basis.
   */
  @Test
  public void testControllerForCostBasis() {
    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "\n2022-10-27\n"
            + "5\n"
            + "test\n"
            + "\n2022-10-27\n"
            + AAPL + "\n"
            + "100\n"
            + "10\n"
            + "6\n"
            + "test"
            + "\n2022-10-27\n"
            + AAPL + "\n"
            + "20\n"
            + "30\n"
            + "9\n"
            + "test\n"
            + "\n2022-10-27\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, true);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());

  }

  /**
   * Test controller when stocks are sold.
   */
  @Test
  public void testControllerWhenStockIsSold() {
    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "\n2022-10-27\n"
            + "5\n"
            + "test\n"
            + "\n2022-10-27\n"
            + AAPL + "\n"
            + "100\n"
            + "10\n"
            + "6\n"
            + "test"
            + "\n2022-10-27\n"
            + AAPL + "\n"
            + "20\n"
            + "30\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, true);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());

  }

  /**
   * Test controller when total value is requested.
   */
  @Test
  public void testControllerWhenTotalValueIsRequested() {
    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "\n2022-10-27\n"
            + "4\n"
            + "test\n"
            + "\n2022-10-27\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, true);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());
  }

  /**
   * Test controller when composition is requested.
   */
  @Test
  public void testControllerWhenCompositionIsRequested() {
    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "\n2022-10-27\n"
            + "3\n"
            + "test\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, true);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());

  }

  /**
   * Test controller when a list of stock is added.
   */
  @Test
  public void testControllerWhenListOfStockIsAdded() {
    StringBuffer input = new StringBuffer();
    input.append("8\n");
    input.append("test\n");
    input.append(FILE_PATH + "\n");
    input.append("2022-10-27\n");
    input.append("5\n");
    input.append("test\n");
    input.append("2\n");
    input.append("2022-10-27\n");
    input.append(AAPL.concat("\n"));
    input.append("100\n");
    input.append("10\n");

    input.append("2022-10-27\n");
    input.append(GOOGL.concat("\n"));
    input.append("200\n");
    input.append("20\n");

    input.append("13\n");
    input.append(META.concat("\n"));
    input.append("10\n");
    input.append(ORCL.concat("\n"));
    input.append("20\n");
    input.append("10\n");

    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, false);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());
  }

  /**
   * Test controller when portfolio is created.
   */
  @Test
  public void testControllerWhenPortfolioIsCreated() {
    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "\n2022-10-27\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, true);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());
  }

  /**
   * Test controller when a stock is added.
   */
  @Test
  public void testControllerWhenAStockIsAdded() {

    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "\n2022-10-27\n"
            + "5\n"
            + "test\n"
            + "\n2022-10-27\n"
            + AAPL + "\n"
            + "100\n"
            + "10\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer sb = new StringBuffer();
    IView view = new MockView(sb);
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log, true);
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    assertEquals("testtest/resources/test.txt2022-10-27", log.toString());
  }

  /**
   * Test view for cost basis.
   */
  @Test
  public void testViewForCostBasis() {

    StringBuffer input = new StringBuffer();
    StringBuffer actualOutput = new StringBuffer();
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME.getMessage()
            + "\n"
            + "test"
            + "\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG.getMessage()
            + "\n"
            + displayTickerSymbols()
            + Messages.NUMBER_OF_SHARES.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.STOCKS_SUCCESSFULLY_BOUGHT.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME_TO_SELL_STOCKS.getMessage()
            + "\n"
            + "test\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_SELL_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG_SEll.getMessage()
            + "\n"
            + Messages.NUMBER_OF_SHARES_SOLD.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.STOCKS_SUCCESSFULLY_SOLD.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + "test\n"
            + Messages.DATE_OF_COST_BASIS.getMessage()
            + "\n"
            + "Cost basis of the portfolio is 0.0"
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";
    input.append("1\n")
            .append("test\n")
            .append(FILE_PATH + "\n")
            .append("2022-10-27\n")
            .append("5\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append(AAPL + "\n")
            .append("100\n")
            .append("10\n")
            .append("6\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append(AAPL + "\n")
            .append("20\n")
            .append("30\n")
            .append("9\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append("10\n");

    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, true));
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test view displays messages correctly when a stock is added to the portfolio.
   */
  @Test
  public void testViewWhenStockIsAdded() {

    StringBuffer input = new StringBuffer();
    StringBuffer actualOutput = new StringBuffer();
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME.getMessage()
            + "\n"
            + "test"
            + "\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG.getMessage()
            + "\n"
            + displayTickerSymbols()
            + Messages.NUMBER_OF_SHARES.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.STOCKS_SUCCESSFULLY_BOUGHT.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";
    input.append("1\n")
            .append("test\n")
            .append(FILE_PATH + "\n")
            .append("2022-10-27\n")
            .append("5\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append(AAPL + "\n")
            .append("100\n")
            .append("10\n")
            .append("10\n");
    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, true));
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test view displays messages correctly when multiple stock are added to the portfolio.
   */
  @Test
  public void testViewWhenMultipleStocksAreAdded() {

    StringBuffer input = new StringBuffer();
    StringBuffer actualOutput = new StringBuffer();
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME.getMessage()
            + "\n"
            + "test"
            + "\n"
            + Messages.NUMBER_OF_STOCKS_TO_BUY.getMessage()
            + "\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG.getMessage()
            + "\n"
            + displayTickerSymbols()
            + Messages.NUMBER_OF_SHARES.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG.getMessage()
            + "\n"
            + displayTickerSymbols()
            + Messages.NUMBER_OF_SHARES.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";


    input.append("8\n");
    input.append("test\n");
    input.append(FILE_PATH + "\n");
    input.append("2022-10-27\n");
    input.append("5\n");
    input.append("test\n");
    input.append("2\n");
    input.append("2022-10-27\n");
    input.append(AAPL.concat("\n"));
    input.append("100\n");
    input.append("10\n");

    input.append("2022-10-27\n");
    input.append(GOOGL.concat("\n"));
    input.append("200\n");
    input.append("20\n");
    input.append("10\n");
    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, false));
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test view displays messages correctly when a portfolio composition is requested.
   */
  @Test
  public void testViewWhenGetCompositionIsCalled() {

    StringBuffer input = new StringBuffer();
    StringBuffer actualOutput = new StringBuffer();
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_PORTFOLIO_NAME.getMessage()
            + "\ntest\n"
            + Messages.COMPOSITION_IS_EMPTY.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";
    input.append("1\n")
            .append("test\n")
            .append(FILE_PATH + "\n")
            .append("2022-10-27\n")
            .append("3\n")
            .append("test\n")
            .append("10\n");
    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, true));
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test if view displays messages correctly when total value of portfolio
   * is requested.
   */
  @Test
  public void testViewWhenTotalValueIsCalled() {

    StringBuffer input = new StringBuffer();
    StringBuffer actualOutput = new StringBuffer();
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME.getMessage()
            + "\n"
            + "test"
            + "\n"
            + Messages.ENTER_DATE_TOTAL_VALUE.getMessage()
            + "\n"
            + "The total value of portfolio is: 0.0\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";
    input.append("1\n")
            .append("test\n")
            .append(FILE_PATH + "\n")
            .append("2022-10-27\n")
            .append("4\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append("10\n");
    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, true));
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test view when a portfolio is created.
   */
  @Test
  public void testViewWhenAPortfolioIsCreated() {
    StringBuffer input = new StringBuffer();
    input.append("1\n")
            .append("test\n")
            .append(FILE_PATH + "\n")
            .append("2022-10-27\n")
            .append("10\n");
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";
    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer actualOutput = new StringBuffer();
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, true));
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test view when the stock is sold.
   */
  @Test
  public void testViewWhenStockIsSold() {
    String expectedOutput = Messages.WELCOME_MESSAGE.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage()
            + "\n"
            + Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME.getMessage()
            + "\n"
            + "test"
            + "\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG.getMessage()
            + "\n"
            + displayTickerSymbols()
            + Messages.NUMBER_OF_SHARES.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.STOCKS_SUCCESSFULLY_BOUGHT.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n"
            + Messages.CORRECT_PORTFOLIO_NAME_TO_SELL_STOCKS.getMessage()
            + "\ntest\n"
            + Messages.ENTER_DATE_WHEN_YOU_WANT_TO_SELL_SHARES.getMessage()
            + "\n"
            + Messages.TICKER_MSG_SEll.getMessage()
            + "\n"
            + Messages.NUMBER_OF_SHARES_SOLD.getMessage()
            + "\n"
            + Messages.ENTER_COMMISSION_FEE.getMessage()
            + "\n"
            + Messages.STOCKS_SUCCESSFULLY_SOLD.getMessage()
            + "\n"
            + Messages.MAIN_MENU.getMessage()
            + "\n"
            + Messages.CREATE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.VIEW_ALL_PORTFOLIOS.getMessage()
            + "\n"
            + Messages.COMPOSITION.getMessage()
            + "\n"
            + Messages.TOTAL_VALUE.getMessage()
            + "\n"
            + Messages.BUY_STOCK.getMessage()
            + "\n"
            + Messages.SELL_STOCK.getMessage()
            + "\n"
            + Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage()
            + "\n"
            + Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage()
            + "\n"
            + Messages.COST_BASIS.getMessage()
            + "\n"
            + Messages.EXIT.getMessage()
            + "\n";


    StringBuffer input = new StringBuffer();
    input.append("1\n")
            .append("test\n")
            .append(FILE_PATH + "\n")
            .append("2022-10-27\n")
            .append("5\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append(AAPL + "\n")
            .append("100\n")
            .append("10\n")
            .append("6\n")
            .append("test\n")
            .append("2022-10-27\n")
            .append(AAPL + "\n")
            .append("20\n")
            .append("30\n")
            .append("10\n");
    InputStream b = new ByteArrayInputStream(input.toString().getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer actualOutput = new StringBuffer();
    IView view = new View(actualOutput);
    IController controller = new Controller(inputStreamReader, view);
    StringBuilder log = new StringBuilder();
    controller.execute(new MockModel(log, true));
    assertEquals(expectedOutput, actualOutput.toString());

  }

  @Test
  public void testStockApplicationIntegration() {
    String input = "1\n"
            + "test\n"
            + FILE_PATH + "\n"
            + "2022-10-27\n"
            + "5\n"
            + "test\n"
            + "2022-10-27\n"
            + AAPL + "\n"
            + "100\n"
            + "10\n"
            + "6\n"
            + "test"
            + "\n2022-10-27\n"
            + AAPL + "\n"
            + "20\n"
            + "30\n"
            + "10\n";
    InputStream b = new ByteArrayInputStream(input.getBytes());
    InputStreamReader inputStreamReader = new InputStreamReader(b);
    StringBuffer actualOutputBuffer = new StringBuffer();
    IView view = new View(actualOutputBuffer);
    IModel model = new Model();
    IController controller = new Controller(inputStreamReader, view);
    controller.execute(model);
    StringBuffer expectedOutputBuffer = new StringBuffer();
    expectedOutputBuffer
            .append(Messages.WELCOME_MESSAGE.getMessage() )
            .append("\n")
            .append(Messages.MAIN_MENU.getMessage())
            .append("\n")
            .append(Messages.CREATE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.VIEW_ALL_PORTFOLIOS.getMessage() )
            .append("\n")
            .append(Messages.COMPOSITION.getMessage())
            .append("\n")
            .append(Messages.TOTAL_VALUE.getMessage())
            .append("\n")
            .append(Messages.BUY_STOCK.getMessage())
            .append("\n")
            .append(Messages.SELL_STOCK.getMessage())
            .append("\n")
            .append(Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.COST_BASIS.getMessage())
            .append("\n")
            .append(Messages.EXIT.getMessage())
            .append("\n")
            .append(Messages.ENTER_NAME_OF_THE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.ENTER_PATH_FOR_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.ENTER_DATE_CREATE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.PORTFOLIO_SUCCESSFULLY_CREATED.getMessage())
            .append("\n")
            .append(Messages.MAIN_MENU.getMessage())
            .append("\n")
            .append(Messages.CREATE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.VIEW_ALL_PORTFOLIOS.getMessage() )
            .append("\n")
            .append(Messages.COMPOSITION.getMessage())
            .append("\n")
            .append(Messages.TOTAL_VALUE.getMessage())
            .append("\n")
            .append(Messages.BUY_STOCK.getMessage())
            .append("\n")
            .append(Messages.SELL_STOCK.getMessage())
            .append("\n")
            .append(Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.COST_BASIS.getMessage())
            .append("\n")
            .append(Messages.EXIT.getMessage())
            .append("\n")
            .append(Messages.CORRECT_PORTFOLIO_NAME.getMessage())
            .append("\n")
            .append( "test")
            .append("\n")
            .append(Messages.ENTER_DATE_WHEN_YOU_WANT_TO_BUY_SHARES.getMessage())
            .append("\n")
            .append(Messages.TICKER_MSG.getMessage())
            .append("\n")
            .append(displayTickerSymbols())
            .append(Messages.NUMBER_OF_SHARES.getMessage())
            .append("\n")
            .append(Messages.ENTER_COMMISSION_FEE.getMessage())
            .append("\n")
            .append(Messages.STOCKS_SUCCESSFULLY_BOUGHT.getMessage())
            .append("\n")
            .append(Messages.MAIN_MENU.getMessage())
            .append("\n")
            .append(Messages.CREATE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.VIEW_ALL_PORTFOLIOS.getMessage() )
            .append("\n")
            .append(Messages.COMPOSITION.getMessage())
            .append("\n")
            .append(Messages.TOTAL_VALUE.getMessage())
            .append("\n")
            .append(Messages.BUY_STOCK.getMessage())
            .append("\n")
            .append(Messages.SELL_STOCK.getMessage())
            .append("\n")
            .append(Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.COST_BASIS.getMessage())
            .append("\n")
            .append(Messages.EXIT.getMessage())
            .append("\n")
            .append(Messages.CORRECT_PORTFOLIO_NAME_TO_SELL_STOCKS.getMessage())
            .append("\n")
            .append("test")
            .append("\n")
            .append(Messages.ENTER_DATE_WHEN_YOU_WANT_TO_SELL_SHARES.getMessage())
            .append("\n")
            .append(Messages.TICKER_MSG_SEll.getMessage())
            .append("\n")
            .append(AAPL)
            .append("\n")
            .append(Messages.NUMBER_OF_SHARES_SOLD.getMessage())
            .append("\n")
            .append(Messages.ENTER_COMMISSION_FEE.getMessage())
            .append("\n")
            .append(Messages.STOCKS_SUCCESSFULLY_SOLD.getMessage())
            .append("\n")
            .append(Messages.MAIN_MENU.getMessage())
            .append("\n")
            .append(Messages.CREATE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.VIEW_ALL_PORTFOLIOS.getMessage() )
            .append("\n")
            .append(Messages.COMPOSITION.getMessage())
            .append("\n")
            .append(Messages.TOTAL_VALUE.getMessage())
            .append("\n")
            .append(Messages.BUY_STOCK.getMessage())
            .append("\n")
            .append(Messages.SELL_STOCK.getMessage())
            .append("\n")
            .append(Messages.GET_PERFORMANCE_OF_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.CREATE_INFLEXIBLE_PORTFOLIO.getMessage())
            .append("\n")
            .append(Messages.COST_BASIS.getMessage())
            .append("\n")
            .append(Messages.EXIT.getMessage())
            .append("\n");



    assertEquals(expectedOutputBuffer.toString(), actualOutputBuffer.toString());
  }


  /**
   * Helper to display ticker symbols.
   *
   * @return A string representation all the ticker symbols
   */
  private String displayTickerSymbols() {
    StringBuffer sb = new StringBuffer();
    Set<String> tickerSymbolArray = TickerSymbol.getAllTickerSymbols();
    for (String ticker : tickerSymbolArray) {
      sb.append("o" + " " + ticker).append("\n");
    }
    return sb.toString();
  }
}
