package xadkile.IconImageView

/**
 * Created by abc on 3/17/18.
 */

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.TypedValue


/**
 * Created by zUser on 3/1/18.
 */
open class IconImageView : AppCompatImageView{
    private var alphaMap: Array<IntArray> = arrayOf(intArrayOf(0))
    constructor(context:Context):super(context){
        alphaMap = makeAlphaMap(this)
    }
    constructor(context: Context,attr:AttributeSet?):super(context,attr){
        alphaMap = makeAlphaMap(this)
        val arrayAttr:TypedArray = context.theme.obtainStyledAttributes(
                attr,R.styleable.IconImageView,
                0,0)
        try{
            val iconColor = TypedValue()
            arrayAttr.getValue(R.styleable.IconImageView_iconColor,iconColor)

            if(iconColor.type==3){
                setIconColor(ContextCompat.getDrawable(context,iconColor.resourceId))
            }
            else if(iconColor.type >=TypedValue.TYPE_FIRST_COLOR_INT
                    && iconColor.type<=TypedValue.TYPE_LAST_COLOR_INT){
                setIconColor(iconColor.data)
            }

        } finally {
            arrayAttr.recycle()
        }
    }
    constructor(context:Context,attr:AttributeSet?,defStyleAttr:Int):super(context,attr,defStyleAttr){
        alphaMap = makeAlphaMap(this)
    }

    /**
     * Generate an array of alpha value of all pixel of an imageView
     */
    protected open fun makeAlphaMap(imageView: AppCompatImageView):Array<IntArray>{
        val bitmap:Bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val result:Array<IntArray> = Array(bitmap.width,{i:Int->IntArray(0)})
        for(x in 0 until bitmap.width){
            val column: IntArray = kotlin.IntArray(bitmap.height)
            for(y in 0 until bitmap.height){
                column.set(y,Color.alpha(bitmap.getPixel(x,y)))
            }
            result.set(x,column)
        }
        return result
    }

    /**
     * Change non-transparent pixel of the bitmap in this imageview to newColor
     * preserve alpha value
     */
    protected open fun setIconColor(newColor:Int, imageView: AppCompatImageView) {
        val bitmapDrawable: BitmapDrawable = imageView.drawable as BitmapDrawable
        val bitmap: Bitmap = bitmapDrawable.bitmap
        //use separated bitmap to prevent IllegalStateException when setting each pixel's color
        val newBitmap: Bitmap = Bitmap.createBitmap(bitmap.width,bitmap.height,bitmap.config)
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                //use alpha to sort out transparent pixel
                //better than checking pixel value one by one
                var alpha: Int = alphaMap.get(x).get(y)
                val red: Int = Color.red(newColor)
                val green: Int = Color.green(newColor)
                val blue: Int = Color.blue(newColor)
                newBitmap.setPixel(x,y,Color.argb(alpha,red,green,blue))
            }
        }
        imageView.setImageBitmap(newBitmap) //this invokes onDraw
    }

    /**
     * convinient method of setIconColor
     * Apply directly to this instance
     */
    open fun setIconColor(newColor:Int){
        setIconColor(newColor,this)
    }

    /**
     * convinient method of setIconColor, accept color represented by a string
     * Apply directly to this instance
     */
    open fun setIconColor(newColorStr:String){
        setIconColor(Color.parseColor(newColorStr))
    }

    /**
     * Draw a Drawable over the non-transparent of the bitmap of the imageView
     * preserve alpha value
     */
    protected open fun setIconColor(drawable: Drawable, imageView: AppCompatImageView){
        val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(newBitmap)
        drawable.setBounds(0,0,newBitmap.width,newBitmap.height)
        drawable.draw(canvas)
        for(x in 0 until bitmap.width){
            for(y in 0 until bitmap.height){
                val newPixel: Int = newBitmap.getPixel(x,y)
                var alpha: Int = alphaMap.get(x).get(y)
                val red: Int = Color.red(newPixel)
                val green: Int = Color.green(newPixel)
                val blue: Int = Color.blue(newPixel)
                newBitmap.setPixel(x,y,Color.argb(alpha,red,green,blue))
            }
        }
        imageView.setImageBitmap(newBitmap)
    }

    /**
     * convenient method of setIconColor
     * Apply directly to this instance
     */
    open fun setIconColor(drawable: Drawable){
        setIconColor(drawable,this)
    }
}