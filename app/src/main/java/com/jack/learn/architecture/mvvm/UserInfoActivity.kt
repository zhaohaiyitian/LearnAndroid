package com.jack.learn.architecture.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jack.learn.R
import com.jack.learn.databinding.ActivityUserInfoBinding

class UserInfoActivity : BaseMVVMActivity<UserInfoViewModel,ActivityUserInfoBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_info)
    }
}