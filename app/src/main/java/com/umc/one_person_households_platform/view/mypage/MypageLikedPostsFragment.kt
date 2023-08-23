package com.umc.one_person_households_platform.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.one_person_households_platform.adapter.MypageLikedpostAdapter
import com.umc.one_person_households_platform.databinding.FragmentMypageLikedPostsBinding
import com.umc.one_person_households_platform.model.LikeResult
import com.umc.one_person_households_platform.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MypageLikedPostsFragment : Fragment() {

    private var _binding: FragmentMypageLikedPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 데이터 바인딩 초기화
        _binding = FragmentMypageLikedPostsBinding.inflate(inflater, container, false)

        // 뒤로가기 버튼 클릭 이벤트 설정
        binding.ivBack.setOnClickListener {
            goBack() // 뒤로가기 기능을 수행하는 함수 호출
        }

        fetchMyPosts()

        return binding.root
    }

    // 뒤로가기 기능을 수행하는 함수
    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun fetchMyPosts() {
        val apiClient = ApiClient.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiClient.getUserCommunityLikes()

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val posts = response.body()?.result
                    updatePosts(posts ?: listOf()) // Adapter에 데이터 전달
                } else {
                    Toast.makeText(context, "게시물을 가져오는데 실패했습니다: ${response.body()?.message ?: "알 수 없는 오류"}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "에러: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePosts(posts: List<LikeResult>) {
        binding.likedpost.layoutManager = LinearLayoutManager(context)
        binding.likedpost.adapter = MypageLikedpostAdapter(posts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
