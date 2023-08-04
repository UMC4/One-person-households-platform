package com.umc.one_person_households_platform.view.login

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.umc.one_person_households_platform.R

class LoginPasswordResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_password_result)

        // 이미지뷰를 찾아서 클릭 이벤트를 설정합니다.
        val imageViewClose: ImageView = findViewById(R.id.iv_close)
        imageViewClose.setOnClickListener {
            // 창을 닫는 기능을 수행합니다.
            finish()
        }
    }
}