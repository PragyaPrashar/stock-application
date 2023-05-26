package controller;

import model.IModel;

/**
 * The interface represents controller. The controller takes inputs from the user through text
 * based interface and tells the model what to do and the view what to show.
 */
public interface IController {
  /**
   * Function that triggers the controller to take input from the user through text based
   * interface and based on the input received delegate the work to view and model.
   */
  void execute(IModel model);

}
