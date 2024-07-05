package com.jack.learn.architecture.mvvm

import android.os.Bundle
import com.jack.learn.databinding.ActivityUserInfoBinding

class UserInfoActivity : BaseMVVMActivity<UserInfoViewModel,ActivityUserInfoBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_info)

        bindViewModel {
            userInfoData.observe(this@UserInfoActivity) {
                it.name
            }
        }

        bindView {
            textView.text = ""
        }
        requireViewModel().getUser()
    }
}