package com.umc.one_person_households_platform.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.umc.one_person_households_platform.databinding.FragmentLoginFindIdBinding
import com.umc.one_person_households_platform.model.FindIdResponse
import com.umc.one_person_households_platform.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFindIdFragment : Fragment() {

    private var _binding: FragmentLoginFindIdBinding? = null
    private val binding get() = _binding!!

    private val apiService: ApiClient by lazy {
        ApiClient.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginFindIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            goBack()
        }

        binding.findbutton.setOnClickListener {
            findId()
        }
    }

    private fun findId() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(context, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        apiService.getUserId(email).enqueue(object : Callback<FindIdResponse> {
            override fun onFailure(call: Call<FindIdResponse>, t: Throwable) {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<FindIdResponse>, response: Response<FindIdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.isSuccess == true) {
                        val userId = apiResponse.result?.loginId
                        Toast.makeText(context, "유저 아이디: $userId", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, apiResponse?.message ?: "서버 오류", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "서버 오류", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
