package view;

/**
 * A mock view class to test the controller in isolation. This class will keep
 * track of the output that was printed by view.
 */
public class MockView implements IView {
  StringBuffer sb;

  /**
   * Constructor to initialise mock view.
   *
   * @param sb string buffer to keep track of the output that will be printed by view
   */
  public MockView(StringBuffer sb) {
    this.sb = sb;
  }

  /**
   * Function to append the message that was printed.
   *
   * @param message message that needs to be displayed
   */
  @Override
  public void showString(String message) {
    sb.append(message);

  }
}
