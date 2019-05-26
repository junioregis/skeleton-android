package com.domain.skeleton.core.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController

class Navigator(private val context: Context) {

    fun nav(navController: NavController,
            resId: Int,
            args: Bundle? = null) {
        navController.navigate(resId, args)
    }

    fun nav(screen: Screen,
            args: Bundle? = null,
            noHistory: Boolean = true) = internalIntent(context, screen.tag, args, noHistory)

    private fun internalIntent(context: Context,
                               action: String,
                               args: Bundle? = null,
                               noHistory: Boolean = true) {
        val intent = Intent(action).setPackage(context.packageName)

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        if (args != null) {
            intent.putExtras(args)
        }

        if (noHistory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        context.startActivity(intent)
    }
}