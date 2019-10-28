package com.pratik.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pratik.todo.R
import com.pratik.todo.databinding.TodoFragmentLayoutBinding
import com.pratik.todo.viewModel.TodoViewModel
import com.pratik.todo.viewModel.TodoViewModelFactory
import java.util.*

class TodoFragment: Fragment() {
    lateinit var binding: TodoFragmentLayoutBinding
    lateinit var viewModel: TodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.todo_fragment_layout, container, false)
        viewModel = ViewModelProviders.of(this, TodoViewModelFactory())
            .get(TodoViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        initClick()
        initObserver()
    }

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
        })
    }



}