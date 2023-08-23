package com.umc.one_person_households_platform.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.umc.one_person_households_platform.databinding.FragmentMypageAccountSettingBinding
import com.umc.one_person_households_platform.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MypageAccountSettingFragment : Fragment() {

    private var _binding: FragmentMypageAccountSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageAccountSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.ivBack.setOnClickListener {
            goBack()
        }

        binding.rbPasswordchange.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 비밀번호 변경 로직
            }
        }

        binding.rbEmailchange.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 이메일 변경 로직
            }
        }


        binding.rbNicknamechange.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val newNickname = binding.etNickname.text.toString().trim()
                if (newNickname.isNotEmpty()) {
                    changeNickname(newNickname)
                } else {
                    Toast.makeText(requireContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun changeNickname(nickname: String) {
        val apiClient = ApiClient.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiClient.setNickname(nickname)

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    Toast.makeText(requireContext(), response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "닉네임 변경 실패: ${response.body()?.message ?: "알 수 없는 오류"}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "에러: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


