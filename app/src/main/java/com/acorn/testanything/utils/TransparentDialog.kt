package com.acorn.testanything.utils

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.acorn.testanything.R

/**
 * Created by acorn on 2021/4/28.
 */
class TransparentDialog:DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        return View.inflate(context, R.layout.dialog_transparent, null)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            val lp = it.attributes
            lp.width = screenWidth
            lp.height = screenHeight
            it.attributes = lp
        }
    }
}