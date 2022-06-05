package com.example.auth0stripe.stripe

import com.stripe.Stripe
import com.stripe.param.checkout.SessionCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class SubscriptionService {

    @Value("\${stripe.keys.private}")
    private val secretStripeKey: String? = null

    /*@Value("\${frontend.origin}")
    private val frontendOrigin: String? = null*/

    @PostConstruct
    fun init() {
        Stripe.apiKey = secretStripeKey
    }

    /*fun createSubscriptionParams(customerId: String?, priceId: String?): SessionCreateParams? {
        // Create new Checkout Session for the order
        // Other optional params include:
        // setBillingAddressCollection - to display billing address details on the page
        // setCustomer - if you have an existing Stripe Customer ID
        // setCustomerEmail - lets you prefill the email input in the form
        return SessionCreateParams.Builder()
            .setCustomer(customerId)
            .setSuccessUrl("$frontendOrigin/profile?payment-success") //to be routed after success
            .setCancelUrl("$frontendOrigin/profile?payment-canceled") //to be routed in case of cancellation
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
            .addLineItem(
                SessionCreateParams.LineItem.Builder()
                    .setQuantity(1L)
                    .setPrice(priceId)
                    .build()
            ).build()
    }*/
}
