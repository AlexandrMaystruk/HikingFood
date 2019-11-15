package com.gmail.maystruks08.hikingfood.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun String.isolateSpecialSymbolsForRegex(): String =
    this.replace("*", "\\*")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace("[", "\\[")
        .replace("]", "\\]")

fun View.setVisibilityIfNeed(visibility: Int){
    if(this.visibility != visibility){
        this.visibility = visibility
    }
}

fun Fragment.hideKeyboard () {
    val view = this.activity?.currentFocus
    if (view != null) {
        val imm = this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun FragmentManager.transaction (block: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().block().commit()
}

fun Context.toast (text: String = "Some text") {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun DisplayMetrics.toDp (px: Float) = (px/(this.densityDpi/DisplayMetrics.DENSITY_DEFAULT))

fun DisplayMetrics.toPx (dp: Float): Float = (dp*(this.densityDpi/DisplayMetrics.DENSITY_DEFAULT))

fun DisplayMetrics.toDp (px: Int) = (px/(this.densityDpi/DisplayMetrics.DENSITY_DEFAULT))

fun DisplayMetrics.toPx (dp: Int) = (dp*(this.densityDpi/DisplayMetrics.DENSITY_DEFAULT))

fun DisplayMetrics.spToPx (sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this)

fun Context.getDisplayWidth () = this.resources.displayMetrics.widthPixels

fun Context.getDisplayHeight () = this.resources.displayMetrics.heightPixels


fun View.createBitmap(): Bitmap{
    this.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    this.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    this.layout(0, 0, this.measuredWidth, this.measuredHeight)
    val bitmap = Bitmap.createBitmap(this.measuredWidth, this.measuredHeight, Bitmap.Config.ARGB_8888)
    val c = Canvas(bitmap)
    this.layout(this.left, this.top, this.right, this.bottom)
    this.draw(c)
    return bitmap
}

fun View?.takeScreenshot(){
    try {
       this?.let {
            val baseDir = Environment.getExternalStorageDirectory().absolutePath
            val bitmap = it.createBitmap()
            val imageFile = File(baseDir + File.separator + Date() + ".jpg")
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}
