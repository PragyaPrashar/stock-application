package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.awt.FlowLayout;
import java.awt.Container;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import controller.Features;
import java.awt.Color;
import java.awt.Font;

/**
 * A class that represents a welcome page.
 */
public class WelcomePage extends JFrame implements ActionListener, GuiView {
  private final JFrame frame;
  private final JButton myButton;

  private JButton closeButton;

  private Features features;

  private JFrame form;

  /**
   * constructor to create object of welcome page.
   */
  public WelcomePage() {
    frame = new JFrame();
    JLabel label = new JLabel();
    myButton = new JButton("Explore");
    label.setText("Welcome to Stock Application");
    label.setForeground(Color.BLACK);
    label.setFont(new Font("Serif", Font.BOLD, 25));
    label.setVerticalAlignment(SwingConstants.BOTTOM);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    myButton.setBounds(100, 160, 200, 40);
    myButton.setVerticalAlignment(SwingConstants.CENTER);
    myButton.setHorizontalAlignment(SwingConstants.CENTER);
    myButton.setFocusable(false);
    myButton.addActionListener(this);
    frame.add(label);
    frame.add(myButton);
    // frame.getContentPane().setBackground(Color.GRAY);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(420, 420);
    frame.setLayout(new FlowLayout());
    frame.setVisible(true);
  }

  /**
   * Function that displays all portfolio names vertically.
   * @param features features
   * @param container container of the frame
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
   * Callback function that is called when an event occurs in the gui.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == myButton) {
      frame.dispose();
      MainMenu myWindow = new MainMenu(features);
    } else if (e.getSource() == closeButton) {
      form.dispose();
    }
  }

  /**
   * sets the features for this gui.
   * @param features list of features for the gui
   */
  private void setFeatures(Features features) {
    this.features = features;
  }

  /**
   * function to invoke the action listeners when a button is clicked on GUI.
   *
   * @param features a list of features to be invoked based on user input
   */
  @Override
  public void addFeatures(Features features) {
    setFeatures(features);
  }

  /**
   * displays a message on the frame.
   *
   * @param message message that needs to be displayed
   */
  @Override
  public void showString(String message) {

    form = new JFrame();
    JLabel portfolioLabel = new JLabel("List of portfolios are:");
    Container container = getContentPane();
    container.setLayout(null);
    displayPortfolioNamesVertically(features, container);
    closeButton = new JButton("close");
    closeButton.addActionListener(this);
    closeButton.setBounds(50, 30, 100, 30);
    portfolioLabel.setBounds(50, 60, 800, 30);
    closeButton.setFocusable(false);
    container.add(portfolioLabel);
    container.add(closeButton);
    form.add(container);
    form.setTitle("View all porfolios");
    form.setVisible(true);
    form.setBounds(10, 10, 500, 500);
    form.setDefaultCloseOperation(EXIT_ON_CLOSE);
    form.setResizable(false);

  }

  /**
   * displays composition of the portoflio in the frame.
   *
   * @param map map consisting composition of the portfolio
   */
  public void showComposition(Map<String, Long> map) {
    JFrame form = new JFrame();
    JLabel portfolioLabel = new JLabel("Composition of portfolio is as follows");
    form.setLayout(null);

    int xCount = 50;
    int yCount = 90;
    for (Map.Entry<String, Long> entry : map.entrySet()) {
      JLabel tmp = new JLabel(entry.getKey() + " " + entry.getValue());
      tmp.setBounds(xCount, yCount, 800, 30);
      form.add(tmp);
      yCount += 20;
    }
    JButton closeButton = new JButton("close");
    closeButton.addActionListener(evt -> form.dispose());
    closeButton.setBounds(50, 30, 100, 30);
    portfolioLabel.setBounds(50, 60, 800, 30);
    closeButton.setFocusable(false);
    form.add(portfolioLabel);
    form.add(closeButton);
    form.setTitle("Composition");
    form.setVisible(true);
    form.setBounds(10, 10, 500, 500);
    form.setResizable(false);
  }

  /**
   * displays total value of portfolio on the frame.
   *
   * @param totalValue total value of the portfolio
   */
  public void showTotalValue(double totalValue) {
    JFrame form = new JFrame();
    JLabel portfolioLabel = new JLabel("Total value of portfolio is " + totalValue);
    form.setLayout(null);
    JButton closeButton = new JButton("close");
    closeButton.addActionListener(evt -> form.dispose());
    closeButton.setBounds(50, 30, 100, 30);
    portfolioLabel.setBounds(50, 60, 800, 30);
    closeButton.setFocusable(false);
    form.add(portfolioLabel);
    form.add(closeButton);
    form.setTitle("Total Value");
    form.setVisible(true);
    form.setBounds(10, 10, 500, 500);
    form.setResizable(false);
  }

  /**
   * displays costbasis of a portfolio.
   * @param costbasis costbasis as a number
   */
  public void showCostBasis(double costbasis) {
    JFrame form = new JFrame();
    JLabel portfolioLabel = new JLabel("Cost basis of portfolio is " + costbasis);
    form.setLayout(null);
    JButton closeButton = new JButton("close");
    closeButton.addActionListener(evt -> form.dispose());
    closeButton.setBounds(50, 30, 100, 30);
    portfolioLabel.setBounds(50, 60, 800, 30);
    closeButton.setFocusable(false);
    form.add(portfolioLabel);
    form.add(closeButton);
    form.setTitle("Cost basis");
    form.setVisible(true);
    form.setBounds(10, 10, 500, 500);
    form.setResizable(false);
  }
}
