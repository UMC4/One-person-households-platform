package com.umc.one_person_households_platform.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umc.one_person_households_platform.databinding.ActivitySignupBinding
import com.umc.one_person_households_platform.model.SignupRequest
import com.umc.one_person_households_platform.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val userId = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()
            val nickname = binding.etNickname.text.toString()

            signupUser(userId, password, email, nickname)
        }

        binding.ivBack.setOnClickListener {
            goBack()
        }

        binding.rbIdcheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkDuplicateUserId(binding.etId.text.toString())
            }
        }

        binding.rbEmailcheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sendValidationCode(binding.etEmail.text.toString())
                binding.tvChecknumber.visibility = View.VISIBLE
                binding.etChecknumber.visibility = View.VISIBLE
                binding.rbChecknumber.visibility = View.VISIBLE
            } else {
                binding.tvChecknumber.visibility = View.GONE
                binding.etChecknumber.visibility = View.GONE
                binding.rbChecknumber.visibility = View.GONE
            }
        }

        binding.etChecknumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    binding.rbChecknumber.text = "재발송"
                } else {
                    binding.rbChecknumber.text = "인증번호 받기"
                }
            }
        })

        binding.rbChecknumber.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sendValidationCode(binding.etEmail.text.toString())
            }
        }

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPasswordStrength(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPasswordagain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPasswordMatch()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 화면을 터치했을 때 키보드 숨기기
        binding.root.setOnClickListener {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

        private fun checkPasswordStrength(password: String) {
            val hasUpperCase = password.any { it.isUpperCase() }
            val hasLowerCase = password.any { it.isLowerCase() }
            val hasDigit = password.any { it.isDigit() }
            val isAtLeast8 = password.length >= 8

            if (hasUpperCase && hasLowerCase && hasDigit && isAtLeast8) {
                binding.tvGoodpassword.visibility = View.VISIBLE
                binding.tvBadpassword.visibility = View.GONE
            } else {
                binding.tvGoodpassword.visibility = View.GONE
                binding.tvBadpassword.visibility = View.VISIBLE
            }
        }

        private fun checkPasswordMatch() {
            if (binding.etPassword.text.toString() == binding.etPasswordagain.text.toString()) {
                binding.tvCorrect.visibility = View.VISIBLE
                binding.tvIncorrect.visibility = View.GONE
            } else {
                binding.tvCorrect.visibility = View.GONE
                binding.tvIncorrect.visibility = View.VISIBLE
            }
        }


    private fun goBack() {
        supportFragmentManager.popBackStack()
    }
    //이메일 인증
    private fun sendValidationCode(email: String) {
        val apiClient = ApiClient.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiClient.sendValidationEmail(email)

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    Toast.makeText(this@SignupActivity, "인증 번호 발송 성공!", Toast.LENGTH_SHORT).show()
                    // 여기서 response.body()?.result?.code 를 사용하여 어디선가 인증번호를 저장할 수 있습니다.
                } else {
                    Toast.makeText(this@SignupActivity, "인증 번호 발송 실패: ${response.body()?.message ?: "알 수 없는 오류"}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "에러: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //회원가입
    private fun signupUser(userId: String, password: String, email: String, nickname: String) {
        val apiClient = ApiClient.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiClient.signupUser(
                    SignupRequest(
                        userName = userId,
                        loginId = userId,
                        password = password,
                        email = email,
                        nickname = nickname,
                        adPolicyAgreement = 1
                    )
                )

                if (response.isSuccessful) {
                    Toast.makeText(this@SignupActivity, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignupActivity, "회원가입 실패: ${response.message()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "에러: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //아이디 중복 확인
    private fun checkDuplicateUserId(userId: String) {
        val apiClient = ApiClient.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiClient.getUsers()

                if (response.isSuccessful) {
                    val userList = response.body()

                    // 유저 아이디 중복 확인
                    val isDuplicate = userList?.any { user -> user.loginId == userId } ?: false
                    if (isDuplicate) {
                        Toast.makeText(this@SignupActivity, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show()
                        binding.rbIdcheck.isChecked = false // 중복되는 경우 체크박스를 해제
                    } else {
                        Toast.makeText(this@SignupActivity, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this@SignupActivity, "유저 정보를 가져오는데 실패했습니다: ${response.message()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "에러: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}







