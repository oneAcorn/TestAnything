package com.acorn.testanything.testAnimator

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_test_animator.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by acorn on 2019-06-03.
 */
class TestAnimatorActivity : AppCompatActivity() {
    private lateinit var objectAnimator2: ObjectAnimator
    private var isNeedReverse = false
    private lateinit var rotaAnim: ValueAnimator
    private var isRotaReverse = false
    private var curAngle = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_animator)
        initAnim()

        testAnimatorBtn.setOnClickListener {
            with(ObjectAnimator.ofFloat(testAnimatorBtn, "rotation", 0f, 270f, -270f, 30f)) {
                duration = 1000
                start()
            }
            EventBus.getDefault().post("abc")
        }

        testAnimatorBtn2.setOnClickListener {
            if (isNeedReverse) {
                objectAnimator2.reverse()
            } else {
                objectAnimator2.start()
            }
            EventBus.getDefault().postSticky("sticky")
        }

        testAnimatorBtn3.setOnClickListener {
            val anim1 = ObjectAnimator.ofFloat(testAnimatorBtn3, "rotation", 0f, 270f)
            val anim2 = ObjectAnimator.ofFloat(testAnimatorBtn3, "translationX", 0f, 800f)
            val anim3 = ObjectAnimator.ofFloat(testAnimatorBtn3, "translationY", 0f, 800f)
            val anim4 = ObjectAnimator.ofFloat(testAnimatorBtn3, "alpha", 1f, 0.2f)
            val anim5 = ObjectAnimator.ofFloat(testAnimatorBtn3, "scaleX", 1f, 1.5f)
            with(AnimatorSet()) {
                play(anim1).after(anim2).with(anim3).after(anim4).before(anim5)
                duration = 5000
                start()
            }
        }

        testAnimatorBtn4.setOnClickListener {
            val kf1 = Keyframe.ofObject(0f, Color.parseColor("#ff0000"))
            val kf2 = Keyframe.ofObject(0.5f, Color.parseColor("#00ff00"))
            val kf3 = Keyframe.ofObject(1f, Color.parseColor("#0000ff"))
            with(
                ObjectAnimator.ofPropertyValuesHolder(
                    testAnimatorBtn4,
                    PropertyValuesHolder.ofKeyframe("backgroundColor", kf1, kf2, kf3)
                )
            ) {
                setEvaluator(ArgbEvaluator())
                duration = 2000
                start()
            }
        }

        testAnimatorBtn5.setOnClickListener {
            with(AnimatorInflater.loadAnimator(this, R.animator.test_animator)) {
                setTarget(testAnimatorBtn5)
                start()
            }
        }

        testBtn6.setOnClickListener {
            if (rotaAnim.isStarted) {
                rotaAnim.cancel()
                isRotaReverse = !isRotaReverse
                rotaAnim.start()
            } else {
                rotaAnim.start()
            }
        }
    }

    private fun initAnim() {
        val propertyHoloder1 = PropertyValuesHolder.ofFloat("rotation", 0f, 360f)
        val propertyHolder2 = PropertyValuesHolder.ofFloat("translationX", 0f, 800f)
        val propertyHolder3 = PropertyValuesHolder.ofFloat("translationY", 0f, 800f)
        objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(
            testAnimatorBtn2,
            propertyHoloder1,
            propertyHolder2,
            propertyHolder3
        ).apply {
            duration = 1000
//            addListener(object : Animator.AnimatorListener {
//                override fun onAnimationRepeat(animation: Animator?) {
//                }
//
//                override fun onAnimationEnd(animation: Animator?) {
//                    isNeedReverse = !isNeedReverse
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                }
//
//                override fun onAnimationStart(animation: Animator?) {
//                }
//
//            })
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    isNeedReverse = !isNeedReverse
                }
            })
        }

        rotaAnim = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 5000
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                testBtn6.rotation = curAngle
                if (isRotaReverse)
                    curAngle--
                else {
                    curAngle++
                }
            }
//            addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
//                    super.onAnimationStart(animation, isReverse)
//                    isRotaReverse = isReverse
//                }
//            })
        }
    }
}