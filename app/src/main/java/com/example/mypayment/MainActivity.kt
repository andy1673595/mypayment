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

    private var paymentService: IPaymentService? = null
    private var isBound = false

    // ServiceConnection: monitors the connection state with the Service
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // Stub.asInterface() returns:
            // - the Stub itself if the caller is in the same process
            // - a Proxy wrapper if the caller is in a different process
            paymentService = IPaymentService.Stub.asInterface(service)
            isBound = true
            updateBalanceDisplay()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            paymentService = null
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
                tvStatus.text = "Query successful"
            }
        }

        findViewById<Button>(R.id.btnPay).setOnClickListener {
            if (isBound) {
                val success = paymentService!!.pay(100)
                tvStatus.text = if (success) "Payment of $100 successful" else "Insufficient balance"
                updateBalanceDisplay()
            }
        }

        findViewById<Button>(R.id.btnTopUp).setOnClickListener {
            if (isBound) {
                paymentService!!.topUp(500)
                tvStatus.text = "Top up $500 successful"
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
        tvBalance.text = "Balance: $${paymentService!!.getBalance()}"
    }
}
