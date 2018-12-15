package com.sortedqueue.programmercreek.util

import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.Gravity
import android.view.View

/**
 * Created by Alok on 10/08/17.
 */

class GravitySnapHelper(private val gravity: Int) : SnapHelper() {
    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null

    init {
        if (gravity != Gravity.START && gravity != Gravity.END && gravity != Gravity.BOTTOM && gravity != Gravity.TOP) {
            throw IllegalArgumentException("Invalid gravity value. Use START " + "| END | BOTTOM | TOP constants")
        }
    }

    /***
     * Well composition does not work so we copy this method from [LinearSnapHelper].
     * That sucks!

     * @param layoutManager
     * *
     * @param velocityX
     * *
     * @param velocityY
     * *
     * @return
     */
    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val currentPosition = layoutManager.getPosition(currentView)
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val vectorProvider = layoutManager as RecyclerView.SmoothScroller.ScrollVectorProvider
        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        val vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1) ?: // cannot get a vector for the given position.
                return RecyclerView.NO_POSITION

        var vDeltaJump: Int
        var hDeltaJump: Int
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getHorizontalHelper(layoutManager), velocityX, 0)
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump
            }
        } else {
            hDeltaJump = 0
        }
        if (layoutManager.canScrollVertically()) {
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getVerticalHelper(layoutManager), 0, velocityY)
            if (vectorForEnd.y < 0) {
                vDeltaJump = -vDeltaJump
            }
        } else {
            vDeltaJump = 0
        }

        val deltaJump = if (layoutManager.canScrollVertically()) vDeltaJump else hDeltaJump
        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION
        }

        var targetPos = currentPosition + deltaJump
        if (targetPos < 0) {
            targetPos = 0
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1
        }
        return targetPos
    }

    /**
     * Override this method to snap to a particular point within the target view or the container
     * view on any axis.
     *
     *
     * This method is called when the [SnapHelper] has intercepted a fling and it needs
     * to know the exact distance required to scroll by in order to snap to the target view.

     * @param layoutManager the [RecyclerView.LayoutManager] associated with the attached
     * *                      [RecyclerView]
     * *
     * @param targetView the target view that is chosen as the view to snap
     * *
     * *
     * @return the output coordinates the put the result into. out[0] is the distance
     * * on horizontal axis and out[1] is the distance on vertical axis.
     */
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val out = IntArray(2)
        out[0] = if (layoutManager.canScrollHorizontally()) getXCoordinateToSnapPos(layoutManager, targetView) else 0
        out[1] = if (layoutManager.canScrollVertically()) getYCoordinateToSnapPos(layoutManager, targetView) else 0
        return out
    }

    /**
     * Override this method to provide a particular target view for snapping.
     *
     *
     * This method is called when the [SnapHelper] is ready to start snapping and requires
     * a target view to snap to. It will be explicitly called when the scroll state becomes idle
     * after a scroll. It will also be called when the [SnapHelper] is preparing to snap
     * after a fling and requires a reference view from the current set of child views.
     *
     *
     * If this method returns `null`, SnapHelper will not snap to any view.

     * @param layoutManager the [RecyclerView.LayoutManager] associated with the attached
     * *                      [RecyclerView]
     * *
     * *
     * @return the target view to which to snap on fling or end of scroll
     */
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        var targetView: View? = null
        if (layoutManager is LinearLayoutManager) {
            when (gravity) {
                Gravity.START -> targetView = findStartView(layoutManager, getHorizontalHelper(layoutManager))
                Gravity.END -> targetView = findEndView(layoutManager, getHorizontalHelper(layoutManager))
                Gravity.TOP -> targetView = findStartView(layoutManager, getVerticalHelper(layoutManager))
                Gravity.BOTTOM -> targetView = findEndView(layoutManager, getVerticalHelper(layoutManager))
            }
        }
        return targetView
    }

    //<editor-fold desc="Helpers">

    //<editor-fold desc="Coordinate Helpers">
    private fun getXCoordinateToSnapPos(layoutManager: RecyclerView.LayoutManager, targetView: View): Int {
        val horizontalHelper = getHorizontalHelper(layoutManager)

        return if (gravity == Gravity.START)
            horizontalHelper.getDecoratedStart(targetView) - horizontalHelper.startAfterPadding
        else
            horizontalHelper.getDecoratedEnd(targetView) - horizontalHelper.endAfterPadding //END
    }

    private fun getYCoordinateToSnapPos(layoutManager: RecyclerView.LayoutManager, targetView: View): Int {
        val verticalHelper = getVerticalHelper(layoutManager)

        return if (gravity == Gravity.TOP)
            verticalHelper.getDecoratedStart(targetView) - verticalHelper.startAfterPadding
        else
            verticalHelper.getDecoratedEnd(targetView) - verticalHelper.endAfterPadding //BOTTOM
    }
    //</editor-fold>

    //<editor-fold desc="Lazy Inject OrientationHelpers">
    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper!!
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }
    //</editor-fold>

    //<editor-fold desc="View finders">
    /**
     * Searches for the last visible item in the adapter.
     * Checks if it is visible enough (more than half) and returns the view.
     * If the view was not visible enough it will return the previous view in line.

     * If we are at the end of the list we return null so no snapping occurs

     * @param layoutManager
     * *
     * @param orientationHelper
     * *
     * @return the view to snap to
     */
    private fun findEndView(layoutManager: RecyclerView.LayoutManager, orientationHelper: OrientationHelper): View? {
        var targetView: View? = null
        if (layoutManager is LinearLayoutManager) {
            val lastChildPos = layoutManager.findLastVisibleItemPosition()
            if (lastChildPos != RecyclerView.NO_POSITION) {
                val lastView = layoutManager.findViewByPosition(lastChildPos)
                val visibleWidth = orientationHelper.totalSpace.toFloat() - orientationHelper.getDecoratedStart(lastView) / orientationHelper.getDecoratedMeasurement(lastView)

                if (visibleWidth > VIEW_HALF_VISIBLE) {
                    targetView = lastView
                } else {
                    // If we're at the start of the list, we shouldn't snap
                    // to avoid having the first item not completely visible.
                    val startOfList = layoutManager.findFirstCompletelyVisibleItemPosition() == 0
                    if (!startOfList) {
                        targetView = layoutManager.findViewByPosition(lastChildPos - 1)
                    }
                }
            }
        }
        return targetView
    }

    /**
     * Searches for the first visible item in the adapter.
     * Checks if it is visible enough (more than half) and returns the view.
     * If the view was not visible enough it will return the next view in line.

     * If we are at the end of the list we return null so no snapping occurs

     * @param layoutManager
     * *
     * @param orientationHelper
     * *
     * @return the view to snap to
     */
    private fun findStartView(layoutManager: RecyclerView.LayoutManager, orientationHelper: OrientationHelper): View? {
        var targetView: View? = null
        if (layoutManager is LinearLayoutManager) {
            val firstChildPos = layoutManager.findFirstVisibleItemPosition()
            if (firstChildPos != RecyclerView.NO_POSITION) {
                val firstView = layoutManager.findViewByPosition(firstChildPos)
                val visibleWidth = orientationHelper.getDecoratedEnd(firstView).toFloat() / orientationHelper.getDecoratedMeasurement(firstView)

                if (visibleWidth > VIEW_HALF_VISIBLE) {
                    targetView = firstView
                } else {
                    // If we're at the end of the list, we shouldn't snap
                    // to avoid having the last item not completely visible.
                    val endOfList = layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1
                    if (!endOfList) {
                        // If the firstView wasn't returned, we need to return
                        // the next view closest to the start.
                        targetView = layoutManager.findViewByPosition(firstChildPos + 1)
                    }
                }
            }
        }
        return targetView
    }
    //</editor-fold>

    //<editor-fold desc="Copied from LinearSnapHelper">
    private fun estimateNextPositionDiffForFling(layoutManager: RecyclerView.LayoutManager,
                                                 helper: OrientationHelper, velocityX: Int, velocityY: Int): Int {
        val distances = calculateScrollDistance(velocityX, velocityY)
        val distancePerChild = computeDistancePerChild(layoutManager, helper)
        if (distancePerChild <= 0) {
            return 0
        }
        val distance = if (Math.abs(distances[0]) > Math.abs(distances[1])) distances[0] else distances[1]

        if (Math.abs(distance) < distancePerChild / 2f) {
            return 0
        }

        return Math.floor((distance / distancePerChild).toDouble()).toInt()
    }

    private fun computeDistancePerChild(layoutManager: RecyclerView.LayoutManager,
                                        helper: OrientationHelper): Float {
        var minPosView: View? = null
        var maxPosView: View? = null
        var minPos = Integer.MAX_VALUE
        var maxPos = Integer.MIN_VALUE
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return INVALID_DISTANCE
        }

        for (i in 0..childCount - 1) {
            val child = layoutManager.getChildAt(i)
            val pos = layoutManager.getPosition(child!!)
            if (pos == RecyclerView.NO_POSITION) {
                continue
            }
            if (pos < minPos) {
                minPos = pos
                minPosView = child
            }
            if (pos > maxPos) {
                maxPos = pos
                maxPosView = child
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE
        }
        val start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView))
        val end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView))
        val distance = end - start
        if (distance == 0) {
            return INVALID_DISTANCE
        }
        return 1f * distance / (maxPos - minPos + 1)
    }

    companion object {

        private val INVALID_DISTANCE = 1f
        private val VIEW_HALF_VISIBLE = 0.5f
    }
    //</editor-fold>

    //</editor-fold>
}
