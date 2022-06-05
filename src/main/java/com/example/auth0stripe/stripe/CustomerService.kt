package com.example.auth0stripe.stripe

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Customer
import com.stripe.model.billingportal.Session
import com.stripe.param.CustomerCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class CustomerService {

    @Value("\${stripe.keys.private}")
    private val secretStripeKey: String? = null

    /*@Value("\${frontend.origin}")
    private val frontendOrigin: String? = null*/

    @PostConstruct
    fun init() {
        Stripe.apiKey = secretStripeKey
    }

    @Throws(StripeException::class)
    fun getExistingCustomer(email: String): Optional<Customer> {
        val params: MutableMap<String, Any> = HashMap()
        params["email"] = email
        val customers = Customer.list(params).data
        return if (customers.size > 0) Optional.of(customers[0]) else Optional.empty()
    }

    /*@Throws(StripeException::class)
    fun requestCustomerPortal(customerId: String): Session? {
        val params: MutableMap<String, Any> = HashMap()
        params["customer"] = customerId
        params["return_url"] = "$frontendOrigin/profile?customer-portal-redirect"
        return Session.create(params)
    }*/

    @Throws(StripeException::class)
    fun createCustomer(email: String?): String {
        val params = createCustomerParams(email)
        val customer = Customer.create(params)
        return customer.id
    }

    private fun createCustomerParams(email: String?): CustomerCreateParams {
        // Create new Customer
        // Other optional params include:
        // setAddress
        // setName
        // setPhone
        return CustomerCreateParams.Builder()
            .setEmail(email)
            .build()
    }
}
