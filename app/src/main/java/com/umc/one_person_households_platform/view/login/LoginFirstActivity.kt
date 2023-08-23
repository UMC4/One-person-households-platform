package com.umc.one_person_households_platform.view.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.umc.one_person_households_platform.R
import com.umc.one_person_households_platform.databinding.ActivityLoginFirstBinding
import com.umc.one_person_households_platform.model.LoginRequest
import com.umc.one_person_households_platform.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFirstActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: ActivityLoginFirstBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 데이터 바인딩 인스턴스 생성
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_first)

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val checkBox = binding.checkBox
        checkBox.isChecked = isLoggedIn()

        binding.editText3.setOnClickListener {
            login()
        }

        // 네비게이션 컴포넌트 초기화
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph_login) as NavHostFragment
        navController = navHostFragment.navController

        binding.signshipt.setOnClickListener {
            navController.navigate(R.id.action_LoginFirstActivity_to_signupActivity)
        }

        binding.findid.setOnClickListener {
            navController.navigate(R.id.action_LoginFirstActivity_to_loginFindIdFragment)
        }

        binding.findpassword.setOnClickListener {
            navController.navigate(R.id.action_LoginFirstActivity_to_loginFindPasswordFragment)
        }
    }

    // 로그인 상태 확인
    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // MainActivity로 이동하는 로직을 추가하세요
    private fun navigateToMainActivity() {}

    // 사용자 로그인 로직
    private fun login() {
        val loginId = binding.editText.text.toString()  // 로그인 아이디 가져오기
        val password = binding.editText2.text.toString()  // 비밀번호 가져오기

        // 둘 중 하나라도 입력되지 않았을 경우
        if (loginId.isEmpty() || password.isEmpty()) {
            binding.tvNoreply.visibility = View.VISIBLE
            binding.tvIncorrect.visibility = View.GONE
            return
        }

        val loginRequest = LoginRequest(loginId, password)

        val apiClient = ApiClient.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiClient.loginUser(loginRequest)

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val jwt = response.body()?.result?.jwt
                    editor.putString("jwt", jwt).apply()
                    navigateToMainActivity()
                } else if (response.isSuccessful && response.body()?.isSuccess == false) {
                    binding.tvIncorrect.visibility = View.VISIBLE
                    binding.tvNoreply.visibility = View.GONE
                } else {
                    val errorMessage = response.body()?.message ?: "로그인 실패!"
                    Toast.makeText(this@LoginFirstActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginFirstActivity, "에러: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}







