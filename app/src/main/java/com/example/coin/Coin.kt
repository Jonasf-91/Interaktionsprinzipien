package com.example.coin.com.example.coin

import android.graphics.Color
import android.graphics.Path
import android.os.Parcel
import android.os.Parcelable

class Coin() :Parcelable{
    var fillColor = Color.WHITE
    var strokeColor = manipulateColor(fillColor, 0.8f)
    var corners = 8
    var print = Path()

    constructor(parcel: Parcel) : this() {
        fillColor = parcel.readInt()
        strokeColor = parcel.readInt()
        corners = parcel.readInt()

    }

    fun setColor(color:Int){
        fillColor = color
        strokeColor = manipulateColor(fillColor, 0.8f)
    }

    fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(
            a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255)
        )
    }

    override fun describeContents() : Int{
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(fillColor)
        dest?.writeInt(strokeColor)
        dest?.writeInt(corners)


    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }


}

