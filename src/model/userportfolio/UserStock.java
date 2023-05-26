package model.userportfolio;

import enums.TickerSymbol;

/**
 * The class represents a stock that a user buy or holds.
 */
public class UserStock {

  private final String tickerSymbol;
  private String date;
  private long stocksBought;
  private long stocksSold;
  private long balance;
  private float buyingPrice;
  private float sellingPrice;
  private double costBasis;
  private float commissionFeesForBuying;

  private float commissionFeesForSelling;

  /**
   * The constructor initialises tickerSymbol, stocksBought, stocksSold,
   * balance, buyingPrice, sellingPrice, costBasis, commissionFeesForBuying.
   *
   * @param date                    date
   * @param tickerSymbol            tickerSymbol
   * @param stocksBought            stocksBought
   * @param stocksSold              stocksSold
   * @param balance                 balance
   * @param buyingPrice             buyingPrice
   * @param sellingPrice            sellingPrice
   * @param costBasis               costBasis
   * @param commissionFeesForBuying commissionFeesForBuying
   */
  private UserStock(String date, String tickerSymbol, long stocksBought,
                    long stocksSold, long balance, float buyingPrice,
                    float sellingPrice, float costBasis, float commissionFeesForBuying,
                    float commissionFeesForSelling) {
    this.date = date;
    this.tickerSymbol = tickerSymbol;
    this.stocksBought = stocksBought;
    this.stocksSold = stocksSold;
    this.balance = balance;
    this.buyingPrice = buyingPrice;
    this.sellingPrice = sellingPrice;
    this.costBasis = costBasis;
    this.commissionFeesForBuying = commissionFeesForBuying;
    this.commissionFeesForSelling = commissionFeesForSelling;
  }

  /**
   * Function that initialises UserStockBuilder Object.
   *
   * @return UserStockBuilder Object
   */
  public static UserStockBuilder getUserStockBuilder() {
    return new UserStockBuilder();
  }

  /**
   * Getter to get the date.
   *
   * @return date as a string representation
   */
  public String getDate() {
    return date;
  }

  /**
   * Setter to set the date.
   *
   * @param date date
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * Getter to get the ticker symbol.
   *
   * @return ticker symbol
   */
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  /**
   * Getter to get the stocks bought.
   *
   * @return stocks bought
   */
  public long getStocksBought() {
    return stocksBought;
  }

  /**
   * Setter to set the stocks bought.
   *
   * @param stocksBought stocks bought
   */
  public void setStocksBought(long stocksBought) {
    this.stocksBought = stocksBought;
  }

  /**
   * Getter to get the stocks sold.
   *
   * @return stocks sold
   */
  public long getStocksSold() {
    return stocksSold;
  }

  /**
   * Setter to set the stocks sold.
   *
   * @param stocksSold stocks sold
   */
  public void setStocksSold(long stocksSold) {
    this.stocksSold = stocksSold;
  }

  /**
   * Getter to get the balance.
   *
   * @return balance
   */
  public long getBalance() {
    return balance;
  }

  /**
   * Setter to set the balance.
   *
   * @param balance balance
   */
  public void setBalance(long balance) {
    this.balance = balance;
  }

  /**
   * Getter to get the buying price.
   *
   * @return buying price
   */
  public float getBuyingPrice() {
    return buyingPrice;
  }

  /**
   * Setter to set the buying price.
   *
   * @param buyingPrice buying price
   */
  public void setBuyingPrice(float buyingPrice) {
    this.buyingPrice = buyingPrice;
  }

  /**
   * Getter to get the selling price.
   *
   * @retur selling price
   */
  public float getSellingPrice() {
    return sellingPrice;
  }

