package xadkile.IconImageViewDemo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xadkile.IconImageView.IconImageView

/**
 * Created by abc on 3/17/18.
 */
@RunWith(AndroidJUnit4::class)
class IconImageViewTest {

    val mainActRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    lateinit var act: MainActivity
    @Before
    fun before(){
        //start demo activity
        mainActRule.launchActivity(Intent())
        act = mainActRule.activity
    }
    @Test
    fun testChangeColor() {
        act.runOnUiThread {
            var iconImageView: IconImageView = act.findViewById(R.id.arrowImageView)
            //change icon color to black (#000000)
            iconImageView.setIconColor("#000000")
            val bitmap: Bitmap = (iconImageView.drawable as BitmapDrawable).bitmap
            //check color of each pixel of the Image
            for(x in 0 until bitmap.width){
                for(y in 0 until bitmap.height){
                    val pixel = bitmap.getPixel(x,y)
                    val red: Int = Color.red(pixel)
                    val green: Int = Color.green(pixel)
                    val blue: Int = Color.blue(pixel)
                    Assert.assertEquals(0, red)
                    Assert.assertEquals(0, green)
                    Assert.assertEquals(0, blue)
                }
            }
        }
    }
}
