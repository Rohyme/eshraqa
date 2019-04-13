package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatItemDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kheer.eshraqa.R
import com.kheer.eshraqa.R.layout
import com.kheer.eshraqa.presentation.appUtils.LinearLayoutManagerWrapper
import com.kheer.eshraqa.databinding.EshraqatDetailsListItemBinding
import com.kheer.eshraqa.presentation.ui.base.BaseFragmentWithInjector
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import kotlinx.android.synthetic.main.eshraqat_details_fragment.eshraqatList
import java.util.ArrayList

class EshraqatItemDetailsFragment :BaseFragmentWithInjector(){
  lateinit var viewModel :EshraqatItemDetailsViewModel
  lateinit var mAdapter :BaseListAdapter<String>

  companion object {
    const val TAG="EshraqatItemDetailsFragment"
    const val ESHRAQA_ITEM_POSITION = "ESHRAQA_ITEM_POSITION"
    const val ESHRAQAT_LIST = "ESHRAQAT_LIST"
    fun newInstance(
      currentItem: Int,
      eshraqatList: ArrayList<String>
    ) =
      EshraqatItemDetailsFragment().apply {
        arguments = Bundle().apply {
          putInt(ESHRAQA_ITEM_POSITION, currentItem)
          putSerializable(ESHRAQAT_LIST, eshraqatList)
        }
      }

  }
  override fun getActivityVM(): Class<out ViewModel> {
    return EshraqatItemDetailsViewModel::class.java
  }

  private var currentItem: Int?=null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel =  vm as EshraqatItemDetailsViewModel

    currentItem = arguments?.getInt(ESHRAQA_ITEM_POSITION, 0)
    viewModel.mainEshraqatList = arguments?.getSerializable(ESHRAQAT_LIST) as ArrayList<String>
  }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.eshraqat_details_fragment,container,false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    setUpEshraqatList()
  }



  private fun setUpEshraqatList() {
    mAdapter= BaseListAdapter(object : MainHolderInterface<String> {
      override fun getView(type: Int): Int {
        return  layout.eshraqat_details_list_item
      }

      override fun getViewData(
        holder: ViewHolder,
        t: String,
        position: Int
      ) {
        val itemBinding = DataBindingUtil.bind<EshraqatDetailsListItemBinding>(holder.itemView)
        itemBinding?.eshraqaText?.text = t
      }

    },context!!)
    val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
    snapHelper.attachToRecyclerView(eshraqatList)
    eshraqatList.layoutManager = LinearLayoutManagerWrapper(context, androidx.recyclerview.widget.RecyclerView.HORIZONTAL,false)
    mAdapter.originalList =viewModel.mainEshraqatList
    eshraqatList.adapter =mAdapter
    eshraqatList.scrollToPosition(currentItem!!)
  }
  override fun isRetained(): Boolean {
    return false
  }
}