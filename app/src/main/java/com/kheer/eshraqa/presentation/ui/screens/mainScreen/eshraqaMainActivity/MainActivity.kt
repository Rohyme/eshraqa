package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.lifecycle.ViewModel
import com.kheer.eshraqa.R
import com.kheer.eshraqa.presentation.ui.base.BaseActivityWithInjector
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen.EshraqatMainListFragment
import kotlinx.android.synthetic.main.app_toolbar.*


class MainActivity : BaseActivityWithInjector(), AdapterView.OnItemSelectedListener {

    val titles = arrayOf("اشراقات", "المفضلة")

    lateinit var viewModel: MainActivityViewModel
    override fun getActivityVM(): Class<out ViewModel> {
        return MainActivityViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = vm as MainActivityViewModel

        setUpTitleSpinner()
    }

    private fun setUpTitleSpinner() {
        ArrayAdapter(
                this,
                R.layout.spinner_item,
                titles
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            titleSpinner.adapter = adapter
        }
        titleSpinner.setSelection(0)
        titleSpinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0) {
            openEshraqatList(false)
        } else {
            openEshraqatList(true)
        }
    }

    fun openEshraqatList(isFavourite: Boolean) {
        val eshraqatMainListFragment = EshraqatMainListFragment.newInstance(isFavourite)
        val trans = supportFragmentManager.beginTransaction()
        trans.setTransition(TRANSIT_FRAGMENT_OPEN)
        trans.addToBackStack(null).replace(R.id.fragmentsContainer,eshraqatMainListFragment,EshraqatMainListFragment.TAG).commitAllowingStateLoss()


    }

    fun setCurrentFragment(isDetails: Boolean) {
        if (isDetails) {
            barContainer.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.goldColor))
            next.visibility=View.VISIBLE
            previous.visibility=View.VISIBLE
        } else {
            barContainer.setBackgroundColor(ContextCompat.getColor(this@MainActivity,android.R.color.transparent))
            next.visibility=View.GONE
            previous.visibility=View.GONE
        }
    }

    override fun onBackPressed() {
        val fr = supportFragmentManager.findFragmentById(R.id.fragmentsContainer)
        if (fr is EshraqatMainListFragment){
            finish()
        }else{
            super.onBackPressed()
        }
    }

    fun arrowsListeners(onNextClicked : () ->Unit,onPreviousClicked :()->Unit) {
        next.setOnClickListener {
            onNextClicked()
        }

        previous.setOnClickListener {
            onPreviousClicked()
        }
    }


}
