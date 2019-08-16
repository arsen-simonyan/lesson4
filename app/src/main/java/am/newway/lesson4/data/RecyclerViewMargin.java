package am.newway.lesson4.data;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewMargin extends RecyclerView.ItemDecoration
{
    private int margin;

    public RecyclerViewMargin( @IntRange(from=0)int margin ) {
        this.margin = margin;

    }

    @Override
    public void getItemOffsets( Rect outRect, View view,
                                RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        outRect.right = margin;
        outRect.left = margin;
        outRect.bottom = margin;

        int nItemCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();
        if(1 <= nItemCount && position == (nItemCount - 1)){
            outRect.bottom = margin*8;
        }
    }
}
