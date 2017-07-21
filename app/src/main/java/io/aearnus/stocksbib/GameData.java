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

        money = 0.0;
        stocks = 0;

        stockValueArgumentChange = 0.0;
        stockValueArgument = 0.0;
        stockValue = 10.0;
    }

    private double stockValueBounding(double inStockValueArgument) {
        return Math.atan(inStockValueArgument) + (Math.PI / 2);
    }
    /**
     * updateTick() is meant to be called every tick
     * It updates things that happen constantly, like stock price changing
     */
    public void updateTick() {
        // every tick there's a 1/1024 change to change the derivative
        if (randomGenerator.nextInt(1024) == 1) {
            stockValueArgumentChange = (2 * randomGenerator.nextDouble()) - 1;
        }
        // euler's integration of argument, with a small noise factor
        stockValueArgument += stockValueArgumentChange + (randomGenerator.nextDouble() * 0.25) - 0.125;
        stockValue = stockValueBounding(stockValueArgument);
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
