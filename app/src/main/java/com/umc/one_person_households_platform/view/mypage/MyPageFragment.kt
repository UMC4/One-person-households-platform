package com.umc.one_person_households_platform.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.umc.one_person_households_platform.R
import com.umc.one_person_households_platform.databinding.FragmentMyPageBinding

class MypageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.ivBack.setOnClickListener {
            goBack()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NavController 초기화
        val navController = findNavController()

        binding.tvAccount.setOnClickListener {
            navController.navigate(R.id.mypageAccountSettingFragment)
        }

        binding.tvMypost.setOnClickListener {
            navController.navigate(R.id.mypageMyPostsFragment)
        }

        binding.tvMycomment.setOnClickListener {
            navController.navigate(R.id.mypageCommentedPostsFragment)
        }

        binding.tvLikepost.setOnClickListener {
            navController.navigate(R.id.mypageLikedPostsFragment)
        }

        binding.tvInterestpost.setOnClickListener {
            navController.navigate(R.id.mypageSavedPostsFragment)
        }

        binding.tvPostedGroupBuying.setOnClickListener {
            navController.navigate(R.id.mypagePostedGroupBuyingFragment)
        }

        binding.tvJoinedGroupBuying.setOnClickListener {
            navController.navigate(R.id.mypageJoinedGroupBuyingFragment)
        }

        binding.tvSavedGroupBuying.setOnClickListener {
            navController.navigate(R.id.mypageSavedGroupBuyingFragment)
        }

        binding.tvAlarmSetting.setOnClickListener {
            navController.navigate(R.id.mypageAlarmSettingFragment)
        }
    }

    // 뒤로가기 기능을 수행하는 함수
    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

