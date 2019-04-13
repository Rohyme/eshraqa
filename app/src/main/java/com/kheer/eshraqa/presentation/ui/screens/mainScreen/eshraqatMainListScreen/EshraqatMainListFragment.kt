package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen

import android.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kheer.eshraqa.R
import com.kheer.eshraqa.R.layout
import com.kheer.eshraqa.presentation.appUtils.LinearLayoutManagerWrapper
import com.kheer.eshraqa.databinding.EshraqatMainListItemBinding
import com.kheer.eshraqa.presentation.ui.base.BaseFragmentWithInjector
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity.MainActivity
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatItemDetailsScreen.EshraqatItemDetailsFragment
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import kotlinx.android.synthetic.main.eshraqat_main_fragment.mainEshraqatList

class EshraqatMainListFragment : BaseFragmentWithInjector() {
  lateinit var mAdapter: BaseListAdapter<String>

  lateinit var viewModel: EshraqatMainListViewModel

  companion object {
    const val TAG = "EshraqatMainListFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = vm as EshraqatMainListViewModel
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
  }

  override fun onResume() {
    super.onResume()
    viewModel.setList()
  }

  override fun getActivityVM(): Class<out ViewModel> {
    return EshraqatMainListViewModel::class.java
  }

  override fun isRetained(): Boolean {
    return true
  }

  private fun setUpEshraqatList() {
    mAdapter = BaseListAdapter(object : MainHolderInterface<String> {
      override fun getView(type: Int): Int {
        return layout.eshraqat_main_list_item
      }

      override fun getListCopy(): ArrayList<String>? {
        return viewModel.mainEshraqatList
      }

      override fun getViewData(
        holder: ViewHolder,
        t: String,
        position: Int
      ) {
        val itemBinding = DataBindingUtil.bind<EshraqatMainListItemBinding>(holder.itemView)
        itemBinding?.eshraqaText2?.text = t
        itemBinding?.testSwipeable?.setOnClickListener {
          Toast.makeText(context, "Item number $position", Toast.LENGTH_SHORT)
              .show()
        }
        itemBinding?.smContentView?.setOnClickListener {
          (activity as MainActivity).supportFragmentManager.beginTransaction()
              .addToBackStack(null)
              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
              .replace(
                  R.id.fragmentsContainer,
                  EshraqatItemDetailsFragment.newInstance(position, viewModel.mainEshraqatList)
              )
              .commit()
        }
      }

    }, context!!)

    mainEshraqatList.layoutManager = LinearLayoutManagerWrapper(context)
    mAdapter.originalList = viewModel.mainEshraqatList
    mainEshraqatList.adapter = mAdapter
  }
}