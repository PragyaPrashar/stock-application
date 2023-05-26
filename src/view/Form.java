package view;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Form class that represents a frame.
 */
public class Form extends JPanel {
  JFrame frame;

  /**
   * Initialises the form.
   */
  Form() {
    frame = new JFrame();
    frame.setLayout(new FlowLayout());
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Returns a frame.
   *
   * @return a frame
   */
  public JFrame getFrame() {
    return frame;
  }

  /**
   * Function to create a label.
   *
   * @param message message to display on the label
   * @return a label
   */
  public JLabel createLabel(String message) {
    return new JLabel(message);
  }

  /**
   * Function to create a button.
   *
   * @param message message to display on the button
   * @return a button
   */
  public JButton createButton(String message) {
    return new JButton(message);
  }

  /**
   * Function to create text field.
   *
   * @param coloumns width of the text field
   * @return a text field
   */
  public JTextField createTextField(int coloumns) {
    return new JTextField(coloumns);
  }

  /**
   * Function to add a component.
   *
   * @param component component
   */
  public void addComponent(Component component) {
    frame.add(component);
  }

  /**
   * Function to set frame size.
   *
   * @param width  width of the frame
   * @param height height of the frame
   */
  public void setFrameSize(int width, int height) {
    frame.setSize(width, height);
  }

  /**
   * Function to dispose form.
   */
  public void disposeForm() {
    frame.dispose();
  }
}
