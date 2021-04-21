package com.thallesmarchetti.eshop.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.thallesmarchetti.eshop.Firebase.firebaseClass

import com.thallesmarchetti.eshop.R
import com.thallesmarchetti.eshop.Utils.Constant
import com.thallesmarchetti.eshop.adapters.DashboardAdapter
import com.thallesmarchetti.eshop.models.product
import com.thallesmarchetti.eshop.ui.activites.MycartActivity
import com.thallesmarchetti.eshop.ui.activites.ProductDetailsActivity
import com.thallesmarchetti.eshop.ui.activites.SettingActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {

    var menuItem = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

          setHasOptionsMenu(true)

    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  dashboardViewModel =ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onResume() {
        getproductsdetails()

        super.onResume()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){

            R.id.setting ->{

                startActivity(Intent(activity, SettingActivity::class.java))

                return true
            }

            R.id.dashboard_cart ->{

                startActivity(Intent(activity, MycartActivity::class.java))

            }
        }

        return super.onOptionsItemSelected(item)
    }


    fun loadAllproductsdetailssuccess(products: ArrayList<product>){

        var list = true

        if (products.size > 0){

            dashboard_recycle.visibility = View.VISIBLE
            text_dashboard.visibility = View.GONE

            val adapter = DashboardAdapter(requireActivity(), products)

            dashboard_recycle.layoutManager = LinearLayoutManager(requireActivity())
            dashboard_recycle.setHasFixedSize(true)
            dashboard_recycle.adapter = adapter

            flc_button.setOnClickListener {
              if (list){

                  dashboard_recycle.layoutManager = GridLayoutManager(requireActivity(),2)
                  dashboard_recycle.setHasFixedSize(true)
                  dashboard_recycle.adapter = adapter

                  flc_button.setImageDrawable(
                     ContextCompat.getDrawable(requireContext(),R.drawable.ic_list_recycle_24))

                 list = false

              }else{

                  dashboard_recycle.layoutManager = LinearLayoutManager(requireActivity())
                  dashboard_recycle.setHasFixedSize(true)
                  dashboard_recycle.adapter = adapter

                  flc_button.setImageDrawable(
                      ContextCompat.getDrawable(requireContext(),R.drawable.ic_grid_recycle_24))

                 list = true
             }

        }

            adapter.setonclicklistener(object :DashboardAdapter.OnClickListener{
                override fun onclick(position: Int, product: product) {

                    val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
                    intent.putExtra(Constant.PRODUCT_DETAILS,product)
                    intent.putExtra(Constant.MENU_ITEM_DISPLAY,menuItem)
                    startActivity(intent)

                }

            })

        }else{

            dashboard_recycle.visibility = View.GONE
            text_dashboard.visibility = View.VISIBLE
        }

        hideprogressdialog()
    }

    private fun getproductsdetails(){

        showprogressdialog("please wait")

        firebaseClass().getAllproductsdetails(this)

    }


}