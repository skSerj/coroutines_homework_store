package com.sourceit.homework_corountines_store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var numOfGoodsInStoreOnStart = AtomicInteger(15000)
        val maxNumOfGoods = numOfGoodsInStoreOnStart.toInt() * 4
        var balanceOfGoodsInTime = AtomicInteger(0)
        val stringBuffer = StringBuffer()
        var time: Long
        var numOfGods: Int
        txt_available_goods.text = "$numOfGoodsInStoreOnStart"
        GlobalScope.launch {
            while (balanceOfGoodsInTime.toInt() in 0..maxNumOfGoods) {

                time = (10000..15000L).random()
                delay(time)
                numOfGods = (3000..6000).random()
                balanceOfGoodsInTime = if (balanceOfGoodsInTime.toInt() == 0) {
                    AtomicInteger(numOfGoodsInStoreOnStart.toInt() + numOfGods)
                } else {
                    AtomicInteger(balanceOfGoodsInTime.toInt() + numOfGods)
                }
                stringBuffer.append(", поставка: $numOfGods за ${time / 1000} сек")
                withContext(Dispatchers.Main) {
                    txt_logbook.text = "$stringBuffer"
                    txt_available_goods.text = "$balanceOfGoodsInTime"
                }
            }
        }
        GlobalScope.launch {
            while (balanceOfGoodsInTime.toInt() in 0..maxNumOfGoods) {
                time = (1000..5000L).random()
                delay((2000..5000L).random())
                numOfGods = (1..5).random()
                balanceOfGoodsInTime = if (balanceOfGoodsInTime.toInt() == 0) {
                    AtomicInteger(numOfGoodsInStoreOnStart.toInt() - numOfGods)
                } else {
                    AtomicInteger(balanceOfGoodsInTime.toInt() - numOfGods)
                }
                stringBuffer.append(", приобрели: $numOfGods за ${time / 1000} сек ")
                withContext(Dispatchers.Main) {
                    txt_logbook.text = "$stringBuffer"
                    txt_available_goods.text = "$balanceOfGoodsInTime"
                }
            }
        }
    }
}