package com.example.mypayment.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mypayment.IPaymentService

class ClientActivity : AppCompatActivity() {

    private var paymentService: IPaymentService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // Since we're in a different app (different process),
            // asInterface() returns a Proxy that marshals calls across processes via Binder IPC.
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
        setContentView(R.layout.activity_client)

        tvBalance = findViewById(R.id.tvBalance)
        tvStatus = findViewById(R.id.tvStatus)

        findViewById<Button>(R.id.btnCheckBalance).setOnClickListener {
            if (isBound) {
                updateBalanceDisplay()
                tvStatus.text = "Query successful (from Client App)"
            }
        }

        findViewById<Button>(R.id.btnPay).setOnClickListener {
            if (isBound) {
                val success = paymentService!!.pay(100)
                tvStatus.text = if (success) "Payment of $100 successful (from Client App)" else "Insufficient balance"
                updateBalanceDisplay()
            }
        }

        findViewById<Button>(R.id.btnTopUp).setOnClickListener {
            if (isBound) {
                paymentService!!.topUp(500)
                tvStatus.text = "Top up $500 successful (from Client App)"
                updateBalanceDisplay()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to the remote PaymentService in the server app using an explicit Intent.
        // We must use setComponent() because implicit intents cannot start services on Android 5+.
        val intent = Intent().apply {
            action = "com.example.mypayment.IPaymentService"
            component = ComponentName(
                "com.example.mypayment",
                "com.example.mypayment.PaymentService"
            )
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

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
