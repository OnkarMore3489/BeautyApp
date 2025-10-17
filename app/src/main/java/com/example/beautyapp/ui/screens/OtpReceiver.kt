package com.example.beautyapp.ui.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class OtpReceiver(
    private val onOtpReceived: (String) -> Unit
) {
    private var smsReceiver: BroadcastReceiver? = null

    fun register(context: Context) {
        val client = SmsRetriever.getClient(context)
        client.startSmsRetriever()
            .addOnSuccessListener { Log.d("OtpReceiver", "SMS Retriever started") }
            .addOnFailureListener { Log.e("OtpReceiver", "Failed to start SMS Retriever", it) }

        smsReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
                    val extras = intent.extras
                    val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
                    if (status?.statusCode == CommonStatusCodes.SUCCESS) {
                        val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
                        message?.let {
                            val otpRegex = Regex("(\\d{4,6})")
                            val match = otpRegex.find(it)
                            val otp = match?.value
                            if (!otp.isNullOrEmpty()) {
                                onOtpReceived(otp)
                            }
                        }
                    }
                }
            }
        }

        val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        ContextCompat.registerReceiver(
            context,
            smsReceiver,
            filter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    fun unregister(context: Context) {
        try {
            smsReceiver?.let {
                context.unregisterReceiver(it)
                smsReceiver = null
            }
        } catch (e: Exception) {
            Log.e("OtpReceiver", "Error unregistering receiver", e)
        }
    }
}
