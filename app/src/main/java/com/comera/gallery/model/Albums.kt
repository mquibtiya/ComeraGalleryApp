package com.comera.gallery.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Albums(
    var folderName: String?,
    var folderPath: String?,
    var imgCount: Int,
    var isVideo: Boolean,
    var latestMediaItemContentUri: Uri
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        Uri.parse(parcel.readString())
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(folderName)
        parcel.writeString(folderPath)
        parcel.writeInt(imgCount)
        parcel.writeByte(if (isVideo) 1 else 0)
        parcel.writeString(latestMediaItemContentUri.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Albums> {
        override fun createFromParcel(parcel: Parcel): Albums {
            return Albums(parcel)
        }

        override fun newArray(size: Int): Array<Albums?> {
            return arrayOfNulls(size)
        }
    }
}