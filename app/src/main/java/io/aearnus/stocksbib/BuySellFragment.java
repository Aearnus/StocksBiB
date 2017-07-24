package io.aearnus.stocksbib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tyler on 7/17/17.
 */

public class BuySellFragment extends Fragment {
    SurfaceView stockGraph;
    UpdateThread updateThread;
    GraphCallback graphCallback;
    Button buyButton;
    Button sellButton;
    boolean isGraphHooked = false;
    public void setUpdateThread(UpdateThread inUpdateThread) {
        updateThread = inUpdateThread;
        hookCallbacks();
    }
    private void hookCallbacks() {
        // creates the GraphCallback item if and only if it doesn't exist
        if (graphCallback == null) {
            graphCallback = new GraphCallback(updateThread);
        }
        // this method ensures that it only runs once
        // but, it has to run when stockGraph and updateThread are populated
        if (stockGraph != null && updateThread != null && !isGraphHooked) {
            stockGraph.getHolder().addCallback(graphCallback);
            isGraphHooked = true;
            updateThread.setBuySellButtons(buyButton, sellButton);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_buy_sell, container, false);
        // attach the SurfaceHolder.Callback to the stock graph to draw to it
        stockGraph = view.findViewById(R.id.stockGraph);
        buyButton = view.findViewById(R.id.buyButton);
        sellButton = view.findViewById(R.id.sellButton);
        hookCallbacks();
        Log.i("BUYSELLFRAGMENT", "Fragment created");
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isGraphHooked = false;
        Log.i("BUYSELLFRAGMENT", "Fragment destroyed");
    }
}