  /**
   * Setter to set the selling price.
   *
   * @param sellingPrice selling price
   */
  public void setSellingPrice(float sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  /**
   * Getter to get the cost basis.
   *
   * @return cost basis
   */
  public double getCostBasis() {
    return costBasis;
  }

  /**
   * Setter to set the cost basis.
   *
   * @param costBasis cost basis
   */
  public void setCostBasis(double costBasis) {
    this.costBasis = costBasis;
  }

  /**
   * Getter to get the commission fees.
   *
   * @return commission fees
   */
  public float getCommissionFeesForBuying() {
    return commissionFeesForBuying;
  }

  /**
   * Setter to set the commission fees.
   *
   * @param commissionFeesForBuying commission fees
   */
  public void setCommissionFeesForBuying(float commissionFeesForBuying) {
    this.commissionFeesForBuying = commissionFeesForBuying;
  }

  /**
   * Function the returns hashcode of the object.
   *
   * @return hashcode of the object
   */
  @Override
  public int hashCode() {
    int result = tickerSymbol.hashCode();
    result = 31 * result + (int) (balance ^ (balance >>> 32));
    return result;
  }

  /**
   * Function that compares two stocks object.
   *
   * @param o other stock object
   * @return true if the objects are equal else false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserStockBuilder)) {
      return false;
    }


    UserStockBuilder that = (UserStockBuilder) o;

    if (balance != that.balance) {
      return false;
    }
    return tickerSymbol.equalsIgnoreCase(that.tickerSymbol);
  }

  /**
   * Function that returns string representation of the object.
   *
   * @return string representation of the object
   */
  @Override
  public String toString() {
    String sb = date
            + "~"
            + tickerSymbol
            + "~"
            + stocksBought
            + "~"
            + buyingPrice
            + "~"
            + stocksSold
            + "~"
            + sellingPrice
            + "~"
            + balance
            + "~"
            + commissionFeesForBuying
            + "~"
            + commissionFeesForSelling;
    return sb;
  }

  public float getCommissionFeesForSelling() {
    return commissionFeesForSelling;
  }

  public void setCommissionFeesForSelling(float commissionFeesForSelling) {
    this.commissionFeesForSelling = commissionFeesForSelling;
  }

  /**
   * Builder for building UserStock object.
   */
  public static class UserStockBuilder {

    String date;
    String tickerSymbol;
    long stocksBought;
    long stocksSold;
    long balance;
    float buyingPrice;
    float sellingPrice;
    float costBasis;
    float commissionFeesForBuying;
    float commissionFeesForSelling;

    /**
     * Constructor that initialises the default values for the UserStockBuilder Object.
     */
    private UserStockBuilder() {
      this.date = "";
      this.tickerSymbol = TickerSymbol.DEFAULT;
      this.stocksBought = 0;
      this.stocksSold = 0;
      this.balance = 0;
      this.buyingPrice = 0;
      this.sellingPrice = 0;
      this.costBasis = 0;
      this.commissionFeesForBuying = 0;
      this.commissionFeesForSelling = 0;
    }

    /**
     * Setter to set the date.
     *
     * @param date date
     * @return UserStockBuilder object
     */
    public UserStockBuilder date(String date) {
      this.date = date;
      return this;
    }

    /**
     * Setter to set the ticker symbol.
     *
     * @param tickerSymbol ticker symbol
     * @return UserStockBuilder object
     */
    public UserStockBuilder tickerSymbol(String tickerSymbol) {
      this.tickerSymbol = tickerSymbol;
      return this;

    }

    /**
     * Setter to set the stocks bought.
     *
     * @param stocksBought stocks bought
     * @return UserStockBuilder object
     */
    public UserStockBuilder stockBought(long stocksBought) {
      this.stocksBought = stocksBought;
      return this;
    }

    /**
     * Setter to set the stocks sold.
     *
     * @param stocksSold stocks sold
     * @return UserStockBuilder object
     */
    public UserStockBuilder stocksSold(long stocksSold) {
      this.stocksSold = stocksSold;
      return this;
    }

    /**
     * Setter to set the balance.
     *
     * @param balance balance
     * @return UserStockBuilder object
     */
    public UserStockBuilder balance(long balance) {
      this.balance = balance;
      return this;
    }

    /**
     * Setter to set the buying price.
     *
     * @param buyingPrice buying price
     * @return UserStockBuilder object
     */
    public UserStockBuilder buyingPrice(float buyingPrice) {
      this.buyingPrice = buyingPrice;
      return this;
    }

    /**
     * Setter to set the selling price.
     *
     * @param sellingPrice selling price
     * @return UserStockBuilder object
     */
    public UserStockBuilder sellingPrice(float sellingPrice) {
      this.sellingPrice = sellingPrice;
      return this;
    }

    /**
     * Setter to set the cost basis.
     *
     * @param costBasis cost basis
     * @return UserStockBuilder object
     */
    public UserStockBuilder costBasis(float costBasis) {
      this.costBasis = costBasis;
      return this;
    }

    /**
     * Setter to set the commission fees for buying.
     *
     * @param commissionFees commission fees
     * @return UserStockBuilder object
     */
    public UserStockBuilder commissionFeesForBuying(float commissionFees) {
      this.commissionFeesForBuying = commissionFees;
      return this;
    }

    /**
     * Setter to set the commission fees.
     *
     * @param commissionFees commission fees
     * @return UserStockBuilder object
     */
    public UserStockBuilder commissionFeesForSelling(float commissionFees) {
      this.commissionFeesForSelling = commissionFees;
      return this;
    }

    /**
     * Function that builds the UserStock object.
     *
     * @return UserStock object
     */
    public UserStock build() {
      return new UserStock(date, tickerSymbol, stocksBought, stocksSold, balance,
              buyingPrice, sellingPrice, costBasis, commissionFeesForBuying,
              commissionFeesForSelling);
    }

  }
}
