import java.io.InputStreamReader;

import controller.Controller;
import controller.GuiController;
import controller.IController;
import model.IModel;
import model.Model;
import view.GuiView;
import view.IView;
//import view.JFrameView;
import view.View;
import view.WelcomePage;

/**
 * This represents the main class. The class initialises the program.
 */
public class StockApplicationDriver {
  /**
   * This is main function. The function initialises the program.
   *
   * @param args array of string as an argument
   */
  public static void main(String[] args) {
    IModel model = new Model();

    if (args != null && args.length > 0 && args[0].equalsIgnoreCase("gui")) {
      GuiView view = new WelcomePage();
      IController controller = new GuiController(view, model);
      controller.execute(model);
    } else {
      IView view = new View(System.out);
      InputStreamReader inputStreamReader = new InputStreamReader(System.in);
      IController controller = new Controller(inputStreamReader, view);
      controller.execute(model);
    }
  }

}
