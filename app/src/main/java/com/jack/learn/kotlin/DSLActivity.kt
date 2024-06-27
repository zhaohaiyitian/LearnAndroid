package com.jack.learn.kotlin

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.jack.learn.R
import com.jack.learn.databinding.ActivityDslBinding

/**
 * 利用扩展函数和高阶函数的组合实现DSL
 */
class DSLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val viewBinding = ActivityDslBinding.inflate(layoutInflater)
//        setContentView(viewBinding.root)
//        viewBinding.root.mLinearLayout {
//            mTextView {
//                text = "垂直1"
//            }
//            mTextView {
//                text = "垂直2"
//            }
//            mHLinearLayout {
//                mTextView {
//                    text = "水平"
//                }
//                mTextView {
//                    text = "水平2"
//                }
//            }
//        }

        createRoot {
            mLinearLayout {
                mTextView {
                    text = "垂直1"
                }
                mTextView {
                    text = "垂直2"
                }
                mHLinearLayout {
                    mTextView {
                        text = "水平"
                    }
                    mTextView {
                        text = "水平2"
                    }

                    mEditView {
                        textChange {
                            onTextChanged { s, start, before, count ->
                            }

                            afterTextChanged {

                            }
                        }
                    }
                }
            }
        }
    }



    private fun Activity.createRoot(view: ViewGroup.()-> Unit) {
        this.setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            view()
        })
    }


    private fun ViewGroup.mTextView(block: TextView.()->Unit) {
        this.addView(TextView(this.context).apply(block))
    }

    private fun ViewGroup.mEditView(block: EditText.()->Unit) {
        this.addView(EditText(this.context).apply(block))
    }

    private fun ViewGroup.mLinearLayout(block: LinearLayout.() -> Unit) {
        this.addView(LinearLayout(context).apply{
            orientation = LinearLayout.VERTICAL
            block()
        })
    }

    private fun ViewGroup.mHLinearLayout(block: LinearLayout.() -> Unit) {
        this.addView(LinearLayout(context).apply{
            orientation = LinearLayout.HORIZONTAL
            block()
        })
    }
}