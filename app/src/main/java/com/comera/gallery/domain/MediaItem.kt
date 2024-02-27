package com.comera.gallery.domain

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class MediaItem(
    val id: Long,
    val contentUri: Uri,
    val displayName: String,
    val dateAdded: Long,
    val bucketId: Long,
    val bucketName: String,
    val isVideo: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        Uri.parse(parcel.readString()),
        parcel.readString() ?: "" ,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(contentUri.toString())
        parcel.writeString(displayName)
        parcel.writeLong(dateAdded)
        parcel.writeLong(bucketId)
        parcel.writeString(bucketName)
        parcel.writeByte(if (isVideo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaItem> {
        override fun createFromParcel(parcel: Parcel): MediaItem {
            return MediaItem(parcel)
        }

        override fun newArray(size: Int): Array<MediaItem?> {
            return arrayOfNulls(size)
        }
    }
}
