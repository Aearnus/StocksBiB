package io.aearnus.stocksbib;

import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by tyler on 7/20/17.
 */

public class GraphCallback implements SurfaceHolder.Callback {
    UpdateThread updateThread;
    public GraphCallback(UpdateThread inUpdateThread) {
        updateThread = inUpdateThread;
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        updateThread.setGraphHolder(surfaceHolder);
        Log.i("GRAPHCALLBACK", "Created Graph Surface");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i("GRAPHCALLBACK", "Changed Graph Surface");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        updateThread.setGraphHolder(null);
        Log.i("GRAPHCALLBACK", "Destroyed Graph Surface");
    }
}
