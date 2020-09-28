package com.acorn.testanything.qr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.utils.dp
import com.acorn.testanything.utils.rawToFile
import com.bumptech.glide.Glide
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.background.BlendBackground
import com.github.sumimakito.awesomeqr.option.background.GifBackground
import com.github.sumimakito.awesomeqr.option.background.StillBackground
import com.github.sumimakito.awesomeqr.option.color.Color
import com.github.sumimakito.awesomeqr.option.logo.Logo
import kotlinx.android.synthetic.main.activity_qr.*
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType
import net.glxn.qrgen.core.scheme.MeCard
import java.io.File


/**
 * Created by acorn on 2020/9/27.
 */
class QRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val option = RenderOption().apply {
            content = "hello world fdsalkfjsdaldsjalf"
            size = 200.dp
            color = color1()
//            background = bg1()
            logo = logo1()
            patternScale = 0.3f
            roundedPatterns = true
        }

        val option2 = RenderOption().apply {
            content = "hello world"
            size = 200.dp
//            color = color1()
//            background = bg2()
//            logo = logo1()
        }

        val option3 = RenderOption().apply {
            content = "hello world"
            size = 200.dp
            color = color1()
            background = bg3()
            logo = logo1()
        }

        AwesomeQrRenderer.renderAsync(option, {
            if (it.bitmap != null) {
                iv1.setImageBitmap(it.bitmap)
            }
        }, {
            it.printStackTrace()
        })

        AwesomeQrRenderer.renderAsync(option2, {
            if (it.bitmap != null) {
                iv2.setImageBitmap(it.bitmap)
            }
        }, {
            it.printStackTrace()
        })

        AwesomeQrRenderer.renderAsync(option3, {
            if (it.type == RenderResult.OutputType.GIF) {
                Glide.with(this).asGif().load(it.gifOutputFile)
                    .into(iv3)
            }
        }, {
            it.printStackTrace()
        })

        iv4.setImageBitmap(
            QRCode.from("Hello World sdafadasfasd").to(ImageType.PNG)
                .withColor(android.graphics.Color.BLUE, android.graphics.Color.WHITE)
                .withSize(200.dp, 200.dp).bitmap()
        )
//        Glide.with(this).asGif().load(QRCode.from("Hello World sdafadasfasd").to(ImageType.GIF).withSize(200.dp, 200.dp).file())
//            .into(iv4)

        val johnDoe = MeCard("John Doe")
        johnDoe.email = "john.doe@example.org"
        johnDoe.address = "John Doe Street 1, 5678 Doestown"
        johnDoe.telephone = "1234"
        iv5.setImageBitmap(QRCode.from(johnDoe).bitmap())
    }

    private fun bg1() = StillBackground()
        .apply {
            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.pic1)
//            clippingRect = Rect(0, 0, 200, 200)
            alpha = 0.7f
        }

    private fun bg2() = BlendBackground()
        .apply {
            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.memory)
                .copy(Bitmap.Config.ARGB_4444, true)
            clippingRect = Rect(0, 0, 200.dp, 200.dp)
            alpha = 0.7f
            borderRadius = 10 // radius for blending corners
        }

    private fun bg3(): GifBackground {
        val mInputFile = rawToFile(R.raw.test)
        val mOutFile = File(mInputFile.absolutePath, "res.gif")
        return GifBackground()
            .apply {
                inputFile = mInputFile
                outputFile = mOutFile
                clippingRect = Rect(0, 0, 200, 200)
            }
    }

    private fun color1() = Color()
        .apply {
            dark = 0xFF1F7Dff.toInt() // for blank spaces
            light = 0xFFFFFFFF.toInt() // for non-blank spaces
            // for the background (will be overriden by background images, if set)
            background = 0xFFFFFFFF.toInt()
            // set to true to automatically pick out colors from the background image (will only work if background image is present)
            auto = false
        }

    private fun logo1() = Logo()
        .apply {
            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_living_room_large)
//            borderRadius = 10 // radius for logo's corners
            borderWidth = 0 // width of the border to be added around the logo
//            scale = 0.3f // scale for the logo in the QR code
//            clippingRect =
//                RectF(0f, 0f, 200f, 200f) // crop the logo image before applying it to the QR code
        }
}