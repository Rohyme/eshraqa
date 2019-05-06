package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatItemDetailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kheer.eshraqa.R
import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import com.kheer.eshraqa.databinding.EshraqatDetailsListItemBinding
import com.kheer.eshraqa.presentation.appUtils.LinearLayoutManagerWrapper
import com.kheer.eshraqa.presentation.appUtils.shareEshraqa
import com.kheer.eshraqa.presentation.appUtils.toggleFavourite
import com.kheer.eshraqa.presentation.ui.base.BaseFragmentWithInjector
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity.MainActivity
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import kotlinx.android.synthetic.main.eshraqat_details_fragment.*
import kotlinx.android.synthetic.main.eshraqat_details_list_item.*
import java.util.*

class EshraqatItemDetailsFragment :BaseFragmentWithInjector(){
  lateinit var viewModel :EshraqatItemDetailsViewModel
  lateinit var mAdapter: BaseListAdapter<EshraqatResponse.Eshraqa>

  companion object {
    const val TAG="EshraqatItemDetailsFragment"
    const val ESHRAQA_ITEM_POSITION = "ESHRAQA_ITEM_POSITION"
    const val ESHRAQAT_LIST = "ESHRAQAT_LIST"
    fun newInstance(
            currentItem: Int,
            eshraqatList: ArrayList<EshraqatResponse.Eshraqa>
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
    currentPosition = currentItem?:0
    viewModel.mainEshraqatList = arguments?.getSerializable(ESHRAQAT_LIST) as ArrayList<EshraqatResponse.Eshraqa>
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
    mAdapter = BaseListAdapter(object : MainHolderInterface<EshraqatResponse.Eshraqa> {
      override fun getView(type: Int): Int {
        return R.layout.eshraqat_details_list_item
      }

      override fun getViewData(
              holder: ViewHolder,
              t: EshraqatResponse.Eshraqa,
              position: Int
      ) {
        val itemBinding = DataBindingUtil.bind<EshraqatDetailsListItemBinding>(holder.itemView)
        itemBinding?.model = t
        itemBinding?.addToFav?.setOnClickListener {
          t.isFavourite = !t.isFavourite
          viewModel.addToFav(t.id)
          itemBinding.addToFav.toggleFavourite(t.isFavourite,false)
        }
        itemBinding?.share?.setOnClickListener {
          shareEshraqa(t.title,t.body)
        }
      }

    },context!!)
    val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
    snapHelper.attachToRecyclerView(eshraqatList)
    val layoutMan = LinearLayoutManagerWrapper(context, RecyclerView.HORIZONTAL, false)
    eshraqatList.layoutManager = layoutMan
    mAdapter.originalList =viewModel.mainEshraqatList
    eshraqatList.adapter =mAdapter
    eshraqatList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          currentPosition = layoutMan.findFirstCompletelyVisibleItemPosition()
        }
      }
    })
    eshraqatList.scrollToPosition(currentItem!!)
  }

  var currentPosition: Int = 0
  override fun isRetained(): Boolean {
    return false
  }

  override fun onResume() {
    super.onResume()
    (activity as MainActivity).setCurrentFragment(true)
    (activity as MainActivity).arrowsListeners({
      if (currentPosition -1 >= 0){
        eshraqatList.smoothScrollToPosition(currentPosition-1)
      }
    }, {
      if (currentPosition +1 < mAdapter.itemCount){
        eshraqatList.smoothScrollToPosition(currentPosition+1)
      }
    })
  }

}