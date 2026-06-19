package com.example.mypayment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var paymentService: PaymentService
    private var isBound = false

    // ServiceConnection: monitors the connection state with the Service
    private val connection = object : ServiceConnection {
        // Called when binding succeeds; service is the IBinder returned by PaymentService.onBind()
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as PaymentService.PaymentBinder
            paymentService = binder.getService()
            isBound = true
            updateBalanceDisplay()
        }

        // Called when the Service crashes or is killed (not triggered by normal unbind)
        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    private lateinit var tvBalance: TextView
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvBalance = findViewById(R.id.tvBalance)
        tvStatus = findViewById(R.id.tvStatus)

        findViewById<Button>(R.id.btnCheckBalance).setOnClickListener {
            if (isBound) {
                updateBalanceDisplay()
                tvStatus.text = "查詢成功"
            }
        }

        findViewById<Button>(R.id.btnPay).setOnClickListener {
            if (isBound) {
                val success = paymentService.pay(100)
                tvStatus.text = if (success) "付款 $100 成功" else "餘額不足，付款失敗"
                updateBalanceDisplay()
            }
        }

        findViewById<Button>(R.id.btnTopUp).setOnClickListener {
            if (isBound) {
                paymentService.topUp(500)
                tvStatus.text = "儲值 $500 成功"
                updateBalanceDisplay()
            }
        }
    }

    // Bind to the Service in onStart
    override fun onStart() {
        super.onStart()
        val intent = Intent(this, PaymentService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    // Unbind from the Service in onStop
    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private fun updateBalanceDisplay() {
        tvBalance.text = "餘額：$${paymentService.getBalance()}"
    }
}
