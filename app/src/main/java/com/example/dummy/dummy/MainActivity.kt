package com.example.dummy.dummy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dummy.core.network.domain.java.request.rotate.RotationDirection
import com.example.dummy.core.network.domain.java.request.rotate.api.RotateApi
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "___AppCompatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        RxView.clicks(activity_main_btn_turn_left)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    RotateApi.rotate(RotationDirection.LEFT,activity_main_et_step_count.text.toString().toLongOrNull())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Log.e(TAG, "Success")
                            }, {

                                Log.e(TAG, "failed$it")
                            });
                }, {
                    Log.e(TAG, "failed"+it)
                })

        RxView.clicks(activity_main_btn_turn_right)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    RotateApi.rotate(RotationDirection.LEFT,activity_main_et_step_count.text.toString().toLongOrNull())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Log.e(TAG, "Success")
                            }, {

                                Log.e(TAG, "failed$it")
                            });
                }, {
                    Log.e(TAG, "failed$it")
                })

    }
}
