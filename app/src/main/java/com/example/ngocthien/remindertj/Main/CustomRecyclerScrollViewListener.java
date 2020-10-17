package com.example.ngocthien.remindertj.Main;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class CustomRecyclerScrollViewListener extends RecyclerView.OnScrollListener {
    int scrollDist = 0;
    boolean isVisible = true;
    static final float MINIMUM = 20;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        if (isVisible && scrollDist > MINIMUM) {
            Log.d("NgocThien", "Hide " + scrollDist);
            hide();
            scrollDist = 0;
            isVisible = false;
        } else if (!isVisible && scrollDist < -MINIMUM) {
            Log.d("NgocThien", "Show " + scrollDist);
            show();
            scrollDist = 0;
            isVisible = true;
        }
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            Log.d("NgocThien", "Add Up " + scrollDist);
            scrollDist += dy;
        }
    }

    public abstract void show();

    public abstract void hide();
}
