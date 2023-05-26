package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import controller.Features;
import enums.TickerSymbol;
import model.userportfolio.UserStock;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * A class that is an implementation of graphical user interface.
 */
public class JFrameView extends JFrame implements ActionListener, GuiView {

  private JFrame createPortfolioFrame;

  private Features features;

  private JLabel portfolioLabel;

  private JLabel fileLabel;

  private JLabel dateLabel;

  private JTextField portfolioField;
  private JTextField fileField;
  private JTextField dateField;

  private JButton createPortfolioSubmitButton;
  private JButton viewAllPortfolioSubmitButton;

  private JButton compositionSubmitButton;

  private JButton addStockSubmitButton;

  private JFrame viewAllPortfolioFrame;

  private JFrame compositionFrame;

  private JFrame addStockFrame;

  private JTextField sharesField;

  private JTextField commissionField;

  private String tickerSymbolChoosen;

  /**
   * Constructor that initialises the object.
   *
   * @param number   a number that indicates the button pressed on the screen
   * @param features list of features that the gui supports
   */

  JFrameView(int number, Features features) {
    this.features = features;
    setLayoutManager();
    setLocationAndSize();
    addComponentsToContainer();
    Container container = getContentPane();
    container.setLayout(null);

    if (number == 1) {
      createPortfolioFrame = new JFrame();
      portfolioLabel = new JLabel("Enter the portfolio name");
      fileLabel = new JLabel("Enter the file path");
      dateLabel = new JLabel("Enter the date");
      portfolioField = new JTextField();
      fileField = new JTextField();
      dateField = new JTextField();
      createPortfolioSubmitButton = new JButton("SUBMIT");
      createPortfolioSubmitButton.setFocusable(false);
      createPortfolioSubmitButton.addActionListener(this);
      portfolioLabel.setBounds(50, 150, 800, 30);
      fileLabel.setBounds(50, 220, 800, 30);
      dateLabel.setBounds(50, 290, 800, 30);
      portfolioField.setBounds(250, 150, 150, 30);
      fileField.setBounds(250, 220, 150, 30);
      dateField.setBounds(250, 290, 150, 30);

      createPortfolioSubmitButton.setBounds(200, 340, 100, 30);


      container.add(portfolioLabel);
      container.add(fileLabel);
      container.add(dateLabel);
      container.add(portfolioField);
      container.add(fileField);
      container.add(dateField);
      container.add(createPortfolioSubmitButton);
      createPortfolioFrame.add(container);
      createPortfolioFrame.setTitle("Create portfolio");
      createPortfolioFrame.setVisible(true);
      createPortfolioFrame.setBounds(10, 10, 500, 500);
      createPortfolioFrame.setResizable(false);
    }

    if (number == 2) {

      viewAllPortfolioFrame = new JFrame();
      JLabel portfolioLabel = new JLabel("List of portfolios are:");
      displayPortfolioNamesVertically(features, container);
      viewAllPortfolioSubmitButton = new JButton("close");
      viewAllPortfolioSubmitButton.addActionListener(this);
      viewAllPortfolioSubmitButton.setBounds(50, 30, 100, 30);
      portfolioLabel.setBounds(50, 60, 800, 30);
      viewAllPortfolioSubmitButton.setFocusable(false);
      container.add(portfolioLabel);
      container.add(viewAllPortfolioSubmitButton);
      viewAllPortfolioFrame.add(container);
      viewAllPortfolioFrame.setTitle("View all porfolios");
      viewAllPortfolioFrame.setVisible(true);
      viewAllPortfolioFrame.setBounds(10, 10, 500, 500);
      viewAllPortfolioFrame.setResizable(false);
    }


    if (number == 3) {
      compositionFrame = new JFrame();
      JLabel allPortfoliosHeaderLabel = new JLabel("List of portfolios are: ");
      JLabel allPortfoliosLabel = new JLabel(displayAllPortfoliosHorizontally(features));

      portfolioLabel = new JLabel("Enter the portfolio name");
      dateLabel = new JLabel("Enter the date");
      portfolioField = new JTextField();
      dateField = new JTextField();

      compositionSubmitButton = new JButton("SUBMIT");
      compositionSubmitButton.setFocusable(false);
      compositionSubmitButton.addActionListener(this);
      allPortfoliosLabel.setBounds(50, 100, 800, 30);
      allPortfoliosHeaderLabel.setBounds(50, 70, 800, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      dateLabel.setBounds(50, 190, 800, 30);
      portfolioField.setBounds(300, 150, 150, 30);
      dateField.setBounds(300, 190, 150, 30);

      compositionSubmitButton.setBounds(200, 240, 100, 30);

      container.add(allPortfoliosHeaderLabel);
      container.add(allPortfoliosLabel);
      container.add(portfolioLabel);
      container.add(dateLabel);
      container.add(portfolioField);
      container.add(dateField);
      container.add(compositionSubmitButton);
      compositionFrame.add(container);
      compositionFrame.setTitle("Composition of portfolio");
      compositionFrame.setVisible(true);
      compositionFrame.setBounds(10, 10, 500, 500);
      compositionFrame.setResizable(false);
    }


    if (number == 4) {
      JFrame totalValueFrame = new JFrame();
      JLabel allPortfoliosHeaderLabel = new JLabel("List of portfolios are: ");
      JLabel allPortfoliosLabel = new JLabel(displayAllPortfoliosHorizontally(features));

      JLabel portfolioLabel = new JLabel("Enter the portfolio name");
      JLabel dateLabel = new JLabel("Enter the date");
      portfolioField = new JTextField();
      dateField = new JTextField();

      JButton submitButton = new JButton("SUBMIT");
      submitButton.setFocusable(false);

      submitButton.addActionListener(e -> {
        String portfolioName = portfolioField.getText();
        String dateOfCreation = dateField.getText();
        features.totalValueOfPortfolio(portfolioName, dateOfCreation);
        totalValueFrame.dispose();
      });

      allPortfoliosHeaderLabel.setBounds(50, 70, 800, 30);
      allPortfoliosLabel.setBounds(50, 100, 800, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      dateLabel.setBounds(50, 190, 800, 30);
      portfolioField.setBounds(300, 150, 150, 30);
      dateField.setBounds(300, 190, 150, 30);

      submitButton.setBounds(200, 240, 100, 30);

      container.add(allPortfoliosHeaderLabel);
      container.add(allPortfoliosLabel);
      container.add(portfolioLabel);
      container.add(dateLabel);
      container.add(portfolioField);
      container.add(dateField);
      container.add(submitButton);
      totalValueFrame.add(container);
      totalValueFrame.setTitle("Total value of portfolio is: ");
      totalValueFrame.setVisible(true);
      totalValueFrame.setBounds(10, 10, 500, 500);
      totalValueFrame.setResizable(false);
    }


    if (number == 5) {
      addStockFrame = new JFrame();
      JLabel allPortfoliosHeaderLabel = new JLabel("List of portfolios are: ");
      JLabel allPortfoliosLabel = new JLabel(displayAllPortfoliosHorizontally(features));
      portfolioLabel = new JLabel("Enter the portfolio name");
      dateLabel = new JLabel("Enter the date");

      JLabel allTickerHeaderLabel = new JLabel("Select the company "
              + "name in which you want to invest");
      final JComboBox<String> allTickerDropDown = new JComboBox<String>(TickerSymbol
              .getAllTickerSymbols().toArray(new String[]{}));
      allTickerDropDown.setMaximumSize(allTickerDropDown.getPreferredSize()); // added code
      allTickerDropDown.addItemListener(i -> {
        tickerSymbolChoosen = String.valueOf(allTickerDropDown.getSelectedItem());
        System.out.println("selected item is " + tickerSymbolChoosen);
      });


      JLabel sharesLabel = new JLabel("Enter the number of shares that you want to buy");
      JLabel commissionFeesLabel = new JLabel("Enter the commission fees");


      portfolioField = new JTextField();
      dateField = new JTextField();
      sharesField = new JTextField();
      commissionField = new JTextField();


      addStockSubmitButton = new JButton("SUBMIT");
      addStockSubmitButton.setFocusable(false);
      addStockSubmitButton.addActionListener(this);


      allPortfoliosHeaderLabel.setBounds(50, 70, 800, 30);
      allPortfoliosLabel.setBounds(50, 110, 800, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      dateLabel.setBounds(50, 190, 800, 30);
      allTickerHeaderLabel.setBounds(50, 230, 800, 30);
      allTickerDropDown.setBounds(50, 270, 150, 30);
      sharesLabel.setBounds(50, 310, 800, 30);
      commissionFeesLabel.setBounds(50, 350, 800, 30);


      portfolioField.setBounds(250, 150, 150, 30);
      dateField.setBounds(250, 190, 150, 30);
      sharesField.setBounds(400, 310, 150, 30);
      commissionField.setBounds(400, 350, 150, 30);

      addStockSubmitButton.setBounds(50, 430, 100, 30);

      container.add(allPortfoliosHeaderLabel);
      container.add(allPortfoliosLabel);
      container.add(portfolioLabel);
      container.add(dateLabel);
      container.add(allTickerHeaderLabel);
      container.add(allTickerDropDown);
      container.add(sharesLabel);
      container.add(commissionFeesLabel);
      container.add(portfolioField);
      container.add(dateField);
      container.add(dateField);
      container.add(sharesField);
      container.add(commissionField);
      container.add(addStockSubmitButton);
      addStockFrame.add(container);
      addStockFrame.setTitle("Buy portfolio");
      addStockFrame.setVisible(true);
      addStockFrame.setBounds(10, 10, 800, 800);
      addStockFrame.setResizable(false);
    }


    if (number == 6) {
      JFrame sellSharesFrame = new JFrame();
      JLabel allPortfoliosHeaderLabel = new JLabel("List of portfolios are: ");
      JLabel allPortfoliosLabel = new JLabel(displayAllPortfoliosHorizontally(features));
      JLabel portfolioLabel = new JLabel("Enter the portfolio name");
      JLabel dateLabel = new JLabel("Enter the date");

      JLabel allTickerHeaderLabel = new JLabel("Select the company name from "
              + "which you want to sell stocks");
      final JComboBox<String> allTickerDropDown = new JComboBox<String>(TickerSymbol
              .getAllTickerSymbols().toArray(new String[0]));
      allTickerDropDown.setMaximumSize(allTickerDropDown.getPreferredSize()); // added code
      allTickerDropDown.addItemListener(i -> tickerSymbolChoosen = String
              .valueOf(allTickerDropDown.getSelectedItem()));
      JLabel sharesLabel = new JLabel("Enter the number of shares that you want to sell");
      JLabel commissionFeesLabel = new JLabel("Enter the commission fees");


      JTextField portfolioField = new JTextField();
      JTextField dateField = new JTextField();
      JTextField sharesField = new JTextField();
      JTextField commissionField = new JTextField();


      JButton submitButton = new JButton("SUBMIT");
      submitButton.setFocusable(false);
      submitButton.addActionListener(e -> {
        UserStock userStock = UserStock.getUserStockBuilder()
                .date(dateField.getText())
                .tickerSymbol(tickerSymbolChoosen.toUpperCase(Locale.US))
                .stocksSold(Long.parseLong(sharesField.getText()))
                .commissionFeesForBuying(Long.parseLong(commissionField.getText()))
                .build();
        features.deleteStockFromPortfolio(portfolioField.getText(), userStock);
        sellSharesFrame.dispose();
      });

      allPortfoliosHeaderLabel.setBounds(50, 70, 800, 30);
      allPortfoliosLabel.setBounds(50, 110, 800, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      dateLabel.setBounds(50, 190, 800, 30);
      allTickerHeaderLabel.setBounds(50, 230, 800, 30);
      allTickerDropDown.setBounds(50, 270, 150, 30);
      sharesLabel.setBounds(50, 310, 800, 30);
      commissionFeesLabel.setBounds(50, 350, 800, 30);
      portfolioField.setBounds(250, 150, 150, 30);
      dateField.setBounds(250, 190, 150, 30);
      sharesField.setBounds(400, 310, 150, 30);
      commissionField.setBounds(400, 350, 150, 30);

      submitButton.setBounds(50, 430, 100, 30);

      container.add(allPortfoliosHeaderLabel);
      container.add(allPortfoliosLabel);
      container.add(portfolioLabel);
      container.add(dateLabel);
      container.add(allTickerHeaderLabel);
      container.add(allTickerDropDown);
      container.add(sharesLabel);
      container.add(commissionFeesLabel);

      container.add(portfolioField);
      container.add(dateField);
      container.add(dateField);
      container.add(sharesField);
      container.add(commissionField);
      container.add(submitButton);
      sellSharesFrame.add(container);
      sellSharesFrame.setTitle("Sell portfolio");
      sellSharesFrame.setVisible(true);
      sellSharesFrame.setBounds(10, 10, 800, 800);
      sellSharesFrame.setResizable(false);
    }
    if (number == 8) {
      JFrame frame = new JFrame();
      JLabel portfolioLabel = new JLabel("Enter the portfolio name");
      JLabel fileLabel = new JLabel("Enter the file path");
      JLabel dateLabel = new JLabel("Enter the date");
      JTextField portfolioField = new JTextField();
      JTextField fileField = new JTextField();
      JTextField dateField = new JTextField();

      JButton submitButton = new JButton("SUBMIT");
      submitButton.setFocusable(false);

      submitButton.addActionListener(a -> {
        String portfolioName = portfolioField.getText();
        String filePath = fileField.getText();
        String dateOfCreation = dateField.getText();
        features.createPortfolio(portfolioName, filePath, dateOfCreation, false);
        frame.dispose();
      });

      portfolioLabel.setBounds(50, 150, 800, 30);
      fileLabel.setBounds(50, 220, 800, 30);
      dateLabel.setBounds(50, 290, 800, 30);
      portfolioField.setBounds(250, 150, 150, 30);
      fileField.setBounds(250, 220, 150, 30);
      dateField.setBounds(250, 290, 150, 30);

      submitButton.setBounds(200, 340, 100, 30);


      container.add(portfolioLabel);
      container.add(fileLabel);
      container.add(dateLabel);
      container.add(portfolioField);
      container.add(fileField);
      container.add(dateField);
      container.add(submitButton);
      frame.add(container);
      frame.setTitle("Create inflexible portfolio");
      frame.setVisible(true);
      frame.setBounds(10, 10, 500, 500);
      frame.setResizable(false);


    }


    if (number == 9) {
      JFrame frame = new JFrame();
      JLabel allPortfoliosHeaderLabel = new JLabel("List of portfolios are: ");
      JLabel allPortfoliosLabel = new JLabel(displayAllPortfoliosHorizontally(features));

      JLabel portfolioLabel = new JLabel("Enter the portfolio name");
      JLabel dateLabel = new JLabel("Enter the date");
      JTextField portfolioField = new JTextField();
      JTextField dateField = new JTextField();

      JButton submitButton = new JButton("SUBMIT");
      submitButton.setFocusable(false);
      submitButton.addActionListener(a -> {
        String portfolioName = portfolioField.getText();
        String date = dateField.getText();
        features.getCostBasisOfPortfolio(portfolioName, date);
        frame.dispose();
      });

      allPortfoliosHeaderLabel.setBounds(50, 70, 800, 30);
      allPortfoliosLabel.setBounds(50, 100, 800, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      dateLabel.setBounds(50, 190, 800, 30);
      portfolioField.setBounds(250, 150, 150, 30);
      dateField.setBounds(250, 190, 150, 30);

      submitButton.setBounds(200, 240, 100, 30);

      container.add(allPortfoliosHeaderLabel);
      container.add(allPortfoliosLabel);
      container.add(portfolioLabel);
      container.add(dateLabel);
      container.add(portfolioField);
      container.add(dateField);
      container.add(submitButton);
      frame.add(container);
      frame.setTitle("Cost basis of portfolio");
      frame.setVisible(true);
      frame.setBounds(10, 10, 500, 500);
      frame.setResizable(false);
    }


    if (number == 10) {
      JFrame frame = new JFrame();
      JLabel allPortfoliosHeaderLabel = new JLabel("List of portfolios are: ");
      JLabel allPortfoliosLabel = new JLabel(displayAllPortfoliosHorizontally(features));
      JLabel portfolioLabel = new JLabel("Enter the portfolio name");
      JLabel dateLabel = new JLabel("Enter the date");
      JLabel fixedAmountLabel = new JLabel("Enter the fixed amount "
              + "that you want to invest");
      JLabel numberOfCompaniesLabel = new JLabel("Enter the number of "
              + "companies in which you want to invest");

      JTextField portfolioField = new JTextField();
      JTextField dateField = new JTextField();
      JTextField fixedAmountField = new JTextField();
      JTextField numberOfCompaniesField = new JTextField();

      JButton submitButton = new JButton("SUBMIT");
      submitButton.setFocusable(false);
      submitButton.addActionListener(a -> {

        long fixedAmount = Long.parseLong(fixedAmountField.getText());
        int companies = Integer.parseInt(numberOfCompaniesField.getText());
        int original = companies;
        JFrame percentageFrame = new JFrame();
        List<UserStock> stocks = new ArrayList<>();
        while (companies != 0) {

          final JComboBox<String> allTickerDropDown = new JComboBox<String>(TickerSymbol
                    .getAllTickerSymbols().toArray(new String[]{}));
          allTickerDropDown.setMaximumSize(allTickerDropDown.getPreferredSize()); // added code

          allTickerDropDown.addItemListener(i ->
                    tickerSymbolChoosen = String.valueOf(allTickerDropDown.getSelectedItem()));
          JLabel percentageLabel = new JLabel("Percentage of shares "
                  + "that you want to invest");

          JTextField percentageField = new JTextField();
          JButton percentageSubmitButton = new JButton("SUBMIT");
          percentageSubmitButton.setFocusable(false);
          percentageField.setColumns(20);
          percentageField.setBounds(300, 150, 200, 30);
          percentageFrame.setLayout(new FlowLayout());
          percentageFrame.add(allTickerDropDown);
          percentageFrame.add(percentageLabel);
          percentageFrame.add(percentageField);
          percentageFrame.add(percentageSubmitButton);
          percentageFrame.setVisible(true);
          percentageFrame.setTitle("Percentage of shares in each company");
          percentageFrame.setResizable(false);
          percentageFrame.setSize(800, 800);

          percentageSubmitButton.addActionListener(action -> {

            UserStock userStock = UserStock.getUserStockBuilder()
                      .date(dateField.getText())
                      .tickerSymbol(tickerSymbolChoosen.toUpperCase(Locale.US))
                      .stockBought((long) (fixedAmount * Double
                              .parseDouble(percentageField.getText()) / 100))
                      .build();
            stocks.add(userStock);
            if (stocks.size() == original) {
              frame.dispose();
              features.addAllStocksToPortfolio(portfolioField.getText(), stocks);
              JOptionPane.showMessageDialog(percentageSubmitButton, "Stocks "
                        + "invested using weights successfully");


              percentageFrame.dispose();
            }
          });
          companies--;
        }

      });

      allPortfoliosHeaderLabel.setBounds(50, 70, 800, 30);
      allPortfoliosLabel.setBounds(50, 110, 800, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      dateLabel.setBounds(50, 190, 800, 30);
      fixedAmountLabel.setBounds(50, 230, 800, 30);
      numberOfCompaniesLabel.setBounds(50, 270, 800, 30);


      portfolioField.setBounds(250, 150, 150, 30);
      dateField.setBounds(250, 190, 150, 30);
      fixedAmountField.setBounds(450, 230, 150, 30);
      numberOfCompaniesField.setBounds(450, 270, 150, 30);

      submitButton.setBounds(50, 330, 100, 30);

      container.add(allPortfoliosHeaderLabel);
      container.add(allPortfoliosLabel);
      container.add(portfolioLabel);
      container.add(dateLabel);
      container.add(fixedAmountLabel);
      container.add(numberOfCompaniesLabel);
      container.add(portfolioField);
      container.add(dateField);
      container.add(dateField);
      container.add(fixedAmountField);
      container.add(numberOfCompaniesField);
      container.add(submitButton);
      frame.add(container);
      frame.setTitle("Invest fixed amount in portfolio");
      frame.setVisible(true);
      frame.setBounds(10, 10, 800, 800);
      frame.setResizable(false);
    }
    if (number == 11) {
      JFrame frame = new JFrame();
      JLabel portfolioLabel = new JLabel("Enter the portfolio name");
      JLabel fileLabel = new JLabel("Enter the file path");
      JLabel dateLabel = new JLabel("Enter the date");
      JTextField portfolioField = new JTextField();
      JTextField fileField = new JTextField();
      JTextField dateField = new JTextField();

      JButton submitButton = new JButton("SUBMIT");
      submitButton.setFocusable(false);

      submitButton.addActionListener(a -> {
        String portfolioName = portfolioField.getText();
        String filePath = fileField.getText();
        String dateOfCreation = dateField.getText();
        features.createPortfolio(portfolioName, filePath, dateOfCreation, true);
        frame.dispose();
        JFrame dollarFrame = new JFrame();
        JLabel startDateLabel = new JLabel("Start date for strategy");
        JLabel endDateLabel = new JLabel("End date for strategy");
        JLabel numberOfCompaniesLabel = new JLabel("Number of companies in which "
                + "you want to invest");
        JLabel fixedAmountLabel = new JLabel("Enter the fixed amount that you want "
                + "to invest periodically");
        JLabel periodicIntervalLabel = new JLabel("Periodic interval");
        JLabel timeUnitLabel = new JLabel("Time Unit");
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JTextField numberOfCompaniesField = new JTextField();
        JTextField fixedAmountField = new JTextField();
        JTextField periodicIntervalField = new JTextField();
        JTextField timeUnitField = new JTextField();
        JButton dollarSubmitButton = new JButton("SUBMIT");
        dollarSubmitButton.setFocusable(false);
        java.util.List<UserStock> stocks = new ArrayList<>();

        startDateLabel.setBounds(50, 150, 800, 30);
        endDateLabel.setBounds(50, 220, 800, 30);
        numberOfCompaniesLabel.setBounds(50, 290, 800, 30);

        fixedAmountLabel.setBounds(50, 360, 800, 30);
        periodicIntervalLabel.setBounds(50, 430, 800, 30);
        timeUnitLabel.setBounds(50, 500, 800, 30);

        startDateField.setBounds(200, 150, 150, 30);
        endDateField.setBounds(200, 220, 150, 30);
        numberOfCompaniesField.setBounds(450, 290, 150, 30);
        fixedAmountField.setBounds(450, 360, 150, 30);
        periodicIntervalField.setBounds(200, 430, 150, 30);
        timeUnitField.setBounds(200, 500, 150, 30);
        dollarSubmitButton.setBounds(50, 570, 100, 30);
        Container parentContainer = dollarFrame.getContentPane();

        dollarFrame.add(startDateLabel);
        dollarFrame.add(endDateLabel);
        dollarFrame.add(numberOfCompaniesLabel);
        dollarFrame.add(timeUnitLabel);
        dollarFrame.add(periodicIntervalLabel);
        dollarFrame.add(fixedAmountLabel);

        dollarFrame.add(startDateField);
        dollarFrame.add(endDateField);
        dollarFrame.add(numberOfCompaniesField);
        dollarFrame.add(fixedAmountField);
        dollarFrame.add(periodicIntervalField);
        dollarFrame.add(timeUnitField);

        dollarFrame.add(dollarSubmitButton);

        dollarFrame.setLayout(null);

        dollarFrame.setTitle("Invest using dollar cost strategy");
        dollarFrame.setVisible(true);
        dollarFrame.setBounds(30, 120, 1000, 1000);
        dollarFrame.setResizable(false);
        dollarSubmitButton.addActionListener(aa -> {
          String startDate = startDateField.getText();
          String endDate = endDateField.getText();

          long fixedAmount = Long.parseLong(fixedAmountField.getText());
          long periodicInterval = Long.parseLong(periodicIntervalField.getText());
          String timeUnit = timeUnitField.getText();
          dollarFrame.dispose();
          int companies = Integer.parseInt(numberOfCompaniesField.getText());
          int original = companies;
          JFrame percentageFrame = new JFrame();
          while (companies != 0) {

            final JComboBox<String> allTickerDropDown = new JComboBox<String>(TickerSymbol
                    .getAllTickerSymbols().toArray(new String[]{}));
            allTickerDropDown.setMaximumSize(allTickerDropDown.getPreferredSize()); // added code

            allTickerDropDown.addItemListener(i ->
                    tickerSymbolChoosen = String.valueOf(allTickerDropDown.getSelectedItem()));
            JLabel percentageLabel = new JLabel("Percentage of shares "
                    + "that you want to invest");

            JTextField percentageField = new JTextField();
            JButton percentageSubmitButton = new JButton("SUBMIT");
            percentageSubmitButton.setFocusable(false);
            percentageField.setColumns(20);
            percentageField.setBounds(300, 150, 200, 30);
            percentageFrame.setLayout(new FlowLayout());
            percentageFrame.add(allTickerDropDown);
            percentageFrame.add(percentageLabel);
            percentageFrame.add(percentageField);
            percentageFrame.add(percentageSubmitButton);
            percentageFrame.setVisible(true);
            percentageFrame.setTitle("Percentage of shares in each company");
            percentageFrame.setResizable(false);
            percentageFrame.setSize(800, 800);

            percentageSubmitButton.addActionListener(action -> {

              UserStock userStock = UserStock.getUserStockBuilder()
                      .date(startDate)
                      .tickerSymbol(tickerSymbolChoosen.toUpperCase(Locale.US))
                      .stockBought((long) (fixedAmount * Double
                              .parseDouble(percentageField.getText()) / 100))
                      .build();
              stocks.add(userStock);
              if (stocks.size() == original) {

                features.dollarCostAveraging(portfolioField.getText(), startDate,
                        endDate, stocks, periodicInterval, timeUnit);
                dollarFrame.dispose();
                JOptionPane.showMessageDialog(dollarSubmitButton, "Dollar "
                        + "cost strategy successfully applied");
                percentageFrame.dispose();

              }
            });
            companies--;
          }
        });
      });
      submitButton.setBounds(200, 340, 100, 30);
      portfolioLabel.setBounds(50, 150, 800, 30);
      fileLabel.setBounds(50, 220, 800, 30);
      dateLabel.setBounds(50, 290, 800, 30);
      portfolioField.setBounds(250, 150, 150, 30);
      fileField.setBounds(250, 220, 150, 30);
      dateField.setBounds(250, 290, 150, 30);
      container.add(portfolioLabel);
      container.add(fileLabel);
      container.add(dateLabel);
      container.add(portfolioField);
      container.add(fileField);
      container.add(dateField);
      container.add(submitButton);
      frame.add(container);
      frame.setTitle("Dollar Cost Strategy");
      frame.setVisible(true);
      frame.setBounds(10, 10, 500, 500);
      frame.setResizable(false);

    }
  }

  /**
   * Helper function that displays portfolio names vertically.
   *
   * @param features  list of features that this gui supports
   * @param container container that belongs to the frame
   */
  private static void displayPortfolioNamesVertically(Features features, Container container) {
    Set<String> allPortfolios = features.getAllPortfolios();
    int xCount = 50;
    int yCount = 90;
    for (String portfolioName : allPortfolios) {
      JLabel tmp = new JLabel(portfolioName);
      tmp.setBounds(xCount, yCount, 800, 30);
      container.add(tmp);
      yCount += 20;
    }
  }

  /**
   * Helper function that displays all portfolios horizontally.
   *
   * @param features list of features that this gui supports
   * @return returns a string representation of a set of portfolio names
   */
  private String displayAllPortfoliosHorizontally(Features features) {
    Set<String> allPortfolios = features.getAllPortfolios();
    StringBuffer sb = new StringBuffer();
    for (String portfolioName : allPortfolios) {
      sb.append(portfolioName).append("  ");
    }
    return sb.toString();
  }

  /**
   * The function sets the layout manger.
   */
  public void setLayoutManager() {
    //

  }

  /**
   * The function that sets location and size of each component using setBound() method.
   */
  public void setLocationAndSize() {
    //
  }

  /**
   * The function that adds components to container.
   */
  public void addComponentsToContainer() {
    //
  }


  /**
   * The function that performs an action on a button click.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == createPortfolioSubmitButton) {
      String portfolioName = portfolioField.getText();
      String filePath = fileField.getText();
      String dateOfCreation = dateField.getText();
      features.createPortfolio(portfolioName, filePath, dateOfCreation, true);
      createPortfolioFrame.dispose();
    } else if (e.getSource() == viewAllPortfolioSubmitButton) {
      viewAllPortfolioFrame.dispose();
    } else if (e.getSource() == compositionSubmitButton) {
      String portfolioName = portfolioField.getText();
      String dateOfCreation = dateField.getText();
      if (!features.validateDate(dateOfCreation)) {
        JOptionPane.showMessageDialog(compositionSubmitButton, "Date "
                + "should be in the format yyyy-mm-dd");
      }
      Map<String, Long> composition = features.compositionOfPortfolio(portfolioName,
              dateOfCreation);
      if (composition == null) {
        JOptionPane.showMessageDialog(compositionSubmitButton, "Portfolio does not "
                + "exist. Please create a portfolio first");
      } else {
        compositionFrame.dispose();
      }

    } else if (e.getSource() == addStockSubmitButton) {
      String portfolioName = portfolioField.getText();
      String dateOfPurchase = dateField.getText();
      UserStock userStock = UserStock.getUserStockBuilder()
              .date(dateOfPurchase)
              .tickerSymbol(tickerSymbolChoosen.toUpperCase(Locale.US))
              .stockBought(Long.parseLong(sharesField.getText()))
              .commissionFeesForBuying(Long.parseLong(commissionField.getText()))
              .build();
      features.addStockToPortfolio(portfolioName, userStock);
      addStockFrame.dispose();
    }
  }

  /**
   * The function that sets features for the gui.
   *
   * @param features list of features
   */
  private void setFeatures(Features features) {
    this.features = features;
  }

  /**
   * Invokes features based on user input.
   *
   * @param features list of features
   */
  @Override
  public void addFeatures(Features features) {
    setFeatures(features);
  }

  /**
   * Function that displays composition in a frame.
   *
   * @param map map consisting of composition of the portfolio
   */
  @Override
  public void showComposition(Map<String, Long> map) {
    //

  }

  /**
   * Function that displays total value of a portfolio.
   *
   * @param totalValue total value of the portfolio
   */
  @Override
  public void showTotalValue(double totalValue) {
    //

  }

  /**
   * displayes cost basis of the portfolio.
   * @param costbasis costbasis as a number
   */
  @Override
  public void showCostBasis(double costbasis) {
    //
  }

  /**
   * Function that displays a message by creating a form.
   *
   * @param message message that needs to be displayed
   */
  @Override
  public void showString(String message) {

    Form form = new Form();
    this.add(form);
    form.setFrameSize(500, 500);
    JLabel displayLabel = form.createLabel("List of portfolios are:  ");
    form.addComponent(displayLabel);
    JLabel messageLabel = form.createLabel(message);
    form.addComponent(messageLabel);
    JButton closeButton = form.createButton("Close");
    form.addComponent(closeButton);
    closeButton.addActionListener(e -> form.disposeForm());
  }
}