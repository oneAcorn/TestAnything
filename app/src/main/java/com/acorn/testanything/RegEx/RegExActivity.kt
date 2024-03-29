package com.acorn.testanything.RegEx

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.utils.log
import com.acorn.testanything.utils.logI
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
            //正则引擎默认使用贪婪模式".+"直接匹配到换行符(因为.不匹配换行符)
            matchEt.setText("""<.+>""")
            testStrEt.setText("I am a title tag<H2>title</H2>,and you?")
        }

        regexBtn2.setOnClickListener {
            //"?"使正则引擎使用惰性模式,当匹配到第一个">"就停止匹配了
            //"+",“*”，“{}”和“?”表示的重复也可以用这个方案
            matchEt.setText("""<.+?>""")
            testStrEt.setText("I am a title tag<H2>title</H2>,and you?")
        }

        regexBtn3.setOnClickListener {
            //惰性扩展的一个替代方案
            //我们还有一个更好的替代方案。可以用一个贪婪重复与一个取反字符集：“<[^>]+>”。
            //之所以说这是一个更好的方案在于使用惰性重复时，
            //引擎会在找到一个成功匹配前对每一个字符进行回溯。而使用取反字符集则不需要进行回溯。
            matchEt.setText("""<[^>]+>""")
            testStrEt.setText("I am a title tag<H2>title</H2>,and you?")
        }

        regexBtn4.setOnClickListener {
            matchEt.setText("""((\d+(\,|，)\d+)*|(\d+))""")
            testStrEt.setText("123，456,789")
        }

        regexBtn5.setOnClickListener {
            matchEt.setText("""a*b""")
            var sb = java.lang.StringBuilder()
            for (i: Int in 0..1000) {
                sb.append("a")
            }
            testStrEt.setText(sb.toString())
        }

        regexBtn6.setOnClickListener {
            matchEt.setText("""[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+""")
            testStrEt.setText("abcd@126.com")
        }

        regexPwdBtn2.setOnClickListener {
            //6-12位数字字母混合
            //?!表示否定式的向前查看
            //(?![a-zA-Z]+$)表示不能纯字母
            //(?!\d+$)表示不能纯数字
            matchEt.setText("""(?![a-zA-Z]+$)(?!\d+$)[a-zA-Z0-9]{6,12}""")
        }

        regexPwdBtn.setOnClickListener {
            //6-12位字母数字混合,可以包含除"\"外特殊字符,不可以包含中文

            //方式一(?=.*[a-zA-Z])(?=.*\d)(?!.*[\u4e00-\u9fa5])[^\\]{6,12}
            //?=表示肯定式的向前查看,?!表示否定式的向前查看
            //其中(?!.*[\u4e00-\u9fa5])代表不包含汉字
            //方式一缺点为[^\\]使得用户可以输入除"\"外任何字符,仅需要满足不能纯数字纯字母,不能有中文即可

            //方式二(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9\x21-\x7e]{6,12}
            //其中\x21-\x7e表示特殊字符,似乎不包括中文特殊字符
            matchEt.setText("""(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9\x21-\x7e]{6,12}""")
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
        testBtn2.setOnClickListener {
            val data =
                """#在抖音，记录http://www.baidu.com/ 美好生活#这大概就是冰雪美人吧…… http://v.douyin.com/eUWYth/ 复制此链接，打开【抖音短视频】，直接观看视频！"""
            val str2 = """大家都觉得精彩 http://www.baidu.com 房间附近福建警xhdhh http://tieba.baidu.com"""
            val matcher = Patterns.WEB_URL.matcher(str2)
            if (matcher.find()) {
                logI("group11:${matcher.group()}")
                for (i in 0..matcher.groupCount()) {
                    logI("group:${matcher.group(i)}")
                }
            }
            logI("------------------------------------------------------")
//            fetchUrl(str2)
            matchTv.movementMethod = LinkMovementMethod.getInstance()
            matchTv.text = str2.getUrlSpannable(null)
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

    private fun fetchUrl(str: String) {
        var matcher = Patterns.WEB_URL.matcher(str)
        if (!matcher.find())
            return
        val urlSegments = mutableListOf<SpannableData>()
        var curIndex = 0
        while (curIndex < str.length) {
            val subStr = str.substring(curIndex)
            if (curIndex > 0) {
                matcher = Patterns.WEB_URL.matcher(subStr)
                if (!matcher.find())
                    break
            }
            val segment = matcher.group()
            logI("url segment:$segment")
            val index = subStr.indexOf(segment)
            if (index == -1) {
                break
            }
            curIndex += index + segment.length
            urlSegments.add(SpannableData(index, segment))
            logI("index:$index,segment:$segment")
        }
//        for (i in 0..matcher.groupCount()) {
//            val segment = matcher.group(i)
//            logI("url segment:$segment")
//            if (Patterns.WEB_URL.matcher(segment).find()) { //是url
//                logI("url segment2:$segment")
//                val index = str.indexOf(segment, curIndex)
//                if (index == -1) {
//                    break
//                }
//                curIndex = index + segment.length
//                urlSegments.add(SpannableData(index, segment))
//                logI("index:$index,segment:$segment")
//            }
//        }
    }

    data class SpannableData(val index: Int, val str: String)

    private fun doRegEx() {
        val startTime = System.currentTimeMillis()
        val matcher: Matcher =
            Pattern.compile(matchEt.text.toString()).matcher(testStrEt.text.toString())
        val matcher2: Matcher =
            Pattern.compile(matchEt.text.toString()).matcher(testStrEt.text.toString())
        matchTitleTv.text = "匹配(matches:${matcher2.matches()}):"
        if (matcher.find()) {
            val sb = StringBuilder()
            val res = if (matcher.groupCount() > 0) {
                sb.append("共找到${matcher.groupCount()}处匹配,耗时${System.currentTimeMillis() - startTime}ms\n")
                for (i in 0 until matcher.groupCount()) {
                    sb.append(matcher.group(i))
                    sb.append("\n")
                }
                sb.toString()
            } else {
                sb.append("共找到1处匹配,耗时${System.currentTimeMillis() - startTime}ms\n")
                sb.append(matcher.group())
                sb.toString()
            }
            matchTv.text = res
        } else {
            matchTv.text = "没有找到匹配,耗时${System.currentTimeMillis() - startTime}ms"
        }
        val anim4 = ObjectAnimator.ofInt(
            matchTv,
            "TextColor",
            Color.argb(255, 30, 30, 30),
            Color.argb(255, 0, 244, 190),
            Color.argb(255, 30, 30, 30)
        )
//        anim4.repeatCount=2
        anim4.start()
    }
}