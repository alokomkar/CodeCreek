package com.sortedqueue.programmercreek.v2.base

import android.support.design.widget.BottomSheetDialogFragment
import android.widget.Toast

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {
    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}