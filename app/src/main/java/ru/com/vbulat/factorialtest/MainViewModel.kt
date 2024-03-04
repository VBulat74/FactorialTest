package ru.com.vbulat.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {

        _state.value = Progress

        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        viewModelScope.launch {
            val number = value.toLong()

            val result = factorial(number)

            _state.value = Factorial(value = result)
        }

    }

    private suspend fun factorial(number: Long): String {
        return suspendCoroutine {
            thread {
                var result = BigInteger.ONE
                for (i in 1.. number) {
                    result = result.multiply(BigInteger.valueOf(i))
                }

                it.resumeWith(Result.success(result.toString()))
            }
        }
    }

}