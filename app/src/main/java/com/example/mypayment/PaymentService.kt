package com.example.mypayment

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PaymentService : Service() {

    @Volatile
    private var balance = 1000

    // Implement the AIDL Stub — this is the server-side implementation.
    // The Stub extends Binder and implements IPaymentService,
    // handling IPC marshalling/unmarshalling automatically.
    private val binder = object : IPaymentService.Stub() {
        override fun getBalance(): Int = this@PaymentService.balance

        override fun pay(amount: Int): Boolean {
            val svc = this@PaymentService
            if (amount <= 0 || amount > svc.balance) return false
            svc.balance -= amount
            return true
        }

        override fun topUp(amount: Int) {
            if (amount > 0) {
                this@PaymentService.balance += amount
            }
        }
    }

    // Return the Stub (which is an IBinder) to clients.
    // For same-process calls, the Stub itself is returned.
    // For cross-process calls, the system wraps it in a Proxy on the client side.
    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}
