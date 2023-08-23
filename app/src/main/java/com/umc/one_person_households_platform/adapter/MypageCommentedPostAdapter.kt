package com.umc.one_person_households_platform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc.one_person_households_platform.R
import com.umc.one_person_households_platform.model.CommentResult

class MypageCommentedPostAdapter(private val posts: List<CommentResult>) : RecyclerView.Adapter<MypageCommentedPostAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvClassification: TextView = itemView.findViewById(R.id.tv_classification)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvUser: TextView = itemView.findViewById(R.id.tv_user)
    }

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mypage_my_posts, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.tvClassification.text = post.category // 여기서 'classification'은 예시 데이터 모델의 필드입니다. 실제 필드명으로 변경해야 합니다.
        holder.tvTitle.text = post.title
        holder.tvUser.text = "${post.createAt} · 댓글 ${post.commentCount}" // 이 부분도 실제 데이터 모델에 맞게 수정해야 합니다.
    }
}
