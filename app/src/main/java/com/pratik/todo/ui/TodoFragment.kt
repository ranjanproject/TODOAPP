package com.pratik.todo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pratik.todo.data.database.WorkData
import com.pratik.todo.data.database.WorkDatabase
import com.pratik.todo.databinding.TodoFragmentLayoutBinding
import com.pratik.todo.viewModel.TodoViewModel
import com.pratik.todo.viewModel.TodoViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import io.reactivex.CompletableObserver
import io.reactivex.Completable
import io.reactivex.functions.Action


class TodoFragment: Fragment() {
    lateinit var binding: TodoFragmentLayoutBinding
    lateinit var viewModel: TodoViewModel
    private var workDatabase: WorkDatabase? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, com.pratik.todo.R.layout.todo_fragment_layout, container, false)
        viewModel = ViewModelProviders.of(this, TodoViewModelFactory())
            .get(TodoViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        initDatabse()
        initClick()
        initObserver()
        getAllWorks()
    }

    private fun initDatabse() {
        workDatabase = WorkDatabase.getDatabase(context!!)
    }

    private var temp: String = ""
    private fun initClick(){
        binding.getWeather.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

//                viewModel.checkName(binding.enterName.text.toString())
                viewModel.getTemp(binding.enterName.text.toString())
//                if( viewModel.checkName(binding.enterName.text.toString())) {
//                    Toast.makeText(context, binding.enterName.text, Toast.LENGTH_LONG).show()
//                }
//                else{
//                    Toast.makeText(context, "empty string", Toast.LENGTH_LONG).show()
//
//                }
            }
        })
        binding.saveWeather.setOnClickListener ( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                insertWork(binding.enterName.text.toString(), temp)
            }
        })
    }

    fun initObserver(){
      viewModel.check.observe(this@TodoFragment, androidx.lifecycle.Observer { x ->
          if(x.equals("0")){
              Toast.makeText(context, "empty string", Toast.LENGTH_LONG).show()
          }
          else{
              Toast.makeText(context, "your name is "+ x +" long", Toast.LENGTH_LONG).show()
          }
      })

        viewModel.temp.observe(this@TodoFragment, androidx.lifecycle.Observer {  temp ->
           binding.showData.text = "current temperature of " +binding.enterName.text +" is "+temp + "C"
           this.temp = temp
        })
    }



    private fun getAllWorks(){
        var disposable = workDatabase?.workDAO()?.getAll()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(this::handleResponse, this::handleError)
        mCompositeDisposable?.add(disposable!!)
    }
    private fun handleResponse(data: List<WorkData>){
      val x = data
    }
    private fun handleError(error: Throwable) {

        Log.d("checkError", error.localizedMessage)
    }

    private fun insertWork(cityName: String , temp: String) {

        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                val user = WorkData(0, cityName, temp)
                workDatabase!!.workDAO().insertWork(user)
            }
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                  val x = true
                }

                override fun onError(e: Throwable) {
                   val x = false
                }
            })
    }


}