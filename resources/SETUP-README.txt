This application runs on Java 11. To run this application one has to install Java 11 in the machine where the application has to be run.

Please follow the instructions below to run the JAR file
1. Go to the resources folder where the JAR file is stored and open command prompt on a Windows machine or terminal on macOS.
2. Run the below command
   a. java -jar <Name of the JAR file> <options>(e.g if the JAR file name is stock.jar then the command would be java -jar stock.jar gui to run the application using
   graphical user interface. If you want to run using text based interface then just run java -jar stock.jar).
3. After executing the above command following message would be displayed
   a. "Welcome to Stock Application"
   b. "Please select your choice from the menu below"
   c. Once the above messages are displayed the application would display a menu that contains the following options
   d.  1. Create flexible portfolio
       2. View all portfolios
       3. Get composition of portfolio
       4. Get total value of portfolio
       5. Buy shares
       6. Sell shares
       7. Get performance of a portfolio
       8. Create inflexible portfolio
       9. Get cost basis of portfolio by date
       10. Invest fixed amount in a portfolio
       11. Create portfolio using dollar cost averaging
       12. Exit MAIN MENU

4. Please follow the steps below to create an inflexible portfolio with 3 different stocks and another inflexible portfolio with 2 different stocks
   a. Select option 8 to create an inflexible portfolio. A message "Please enter the name of the portfolio" will be displayed. Here, enter the name that you wish to give to the portfolio.
   b. After entering the name, you will see a message "Enter pathname where you want to create a portfolio.". Here, you need to enter the full file path where you want to store the location of the portfolio.
      For example, if you want to store in a hypothetical location like /Users/myname/IdeaProjects/PDP/Group/Stocks Application/resources/myportfolio.txt then you need to enter the full file path as mentioned.
   c. Note that the file format should be .txt only. We do not support any other file format for storing a portfolio.
   d. After the above step, a message "Please enter the date on which you want to create portfolio" is displayed. Enter the date when you want to create the portfolio. The format of the date should be YYYY-MM-DD. We do not accept any other format.
      For example, to enter 02 May 2022 you may enter 2022-05-02. Please do not ignore the leading zeroes.
   e. After the above step portfolio will be successfully created. Note that no file is created during this step. File will be created once you buy shares in a company.
   f. Now to buy shares in 3 different stocks, select option 5 and enter the portfolio name from the dropdown.
   g. After the above step, "Enter the number of stocks you want to buy" message will be displayed. Enter the number of stocks you want to buy. In this case enter 3.
   h. Now enter the date on which you want to buy the first stock, the ticker symbol of the company in which you want to buy, the number of shares that you want to buy and the commission fees for buying the  shares.
   i. After the above step, now repeat step h again to add another stock. Once all stocks have been added main menu will be displayed again.
   j. Repeat the steps from a-i to create another portfolio with 2 different stocks.

5. Please follow the steps below to get the total value of the portfolio that you created in the above steps.
   a. Select option 4 and then select the portfolio for which total value needs to be calculated.
   b. Now after the prompt, enter the till which you want to calculate the total value of the portfolio.The format of the date should be YYYY-MM-DD. We do not accept any other format.
      For example, to enter 02 May 2022 you may enter 2022-05-02. Please do not ignore the leading zeroes.
   c. After the above step total value of the portfolio will be displayed.
6. Please follow the steps below to get the cost basis of the portfolio that you created in the above steps.
   a. Select option 9 and then select the portfolio for which cost basis needs to be calculated.
   b. Now after the prompt, enter the till which you want to calculate the cost basis of the portfolio.The format of the date should be YYYY-MM-DD. We do not accept any other format.
      For example, to enter 02 May 2022 you may enter 2022-05-02. Please do not ignore the leading zeroes.
   c. After the above step cost basis of the portfolio will be displayed.
7. Follow the steps below to invest a fixed amount in the portfolio using weights
   a. Choose option 10 and select the portfolio name
   b. Enter the date on which you wish to invest the fixed amount. Date format should be in yyyy-mm-dd
   c. Now enter the fixed amount that you want to invest
   d. The application supports all tickers from the alphavantage api. These companies have been paginated for readability purpose. Enter a positive number to see how many companies to want to see at once.
   e. Now from the list displayed enter the number of companies in which you wish to invest.
   f. Now the enter company name in which you want to invest and also the percentage of share that you want to invest.After this step you have successfully invested a fixed amount in the portfolio.

8. Follow the below steps to use dollar cost strategy to a portfolio.
   a. Choose option 11 to apply dollar cost strategy.
   b. After the above step it'll ask you to create a flexible portfolio. Enter the name that you wish to give to the portfolio.
   c. Specify the absolute file path where you want this portfolio to be stored.
   d. Specify the date on which you want to create the portfolio.
   e. Now that the portfolio is successfully created, enter start and end date for the strategy in yyyy-mm-dd format.
   f. Now enter the fixed amount that you want to invest
   g. The application supports all tickers from the alphavantage api. These companies have been paginated for readability purpose.
      Enter a positive number to see how many companies to want to see at once.
   h. Now from the list displayed enter the number of companies in which you wish to invest.
   i. Now the enter company name in which you want to invest and also the percentage of share that you want to invest.
   j. Enter the periodic interval as a number which indicates the duration after which the dollar cost strategy will be applied again.
   k. Choose the time unit in from one of SECONDS, MINUTES, HOURS or DAYS. After this step dollar cost strategy will be successfully applied.

9. If the user wants to exit the application then the user may select option 12.


Third party libraries' usage:
1. The application uses jackson library to parse the json data obtained from the Alphavantage API.
2. The JAR file has been compressed and placed inside resources folder.
3. Unzip the jackson zip file the lib folder from the handins server and open it in IntelliJ.
4. Now click on File --> Project Structure --> Libraries.
5. Select the plus icon on the top left and select java and then select each of the jars under lib directory.
6. Now click on Modules and click on the dependencies tab and click on the plus icon on the top add these jars under lib folder by selecting JARs or directories
