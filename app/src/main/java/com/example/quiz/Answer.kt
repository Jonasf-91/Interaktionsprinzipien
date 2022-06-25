package com.example.quiz

import android.os.Parcel
import android.os.Parcelable

class Answer(val name: String? , private val isCorrect: Boolean, private var isSelected: Boolean = false) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    public fun isSelectionCorrect() : Boolean {
        return isCorrect == isSelected
    }
    public fun switchSelection() : Boolean {
        isSelected = !isSelected
        return  isSelected
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (isCorrect) 1 else 0)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Answer> {
        override fun createFromParcel(parcel: Parcel): Answer {
            return Answer(parcel)
        }

        override fun newArray(size: Int): Array<Answer?> {
            return arrayOfNulls(size)
        }
    }
}