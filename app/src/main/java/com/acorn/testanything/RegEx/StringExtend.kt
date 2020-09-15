package com.acorn.testanything.RegEx

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2020/9/15.
 */

/**
 * 设置带url的字符串，url自动高亮，并可以点击
 */
fun TextView.setUrlText(
    textStr: String,
    textcolor: Int? = null,
    clickCallback: ((url: String) -> Unit)? = null
) {
    movementMethod = LinkMovementMethod.getInstance()
    text = textStr.getUrlSpannable(textcolor, clickCallback)
}

/**
 * 提取字符串中的链接，并且添加点击事件
 */
fun String.getUrlSpannable(
    textcolor: Int? = null,
    clickCallback: ((url: String) -> Unit)? = null
): SpannableStringBuilder {
    var matcher = Patterns.WEB_URL.matcher(this)
    if (!matcher.find())
        return SpannableStringBuilder(this)
    val urlSegments = mutableListOf<SpannableData>()
    var curIndex = 0
    while (curIndex < length) {
        val subStr = substring(curIndex)
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
        urlSegments.add(SpannableData(curIndex + index, segment))
        curIndex += index + segment.length
        logI("index:$index,curIndex:$curIndex,segment:$segment")
    }
    return SpannableStringBuilder(this)
        .apply {
            for (bean in urlSegments) {
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        clickCallback?.invoke(bean.str)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = textcolor ?: Color.BLUE       //设置文件颜色
                        ds.isUnderlineText = true;
                    }
                }, bean.index, bean.index + bean.str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
}

data class SpannableData(val index: Int, val str: String)