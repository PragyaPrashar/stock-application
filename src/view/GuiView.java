package view;

import java.util.Map;

import controller.Features;

/**
 * Interface to represent a graphical user interface.
 */
public interface GuiView extends IView {

  /**
   * Invoke all the listeners of every feature.
   *
   * @param features list of features
   */
  void addFeatures(Features features);

  /**
   * Display the composition.
   *
   * @param map map consisting of composition of the portfolio.
   */
  void showComposition(Map<String, Long> map);

  /**
   * Display the total value of the portfolio.
   *
   * @param totalValue total value of the portfolio
   */
  void showTotalValue(double totalValue);

  /**
   * display cost basis of the portfolio.
   * @param costbasis costbasis as a number
   */
  void showCostBasis(double costbasis);

}
