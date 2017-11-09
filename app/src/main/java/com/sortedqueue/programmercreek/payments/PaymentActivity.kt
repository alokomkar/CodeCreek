package com.sortedqueue.programmercreek.payments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.razorpay.Checkout
import com.sortedqueue.programmercreek.R
import kotlinx.android.synthetic.main.activity_payment.*
import android.widget.Toast
import com.razorpay.PaymentResultListener
import com.sortedqueue.programmercreek.util.CommonUtils
import org.json.JSONObject



class PaymentActivity : AppCompatActivity(), PaymentResultListener {


    private val TAG = PaymentActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        Checkout.preload(this@PaymentActivity)
        btn_pay.setOnClickListener{
            startPayment()
        }
    }

    private fun startPayment() {

        val checkout = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", "100")

            val preFill = JSONObject()
            preFill.put("email", "test@razorpay.com")
            preFill.put("contact", "9876543210")

            options.put("prefill", preFill)

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_SHORT)
                    .show()
            e.printStackTrace()
        }

    }

    override fun onPaymentError(code: Int, response: String?) {
        CommonUtils.displaySnackBar(this, "Unable to complete payment : " + response)
        Log.e(TAG, "onPaymentError : $code : $response")

    }

    override fun onPaymentSuccess(paymentId: String?) {
        CommonUtils.displayToast(this, "Payment Success : " + paymentId)
    }
}
