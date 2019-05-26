package com.domain.skeleton.profile.fragment

import android.os.Bundle
import com.domain.skeleton.core.fragment.HostFragment
import com.domain.skeleton.profile.di.injectFeature

abstract class ProfileFragment : HostFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFeature()
    }
}