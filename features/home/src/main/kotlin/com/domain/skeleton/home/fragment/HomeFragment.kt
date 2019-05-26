package com.domain.skeleton.home.fragment

import android.os.Bundle
import com.domain.skeleton.core.fragment.HostFragment
import com.domain.skeleton.home.di.injectFeature

abstract class HomeFragment : HostFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFeature()
    }
}