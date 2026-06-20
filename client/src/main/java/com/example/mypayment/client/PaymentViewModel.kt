package com.example.mypayment.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypayment.IPaymentService

class PaymentViewModel : ViewModel() {

    private val _balance = MutableLiveData<String>("Not connected")
    val balance: LiveData<String> = _balance

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    var paymentService: IPaymentService? = null

    fun checkBalance() {
        val service = paymentService ?: return
        _balance.value = "Balance: $${service.getBalance()}"
        _status.value = "Query successful (from Client App)"
    }

    fun pay() {
        val service = paymentService ?: return
        val success = service.pay(100)
        _status.value = if (success) "Payment of $100 successful (from Client App)" else "Insufficient balance"
        _balance.value = "Balance: $${service.getBalance()}"
    }

    fun topUp() {
        val service = paymentService ?: return
        service.topUp(500)
        _status.value = "Top up $500 successful (from Client App)"
        _balance.value = "Balance: $${service.getBalance()}"
    }
}
