package com.domain.skeleton.core.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domain.skeleton.core.ext.AppError
import com.domain.skeleton.core.ext.toAppError
import com.domain.skeleton.core.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected val context: Context by inject()

    private val jobs = ArrayList<Job>()

    val error = MutableLiveData<AppError>()

    fun launch(code: suspend CoroutineScope.() -> Unit,
               onFailure: (suspend CoroutineScope.(Throwable) -> Unit)? = null) {
        run(code, onFailure, Dispatchers.Default)
    }

    fun io(code: suspend CoroutineScope.() -> Unit,
           onFailure: (suspend CoroutineScope.(Throwable) -> Unit)? = null) {
        run(code, onFailure, Dispatchers.IO)
    }

    fun main(code: suspend CoroutineScope.() -> Unit,
             onFailure: (suspend CoroutineScope.(Throwable) -> Unit)? = null) {
        run(code, onFailure, Dispatchers.Main)
    }

    private fun run(code: suspend CoroutineScope.() -> Unit,
                    onFailure: (suspend CoroutineScope.(Throwable) -> Unit)? = null,
                    context: CoroutineContext = Dispatchers.Default) {
        val job = CoroutineScope(context).launch {
            try {
                code.invoke(this)
            } catch (e: Exception) {
                log.e(e)

                if (onFailure != null) {
                    onFailure.invoke(this, e)
                } else {
                    onError(e)
                }
            }
        }

        jobs.add(job)
    }

    override fun onCleared() {
        jobs.forEach { if (!it.isCancelled) it.cancel() }
        super.onCleared()
    }

    protected open fun onError(t: Throwable) = error.postValue(t.toAppError(context))
}