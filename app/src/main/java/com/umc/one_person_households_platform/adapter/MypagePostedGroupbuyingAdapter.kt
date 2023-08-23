package com.umc.one_person_households_platform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.umc.one_person_households_platform.R
import com.umc.one_person_households_platform.databinding.ItemMypagePostedGroupBuyingBinding
import com.umc.one_person_households_platform.model.CommunityBuyPost


class MypagePostedGroupbuyingAdapter(private val heartList: List<CommunityBuyPost>) : RecyclerView.Adapter<MypagePostedGroupbuyingAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMypagePostedGroupBuyingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMypagePostedGroupBuyingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_mypage_posted_group_buying,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val heart = heartList[position]
        holder.binding.tvClassification.text = heart.category
        holder.binding.tvTitle.text = heart.title
        holder.binding.tvProductName.text = "제품명: ${heart.productName}"
        holder.binding.tvUser.text = "${heart.createAt} · ${heart.commentCount} · 마감기한 D-${heart.remainDay}"


    }

    override fun getItemCount(): Int = heartList.size
}
