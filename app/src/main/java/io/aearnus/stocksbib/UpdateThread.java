package io.aearnus.stocksbib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tyler on 7/19/17.
 */

public class UpdateThread extends Thread {
    GameData gameData;
    BuySellFragment buySellFragment;
    SurfaceHolder graphHolder;
    boolean doTick;

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

    private void updateGame() {
        gameData.updateTick();
    }

    private void drawGame() {
        if (graphHolder != null) {
            Canvas canvas = graphHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawRGB(255, 255, 255);
                String textToDraw =
                        "StockValue: " + gameData.stockValue + "\n " +
                        "Money: " + gameData.money + "\n " +
                        "Stocks: " + gameData.stocks;
                canvas.drawText(textToDraw, 10f, 200f, paint);
                graphHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void run() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(32);
        while (doTick) {
            updateGame();
            drawGame();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
