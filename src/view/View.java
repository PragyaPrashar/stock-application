package view;

import java.io.IOException;

/**
 * The class represents view. View displays messages and shows results of
 * operations to the user.
 */
public class View implements IView {

  private final Appendable appendable;

  /**
   * Constructor that initialises appendable.
   *
   * @param appendable appendable that will display the message
   */
  public View(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   * The function displays messages and shows results of operations to the user.
   *
   * @param s message that needs to be displayed
   */
  public void showString(String s) {
    try {
      appendable.append(s).append("\n");
    } catch (IOException e) {
      //
    }
  }
}


