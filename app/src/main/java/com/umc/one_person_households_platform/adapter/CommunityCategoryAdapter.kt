package com.umc.one_person_households_platform.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.umc.one_person_households_platform.R
import com.umc.one_person_households_platform.model.CommunityDTO
import com.umc.one_person_households_platform.model.CommunityDetailDTO
import com.umc.one_person_households_platform.network.ApiClient
import com.umc.one_person_households_platform.view.community.CommunityFragment
import com.umc.one_person_households_platform.view.community.CommunityFragmentDirections
import com.umc.one_person_households_platform.view.community.PostdetailFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityCategoryAdapter(var realpostlist: CommunityDTO) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return CustomViewHolder(view)
    }

    private inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {

        return realpostlist?.result?.size ?: 0

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        var view = holder.itemView


        view.findViewById<TextView>(R.id.tv_posttitle).text =
            realpostlist!!.result[position].title

        view.findViewById<TextView>(R.id.tv_author).text =
            realpostlist!!.result[position].nickname
        view.findViewById<TextView>(R.id.tv_category).text =
            realpostlist!!.result[position].category
        view.findViewById<TextView>(R.id.tv_distance).text =
            realpostlist!!.result[position].distance.toString()
        view.findViewById<TextView>(R.id.tv_time).text =
            realpostlist!!.result[position].createAt




        if(realpostlist!!.result[position].imagePath == "" || realpostlist!!.result[position].imagePath.isNullOrBlank()){}else{
            view.findViewById<ImageView>(R.id.iv_headimg).setImageBitmap(stringToBitmap(realpostlist!!.result[position].imagePath))


        }



        view.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.cl_postclick).setOnClickListener {


            val action = CommunityFragmentDirections.actionCommunityFragmentToPostdetailFragment(realpostlist!!.result[position].postIdx)
            view.findNavController().navigate(action)

//            val navController = Navigation.findNavController(view)
//            navController.navigate(R.id.action_communityFragment_to_postdetailFragment, bundle)
        }
    }

    fun stringToBitmap(encodedString: String?): Bitmap? {
        val encodeByte: ByteArray = java.util.Base64.getDecoder().decode(encodedString)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }
}