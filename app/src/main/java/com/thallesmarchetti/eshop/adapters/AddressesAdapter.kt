package com.thallesmarchetti.eshop.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thallesmarchetti.eshop.Firebase.firebaseClass
import com.thallesmarchetti.eshop.R
import com.thallesmarchetti.eshop.Utils.Constant
import com.thallesmarchetti.eshop.models.Address
import com.thallesmarchetti.eshop.ui.activites.Add_Edit_AddressActivity
import com.thallesmarchetti.eshop.ui.activites.AddressesActivity
import com.thallesmarchetti.eshop.ui.activites.checkoutActivity
import kotlinx.android.synthetic.main.address_item.view.*

class AddressesAdapter(val context: AddressesActivity, val address:ArrayList<Address>,val selectaddress:Boolean): RecyclerView.Adapter<AddressesAdapter.myviewholder>() {

    private var onclicklistener: OnClickListener? = null

    class myviewholder(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        return myviewholder(LayoutInflater.from(context).inflate(R.layout.address_item,
            parent,false))
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {

        val model = address[position]

        Log.i("model",model.toString())

        holder.itemView.address_item_name.text = model.first_name
        holder.itemView.address_item_address.text = model.address
        holder.itemView.address_item_phone.text = model.phone_number.toString()
        holder.itemView.address_item_place.text = model.place

        if (selectaddress){

            holder.itemView.setOnClickListener {

                val intent = Intent(context,checkoutActivity::class.java)
                intent.putExtra(Constant.ADDRESS_DETAILS,model)
                context.startActivity(intent)

            }
        }else {

            holder.itemView.setOnClickListener {

                onclicklistener!!.onclick(position, model.address)

            }
        }
    }

    override fun getItemCount(): Int {
        return address.size
    }

    fun setonclicklistener(onclicklistener:OnClickListener){

        this.onclicklistener = onclicklistener
    }

    interface OnClickListener {

        fun onclick (position: Int,address: String)
    }

    fun notifiyDeleteitem( position: Int){

        alertdialogfordeletingaddress(address[position].id,address[position].address)
        notifyItemChanged(position)
    }

    fun notifiyedititem(activity: Activity, position: Int){

        val intent = Intent(activity,Add_Edit_AddressActivity::class.java)
        intent.putExtra(Constant.ADDRESS_EDIT_DETAILS,address[position])
        activity.startActivityForResult(intent,Constant.ADD_ADDRESS_REQUEST_CODE)
        notifyItemChanged(position)
    }

    // delete product dialog..
    private fun alertdialogfordeletingaddress(addressid:String,title:String){

        val Bulder = AlertDialog.Builder(context)
        Bulder.setTitle("Alert")
        Bulder.setMessage("are you sure you want to delete $title")
        Bulder.setPositiveButton("Yes"){dialoginterface,whitch ->

            dialoginterface.dismiss()

            context.showprogressdialog("please waite")
            firebaseClass().deleteaddress(context,addressid)

        }.setNegativeButton("NO"){
                dialoginterface,whitch ->

            dialoginterface.dismiss()
        }

        val alertdialog = Bulder.create()
        alertdialog.setCancelable(false)
        alertdialog.show()
    }

}