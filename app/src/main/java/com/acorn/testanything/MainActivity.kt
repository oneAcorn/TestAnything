package com.acorn.testanything

import android.os.Bundle
import com.acorn.testanything.RegEx.RegExActivity
import com.acorn.testanything.anything.AnythingActivity
import com.acorn.testanything.bar.StatusNavBarHideActivity
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.broadcast.RegisterBroadcastActivity
import com.acorn.testanything.constraintLayout.ConstraintChainsActivity
import com.acorn.testanything.constraintLayout.ConstraintPlaceHolderActivity
import com.acorn.testanything.constraintLayout.ConstraintRatioActivity
import com.acorn.testanything.countdown.TestCountDownActivity
import com.acorn.testanything.demo.Demo
import com.acorn.testanything.drawable.DrawableActivity
import com.acorn.testanything.memory.TestMemoryLeakActivity
import com.acorn.testanything.memory.TestMemoryLeakActivity2
import com.acorn.testanything.mmkv.MMKVTestActivity
import com.acorn.testanything.motionLayout.MotionLayoutActivity
import com.acorn.testanything.mvvm.MVVMActivity
import com.acorn.testanything.qr.QRActivity
import com.acorn.testanything.rxjava.RxJavaActivity
import com.acorn.testanything.service.TestServiceActivity
import com.acorn.testanything.testAnimator.TestAnimatorActivity
import com.acorn.testanything.testNested.NestedActivity2
import com.acorn.testanything.testNested.NestedActivity3
import com.acorn.testanything.testNested.NestedActivity4
import com.acorn.testanything.testWithOutput.TestWithOutputActivity
import com.acorn.testanything.utils.TransparentDialog
import com.acorn.testanything.utils.log
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseDemoAdapterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo("TestActivity", activity = TestActivity::class.java),
            Demo("TestWithOutputActivity", activity = TestWithOutputActivity::class.java),
            Demo("NestedActivity2", activity = NestedActivity2::class.java),
            Demo("NestedActivity3", activity = NestedActivity3::class.java),
            Demo("NestedActivity4", activity = NestedActivity4::class.java),
            Demo("AnimatorActivity", activity = TestAnimatorActivity::class.java),
            Demo("RegEx", activity = RegExActivity::class.java),
            Demo("Anything", activity = AnythingActivity::class.java),
            Demo("RxJava", activity = RxJavaActivity::class.java),
            Demo("MotionLayout", activity = MotionLayoutActivity::class.java),
            Demo("Constraint Ratio", activity = ConstraintRatioActivity::class.java),
            Demo("Constraint Chains", activity = ConstraintChainsActivity::class.java),
            Demo("Constraint PlaceHolder", activity = ConstraintPlaceHolderActivity::class.java),
            Demo("MemoryLeak", activity = TestMemoryLeakActivity::class.java),
            Demo("MemoryLeak2", activity = TestMemoryLeakActivity2::class.java),
            Demo("Broadcast", activity = RegisterBroadcastActivity::class.java),
            Demo("MVVM", activity = MVVMActivity::class.java),
            Demo("Countdown", activity = TestCountDownActivity::class.java),
            Demo("二维码", activity = QRActivity::class.java),
            Demo("MMKV", activity = MMKVTestActivity::class.java),
            Demo("Drawable", activity = DrawableActivity::class.java),
            Demo("testDialog", id = 1000),
            Demo("隐藏状态栏导航栏", activity = StatusNavBarHideActivity::class.java),
            Demo("测试Service", activity = TestServiceActivity::class.java)
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            1000 -> {
                TransparentDialog().show(supportFragmentManager, "TransparentDialog")
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(s: String) {
        log("收到消息:$s")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
