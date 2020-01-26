package com.acorn.testanything.testNested

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_nested2.*

/**
 * Created by acorn on 2019-06-04.
 */
class NestedActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested2)
        nestedVp.adapter = NestedPagerAdapter(supportFragmentManager)
        nestedTabView.setupWithViewPager(nestedVp)

        nestedTopView.setOnClickListener {
            Toast.makeText(this@NestedActivity2,"...",Toast.LENGTH_SHORT).show()
//            nestedTopView.animate().setInterpolator(BounceInterpolator()).translationY(400f).start()
            with(ObjectAnimator.ofFloat(nestedTopView,"translationY",0f,400f)){
                interpolator=BounceInterpolator()
                duration=300
                start()
            }
        }
    }
}
