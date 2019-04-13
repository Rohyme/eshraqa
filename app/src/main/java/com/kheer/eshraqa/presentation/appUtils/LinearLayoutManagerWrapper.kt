package com.kheer.eshraqa.presentation.appUtils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.State
import android.util.AttributeSet
import android.util.Log

class LinearLayoutManagerWrapper : androidx.recyclerview.widget.LinearLayoutManager {
  constructor(context: Context?) : super(context)
  constructor(
    context: Context?,
    orientation: Int,
    reverseLayout: Boolean
  ) : super(context, orientation, reverseLayout)

  constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
  ) : super(context, attrs, defStyleAttr, defStyleRes)

  override fun onLayoutChildren(
    recycler: Recycler?,
    state: State?
  ) {
    try {
      super.onLayoutChildren(recycler, state)
    } catch (e: IndexOutOfBoundsException) {
      Log.e("probe", "meet a IOOBE in RecyclerView")

    }
  }
}