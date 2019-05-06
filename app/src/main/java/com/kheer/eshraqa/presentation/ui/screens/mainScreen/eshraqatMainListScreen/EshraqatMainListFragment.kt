package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kheer.eshraqa.R
import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import com.kheer.eshraqa.databinding.EshraqatMainListItemBinding
import com.kheer.eshraqa.presentation.appUtils.LinearLayoutManagerWrapper
import com.kheer.eshraqa.presentation.appUtils.StateView
import com.kheer.eshraqa.presentation.appUtils.shareEshraqa
import com.kheer.eshraqa.presentation.appUtils.toggleFavourite
import com.kheer.eshraqa.presentation.ui.base.BaseFragmentWithInjector
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity.MainActivity
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatItemDetailsScreen.EshraqatItemDetailsFragment
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.eshraqat_main_fragment.*

class EshraqatMainListFragment : BaseFragmentWithInjector() {
  lateinit var mAdapter: BaseListAdapter<EshraqatResponse.Eshraqa>

  lateinit var viewModel: EshraqatMainListViewModel

  companion object {
    const val TAG = "EshraqatMainListFragment"
    const val IS_FAV ="IS_FAV"

    fun newInstance(isFav :Boolean) = EshraqatMainListFragment().apply {
      arguments = Bundle().apply {
        putBoolean(IS_FAV ,isFav)
      }
    }
  }
var isFavScreen :Boolean =false
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = vm as EshraqatMainListViewModel
    isFavScreen = arguments?.getBoolean(IS_FAV,false)?:false

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.eshraqat_main_fragment, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    setUpEshraqatList()
    setUpFetchingObs()
    viewModel.fetchAllEshraqat(isFavScreen)
  }

  override fun onResume() {
    super.onResume()
    (activity as MainActivity).setCurrentFragment(false)
  }

  override fun getActivityVM(): Class<out ViewModel> {
    return EshraqatMainListViewModel::class.java
  }

  override fun isRetained(): Boolean {
    return true
  }

  private fun setUpEshraqatList() {
    mAdapter = BaseListAdapter(object : MainHolderInterface<EshraqatResponse.Eshraqa> {
      override fun getView(type: Int): Int {
        return R.layout.eshraqat_main_list_item
      }

      override fun getListCopy(): ArrayList<EshraqatResponse.Eshraqa>? {
        return viewModel.mainEshraqatList
      }

      override fun getViewData(
        holder: ViewHolder,
        t: EshraqatResponse.Eshraqa,
        position: Int
      ) {
        val itemBinding = DataBindingUtil.bind<EshraqatMainListItemBinding>(holder.itemView)
        itemBinding?.eshraqa = t
        itemBinding?.swipeView?.addToFav?.setOnClickListener {
          t.isFavourite = !t.isFavourite
          viewModel.addToFav(t.id)
          if (isFavScreen){
            mAdapter.originalList.removeAt(position)
            mAdapter.notifyDataSetChanged()
          }else{
            itemBinding.swipeView.addToFav.toggleFavourite(t.isFavourite,true)
          }
        }
        itemBinding?.swipeView?.share?.setOnClickListener {
          shareEshraqa(t.title,t.body)
        }
        itemBinding?.smContentView?.setOnClickListener {
          (activity as MainActivity).supportFragmentManager.beginTransaction()
              .addToBackStack(null)
              .setTransition(TRANSIT_FRAGMENT_CLOSE)
              .replace(
                  R.id.fragmentsContainer,
                  EshraqatItemDetailsFragment.newInstance(position, viewModel.mainEshraqatList)
              )
              .commit()
        }
      }

    }, context!!)

    mainEshraqatList.layoutManager = LinearLayoutManagerWrapper(context)
    mainEshraqatList.adapter = mAdapter
  }

  fun setUpFetchingObs(){
    viewModel.getEshraqat.observe(this, Observer {
      when(it){
        is StateView.Success<*> ->{
          mainEshraqatList.setState(StatesConstants.NORMAL_STATE)
          viewModel.mainEshraqatList =  it.data as ArrayList<EshraqatResponse.Eshraqa>
          mAdapter.originalList =viewModel.mainEshraqatList
          mAdapter.notifyDataSetChanged()
        }
        is StateView.Error ->{

          mainEshraqatList.setState(StatesConstants.ERROR_STATE)
        }
        is StateView.Loading ->{
          mainEshraqatList.setState(StatesConstants.LOADING_STATE)

        }
        is StateView.Empty ->{
          mainEshraqatList.setState(StatesConstants.EMPTY_STATE)

        }
      }
    })
  }

}