package com.acorn.testanything.RegEx

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.TextView
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_regex.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by acorn on 2019-06-26.
 */
class RegExActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regex)

        regexBtn1.setOnClickListener {
            matchEt.setText("""(?=.*[a-zA-Z])(?=.*\d)[^\\]{6,12}""")
        }



        clearMatchTv.setOnClickListener {
            matchEt.text = null
        }
        clearTestStrTv.setOnClickListener {
            testStrEt.text = null
        }

        testRegexBtn.setOnClickListener {
            doRegEx()
        }
        val editorListener = TextView.OnEditorActionListener { v, actionId, event ->
            if (null != event && event.action == KeyEvent.ACTION_UP) { //有些情况会action_down+action_up会导致重复2次执行
                return@OnEditorActionListener true
            }
            doRegEx()
            true
        }
        matchEt.setOnEditorActionListener(editorListener)
        testStrEt.setOnEditorActionListener(editorListener)
    }

    private fun doRegEx() {
        val matcher: Matcher = Pattern.compile(matchEt.text.toString()).matcher(testStrEt.text.toString())
        if (matcher.find()) {
//                val sb=StringBuilder()
//                for(i in 0 until matcher.groupCount()){
//                    sb.append(matcher.group(i))
//                    sb.append("\n")
//                }
            matchTv.text = matcher.group()
        } else {
            matchTv.text = "没有找到匹配"
        }
    }
}