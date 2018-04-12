package timer.fity.me.beginner.adapters.tools;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Hovhannisyan.Karo on 25.08.2017.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration() {
        this.space = 8;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = 16;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = 16;

    }
}