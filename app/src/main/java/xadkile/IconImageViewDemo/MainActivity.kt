package xadkile.IconImageViewDemo

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by abc on 3/17/18.
 */


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gradientButton.setOnClickListener {
            arrowImageView.setIconColor(ContextCompat.getDrawable(this,R.drawable.blue_gradient))
        }

        orangeColorButton.setOnClickListener {
            arrowImageView.setIconColor("#f44b42")
        }
        imageButton.setOnClickListener({
            arrowImageView.setIconColor(ContextCompat.getDrawable(this,R.mipmap.ic_launcher))
        })
    }
}
