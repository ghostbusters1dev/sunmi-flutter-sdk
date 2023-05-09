package com.sunmi.sunmi_flutter_sdk

import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel
import com.sunmi.pay.hardware.aidlv2.readcard.ReadCardOptV2
import sunmi.paylib.SunmiPayKernel
import android.os.Bundle



class PaymentEngine(private val activity: MainActivity) : FlutterPlugin {


    private lateinit var context: Context
    private var methodResult: MethodChannel.Result? = null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        val methodChannel = MethodChannel(binding.binaryMessenger, "payment-engine")
        methodChannel.setMethodCallHandler(methodCallHandler)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    private val methodCallHandler = MethodChannel.MethodCallHandler { call, result ->
        methodResult = result
        when (call.method) {
            "startScan" -> startScan()
            else -> result.notImplemented()
        }
    }

    private fun startScan() {

        var mSMPayKernel: SunmiPayKernel? = null
        mSMPayKernel = SunmiPayKernel.getInstance()
        mSMPayKernel!!.initPaySDK(context, object : SunmiPayKernel.ConnectCallback {
            override fun onDisconnectPaySDK() {}
            override fun onConnectPaySDK() {
                try {
                    BaseApp.mReadCardOptV2 = mSMPayKernel!!.mReadCardOptV2
                    BaseApp.mEMVOptV2 = mSMPayKernel!!.mEMVOptV2
                    BaseApp.mPinPadOptV2 = mSMPayKernel!!.mPinPadOptV2
                    BaseApp.mBasicOptV2 = mSMPayKernel!!.mBasicOptV2
                    BaseApp.mSecurityOptV2 = mSMPayKernel!!.mSecurityOptV2



                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

        val mEMVOptV2 = mSMPayKernel?.mEMVOptV2
        val mReadCardOptV2 = mSMPayKernel?.mReadCardOptV2



        try {
            mEMVOptV2?.abortTransactProcess()
            mEMVOptV2?.initEmvProcess()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun returnScanResult(type: String? = null, value: String? = null) {
        val result = methodResult
        if (result != null) {
            if (value != null) {
                val map = HashMap<String, String?>()
                map["type"] = type
                map["value"] = value
                result.success(map)
            } else {
                result.error("-100", "Scan failed", "Scan failed")
            }
        }
    }

}
