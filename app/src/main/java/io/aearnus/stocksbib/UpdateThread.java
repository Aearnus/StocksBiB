package io.aearnus.stocksbib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by tyler on 7/19/17.
 * Handles the ticking of the game, the updating of the shared GameData, and the drawing of various
 * specialized surfaces throughout the app.
 */

public class UpdateThread extends Thread {
    GameData gameData;
    boolean doTick;

    BuySellFragment buySellFragment;
    SurfaceHolder graphHolder;
    LinkedList<Double> graphHistory;

    // drawing stuff
    Paint paint;
    public UpdateThread(GameData inGameData, Fragment inBuySellFragment) {
        gameData = inGameData;
        buySellFragment = (BuySellFragment) inBuySellFragment;
        buySellFragment.setUpdateThread(this);
        doTick = true;
    }

    public void setGraphHolder(SurfaceHolder inGraphHolder) {
        graphHolder = inGraphHolder;
    }

    private float scaleYPoint(double p, double graphMax, int canvasHeight) {
        return (float)(canvasHeight - ((p / graphMax) * canvasHeight));
    }

    private void drawGraph() {
        if (graphHolder != null) {
            Canvas canvas = graphHolder.lockCanvas();
            int canvasHeight = canvas.getHeight();
            int canvasWidth = canvas.getWidth();
            if (canvas != null) {
                canvas.drawRGB(255, 255, 255);
                // find the nearest 10 to bound the graph
                // need to iterate through (almost) all values in order to make graph nice looking
                double graphMax;
                if (graphHistory.size() < 2) {
                    graphMax  = Math.ceil(gameData.stockValue / 10) * 10;
                } else {
                    graphMax = Math.ceil(Collections.max(graphHistory) / 10) * 10;
                }
                String graphMaxString = "$" + graphMax;
                paint.setColor(Color.BLACK);
                canvas.drawText("StockValue: " + gameData.stockValue + "\n " +  "Money: " + gameData.money + "\n " +  "Stocks: " + gameData.stocks, 10f, 200f, paint);
                Rect textBoundingRect = new Rect();
                paint.getTextBounds(graphMaxString, 0, graphMaxString.length(), textBoundingRect);
                canvas.drawText("$" + graphMax, 0, textBoundingRect.height(), paint);
                // note down the graph history and then draw it
                graphHistory.add(0, gameData.stockValue);
                if (graphHistory.size() >= 2048) {
                    graphHistory.remove(2047);
                }
                int graphPointIndex = 0;
                for(double graphPoint : graphHistory) {
                    if (graphPointIndex >= graphHistory.size() - 2) {
                        // avoid accessing a nonexistent point
                        break;
                    }
                    float startX = canvasWidth - graphPointIndex;
                    float startY = scaleYPoint(graphPoint, graphMax, canvasHeight);
                    float endX = startX - 1;
                    float endY = scaleYPoint(graphHistory.get(graphPointIndex + 1), graphMax, canvasHeight);
                    float rectSize = 20;
                    float rectSizeHalf = rectSize / 2;
                    paint.setColor(Color.RED);
                    canvas.drawRect(startX - rectSizeHalf, startY - rectSizeHalf, startX + rectSizeHalf, startY + rectSizeHalf, paint);
                    graphPointIndex++;
                }
                graphHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void updateGame() {
        gameData.updateTick();
    }

    private void drawGame() {
        drawGraph();
    }

    @Override
    public void run() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(32);

        graphHistory = new LinkedList<>();
        while (doTick) {
            updateGame();
            drawGame();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
