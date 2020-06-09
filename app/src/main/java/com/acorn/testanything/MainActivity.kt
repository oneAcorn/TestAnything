package com.acorn.testanything

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.RegEx.RegExActivity
import com.acorn.testanything.anything.AnythingActivity
import com.acorn.testanything.broadcast.RegisterBroadcastActivity
import com.acorn.testanything.constraintLayout.ConstraintChainsActivity
import com.acorn.testanything.constraintLayout.ConstraintPlaceHolderActivity
import com.acorn.testanything.constraintLayout.ConstraintRatioActivity
import com.acorn.testanything.memory.TestMemoryLeakActivity
import com.acorn.testanything.motionLayout.MotionLayoutActivity
import com.acorn.testanything.mvvm.MVVMActivity
import com.acorn.testanything.rxjava.RxJavaActivity
import com.acorn.testanything.testAnimator.TestAnimatorActivity
import com.acorn.testanything.testNested.NestedActivity2
import com.acorn.testanything.testNested.NestedActivity3
import com.acorn.testanything.testNested.NestedActivity4
import com.acorn.testanything.testWithOutput.TestWithOutputActivity
import com.acorn.testanything.utils.log
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestActivity::class.java))
//            val intent = Intent()
//            val comp = ComponentName(
//                "com.nd.hy.android.edu.study.commune",
//                "com.nd.hy.android.edu.study.commune.view.login.LoginActivity"
//            )
//            intent.component = comp
//            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//            startActivity(intent)
        }
        testWithOutput.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestWithOutputActivity::class.java))
        }
        nestedBtn.setOnClickListener {
            startActivity(Intent(this, NestedActivity2::class.java))
        }
        nestedBtn2.setOnClickListener {
            startActivity(Intent(this, NestedActivity3::class.java))
        }
        nestedBtn3.setOnClickListener {
            startActivity(Intent(this, NestedActivity4::class.java))
        }
        animatorBtn.setOnClickListener {
            startActivity(Intent(this, TestAnimatorActivity::class.java))
        }
        regexBtn.setOnClickListener {
            startActivity(Intent(this, RegExActivity::class.java))
        }
        anyThingBtn.setOnClickListener {
            startActivity(Intent(this, AnythingActivity::class.java))
        }
        rxjavaBtn.setOnClickListener {
            startActivity(Intent(this, RxJavaActivity::class.java))
        }
        motionBtn.setOnClickListener {
            startActivity(Intent(this, MotionLayoutActivity::class.java))
        }
        constraintBtn.setOnClickListener {
            startActivity(Intent(this, ConstraintRatioActivity::class.java))
        }
        constraintChainsBtn.setOnClickListener {
            startActivity(Intent(this, ConstraintChainsActivity::class.java))
        }
        constraintPlaceHolderBtn.setOnClickListener {
            startActivity(Intent(this, ConstraintPlaceHolderActivity::class.java))
        }
        memoryLeakBtn.setOnClickListener {
            startActivity(Intent(this, TestMemoryLeakActivity::class.java))
        }
        broadcastBtn.setOnClickListener {
            startActivity(Intent(this, RegisterBroadcastActivity::class.java))
        }
        MVVMBtn.setOnClickListener {
            startActivity(Intent(this, MVVMActivity::class.java))
        }
        EventBus.getDefault().register(this)
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
