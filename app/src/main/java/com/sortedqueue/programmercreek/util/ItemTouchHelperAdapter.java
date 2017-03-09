package com.sortedqueue.programmercreek.util;

/**
 * Created by Alok Omkar on 2017-03-09.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
