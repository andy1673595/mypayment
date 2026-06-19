package com.example.mypayment

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class PaymentService : Service() {

    // Binder for the client to obtain the Service instance
    inner class PaymentBinder : Binder() {
        fun getService(): PaymentService = this@PaymentService
    }

    private val binder = PaymentBinder()
    private var balance = 1000 // initial balance

    // Called when a client calls bindService().
    // The returned IBinder is delivered to the client via ServiceConnection.onServiceConnected().
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun getBalance(): Int = balance

    fun pay(amount: Int): Boolean {
        if (amount <= 0 || amount > balance) return false
        balance -= amount
        return true
    }

    fun topUp(amount: Int) {
        if (amount > 0) {
            balance += amount
        }
    }
}
