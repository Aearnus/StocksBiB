package io.aearnus.stocksbib;

import android.util.Log;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by tyler on 7/16/17.
 * Holds all the various information about the game. Stocks, stock prices, currency, etc.
 */

public class GameData implements Serializable {
    // inner stuff
    private Random randomGenerator;
    // game values
    public double money;
    public long stocks;
    // stock value runs on an exponential scale
    public double stockValueArgumentChange; // -1.0 to 1.0
    public double stockValueArgument; // -infinity to infinity
    // final value is being passed through a bounding function
    public double stockValue; // 0 to infinity
    public GameData() {
        randomGenerator = new Random();

        money = 100.0;
        stocks = 0;

        stockValueArgumentChange = 0.0;
        stockValueArgument = 0.0;
        stockValue = 10.0;
    }

    private double stockValueBounding(double inStockValueArgument) {
        //return Math.atan(inStockValueArgument) + (Math.PI / 2);
        return Math.abs(inStockValueArgument);
    }
    /**
     * updateTick() is meant to be called every tick
     * It updates things that happen constantly, like stock price changing
     */
    public void updateTick() {
        // every tick there's a 1/128 change to change the derivative
        if (randomGenerator.nextInt(128) == 1) {
            stockValueArgumentChange = (0.25 * randomGenerator.nextDouble()) - 0.125;
        }
        // euler's integration of argument, with a small noise factor
        stockValueArgument += stockValueArgumentChange + (randomGenerator.nextDouble() * 0.5) - 0.25;
        stockValue = stockValueBounding(stockValueArgument);
    }

    // OK if more transactions can be made
    // DISABLE if no further transactions can be made
    // FAIL if the transaction failed (for some god awful reason -- this should throw an error)
    enum StockTransactionSuccess {
        OK,
        DISABLE,
        FAIL
    }
    public StockTransactionSuccess buyStock() {
        if (money > stockValue) {
            money -= stockValue;
            stocks++;
            if (money < stockValue) {
                return StockTransactionSuccess.DISABLE;
            }
            return StockTransactionSuccess.OK;
        }
        return StockTransactionSuccess.FAIL;
    }
    public StockTransactionSuccess sellStock() {
        if (stocks > 0) {
            money += stockValue;
            stocks--;
            if (stocks < 1) {
                return StockTransactionSuccess.DISABLE;
            }
            return StockTransactionSuccess.OK;
        }
        return StockTransactionSuccess.FAIL;
    }

    /**
     * This was all from GameDataHandler
     */
    public void saveGame() {
        Log.i("GameDataHandler", "Gamedata is " + this);
    }

    public boolean loadGame() {
        return true;
    }

    public void resetGame() {

    }
}
