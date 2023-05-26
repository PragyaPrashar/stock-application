package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import controller.Features;

/**
 * a class that represents a main menu.
 */
public class MainMenu implements ActionListener {
  private final Features features;
  JFrame frame;
  JLabel label;
  JButton button1;
  JButton button2;
  JButton button3;
  JButton button4;
  JButton button5;
  JButton button6;
  JButton button7;
  JButton button8;
  JButton button9;
  JButton button10;
  JButton button11;
  JButton button12;
  private int number;

  /**
   * constructor to create an object of main menu.
   *
   * @param features a list of features in the gui
   */
  MainMenu(Features features) {
    this.features = features;
    frame = new JFrame();
    label = new JLabel();
    label.setText("Please select your choice from the menu below");
    label.setBounds(100,40,400,50);

    button1 = new JButton("Create flexible portfolio");
    button1.setBounds(100, 100, 400, 50);
    button1.setVerticalAlignment(SwingConstants.CENTER);
    button1.setHorizontalAlignment(SwingConstants.CENTER);
    button1.setFocusable(false);
    button1.addActionListener(this);

    button2 = new JButton("View All Portfolios");
    button2.setBounds(100, 160, 400, 50);
    button2.setVerticalAlignment(SwingConstants.CENTER);
    button2.setHorizontalAlignment(SwingConstants.CENTER);
    button2.setFocusable(false);
    button2.addActionListener(this);

    button3 = new JButton("Get composition of portfolio");
    button3.setBounds(100, 220, 400, 50);
    button3.setVerticalAlignment(SwingConstants.CENTER);
    button3.setHorizontalAlignment(SwingConstants.CENTER);
    button3.setFocusable(false);
    button3.addActionListener(this);

    button4 = new JButton("Get total value of portfolio");
    button4.setBounds(100, 280, 400, 50);
    button4.setVerticalAlignment(SwingConstants.CENTER);
    button4.setHorizontalAlignment(SwingConstants.CENTER);
    button4.setFocusable(false);
    button4.addActionListener(this);

    button5 = new JButton("Buy shares");
    button5.setBounds(100, 340, 400, 50);
    button5.setVerticalAlignment(SwingConstants.CENTER);
    button5.setHorizontalAlignment(SwingConstants.CENTER);
    button5.setFocusable(false);
    button5.addActionListener(this);

    button6 = new JButton("Sell shares");
    button6.setBounds(100, 400, 400, 50);
    button6.setVerticalAlignment(SwingConstants.CENTER);
    button6.setHorizontalAlignment(SwingConstants.CENTER);
    button6.setFocusable(false);
    button6.addActionListener(this);


    button7 = new JButton("Get performance of a portfolio");
    button7.setBounds(100, 460, 400, 50);
    button7.setVerticalAlignment(SwingConstants.CENTER);
    button7.setHorizontalAlignment(SwingConstants.CENTER);
    button7.setFocusable(false);
    button7.addActionListener(this);


    button8 = new JButton("Create inflexible portfolio");
    button8.setBounds(100, 520, 400, 50);
    button8.setVerticalAlignment(SwingConstants.CENTER);
    button8.setHorizontalAlignment(SwingConstants.CENTER);
    button8.setFocusable(false);
    button8.addActionListener(this);

    button9 = new JButton("Get cost basis of portfolio by date");
    button9.setBounds(100, 580, 400, 50);
    button9.setVerticalAlignment(SwingConstants.CENTER);
    button9.setHorizontalAlignment(SwingConstants.CENTER);
    button9.setFocusable(false);
    button9.addActionListener(this);


    button10 = new JButton("Invest fixed amount in a portfolio");
    button10.setBounds(100, 640, 400, 50);
    button10.setVerticalAlignment(SwingConstants.CENTER);
    button10.setHorizontalAlignment(SwingConstants.CENTER);
    button10.setFocusable(false);
    button10.addActionListener(this);

    button11 = new JButton("Create portfolio using dollar cost averaging");
    button11.setBounds(100, 700, 400, 50);
    button11.setVerticalAlignment(SwingConstants.CENTER);
    button11.setHorizontalAlignment(SwingConstants.CENTER);
    button11.setFocusable(false);
    button11.addActionListener(this);

    button12 = new JButton("Exit MAIN MENU");
    button12.setBounds(100, 760, 400, 50);
    button12.setVerticalAlignment(SwingConstants.CENTER);
    button12.setHorizontalAlignment(SwingConstants.CENTER);
    button12.setFocusable(false);
    button12.addActionListener(this);


    frame.add(label);
    frame.add(button1);
    frame.add(button2);
    frame.add(button3);
    frame.add(button4);
    frame.add(button5);
    frame.add(button6);
    frame.add(button7);
    frame.add(button8);
    frame.add(button9);
    frame.add(button10);
    frame.add(button11);
    frame.add(button12);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    frame.setLayout(null);
    frame.setVisible(true);
  }

  /**
   * Returns a number that indicates the button that was clicked in the main menu.
   *
   * @return a number as integer
   */
  public int getNumber() {
    return number;
  }

  /**
   * Setter for setting number.
   *
   * @param number number to set
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * Callback function that is called when an event occurs in the gui.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == button1) {

      number = 1;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button2) {

      number = 2;
      JFrameView jFrameView = new JFrameView(number, features);
    }

    if (e.getSource() == button3) {

      number = 3;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button4) {

      number = 4;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button5) {

      number = 5;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button6) {

      number = 6;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button7) {

      number = 7;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button8) {

      number = 8;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button9) {

      number = 9;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button10) {

      number = 10;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button11) {

      number = 11;
      JFrameView jFrameView = new JFrameView(number, features);
    }
    if (e.getSource() == button12) {

      number = 12;
      JFrameView jFrameView = new JFrameView(number, features);
      frame.dispose();
    }

  }
}
