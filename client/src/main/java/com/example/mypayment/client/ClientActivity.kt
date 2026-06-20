package com.example.mypayment.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypayment.IPaymentService

class ClientActivity : ComponentActivity() {

    private val viewModel: PaymentViewModel by viewModels()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            viewModel.paymentService = IPaymentService.Stub.asInterface(service)
            viewModel.checkBalance()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            viewModel.paymentService = null
        }
    }

    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PaymentScreen(viewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent().apply {
            action = "com.example.mypayment.IPaymentService"
            component = ComponentName(
                "com.example.mypayment",
                "com.example.mypayment.PaymentService"
            )
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
        isBound = true
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}

@Composable
fun PaymentScreen(viewModel: PaymentViewModel) {
    val balance by viewModel.balance.observeAsState("Not connected")
    val status by viewModel.status.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "AIDL Client App",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = balance,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        Button(
            onClick = { viewModel.checkBalance() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check Balance")
        }

        Button(
            onClick = { viewModel.pay() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pay $100")
        }

        Button(
            onClick = { viewModel.topUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Top Up $500")
        }

        if (status.isNotEmpty()) {
            Text(
                text = status,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
