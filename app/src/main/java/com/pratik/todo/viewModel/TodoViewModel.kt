package com.pratik.todo.viewModel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratik.todo.data.model.WeatherModel
import com.pratik.todo.repository.TodoRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TodoViewModel: ViewModel(){

    var check = MutableLiveData<String>()
    private val _temp = MutableLiveData<String>()
    val temp: LiveData<String> = _temp
    private val repository = TodoRepo()
    private var mCompositeDisposable: CompositeDisposable? = null

    fun checkName(name: String){
       if(TextUtils.isEmpty(name)) {
           check.value = "0"
       }
        check.value = name.length.toString()

    }
    fun getTemp(name: String){
        var disposable = repository.weatherData(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
        mCompositeDisposable?.add(disposable)
    }
    private fun handleResponse(weatherModel: WeatherModel) {
       val x= "%.2f".format(weatherModel.main.temp - 273.15).toDouble()
        _temp.value = x.toString()
    }

    private fun handleError(error: Throwable) {

        Log.d("checkError", error.localizedMessage)
    }

}