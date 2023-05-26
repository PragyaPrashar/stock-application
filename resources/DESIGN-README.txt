The architecture of this application follows Model-View-Controller architecture.

The Controller accepts input from the user and delegates the view and model to perform tasks.

Model consists of a PortfolioContainer which can perform operations such as creating a portfolio, calculating total value of the portfolio that a
user holds, fetching the composition of the portfolio by date, calculating cost basis of a portfolio by date and loading, applying dollar cost strategy
 to a portfolio and saving the portfolio in a file.

The View supports both a text based interface and Graphical User Interface with which the user interacts. Both text based and graphical based interface
will display appropriate messages to tell user to provide user input to perform the operations that a model would do.

New features added compared to previous assignment:
1. Support for calculating total value by specifying a date.
2. Support for specifying commission fees during buying and selling a share.
3. New options in menu to calculate total value and cost basis, viewing all portfolios and creating flexible portfolios.
4. A graphical user interface has been added.
5. Dollar cost strategy has been added
6. Invest a fixed amount in an existing portfolio by specifying weights in terms of percentage for each company in which the user wants to buy shares.

New Interfaces added compared to previous assignment:
1. A new interface GuiView has been added to support the graphical user interface. JFrameView class implements this interface.
2. A new method has been added to support dollar cost strategy in the model.
3. An abstract class has been created for representing a portfolio type. Flexible and Inflexible portfolios extend this class. Also, a
   new interface PortfolioType has been added. The abstract class implements this interface.
4. A new class GuiController is added that acts as a controller for GUI.
5. A new utility class is created which contains methods to fetch data from API.


