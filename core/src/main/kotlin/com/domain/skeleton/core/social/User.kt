package com.domain.skeleton.core.social

import android.os.Parcel
import android.os.Parcelable

class User(val provider: Providers,
           val accessToken: String,
           val id: String,
           val email: String,
           val name: String,
           val avatar: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            Providers.valueOf(parcel.readString().toUpperCase()),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(provider.value)
        parcel.writeString(accessToken)
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(avatar)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {

        override fun createFromParcel(parcel: Parcel) = User(parcel)

        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }
}