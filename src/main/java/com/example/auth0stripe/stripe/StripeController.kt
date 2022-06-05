package com.example.auth0stripe.stripe

import com.example.auth0stripe.model.CustomProduct
import com.stripe.exception.StripeException
import com.stripe.model.checkout.Session
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.example.auth0stripe.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = ["api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = ["*"])
class StripeController(private val subscriptionService: SubscriptionService, private val customerService: CustomerService, private val productService: ProductService) {


    @GetMapping(value = ["/getAllProducts"])
    @Throws(StripeException::class)
    fun getAllProductsAPI(): List<CustomProduct?> {
        return productService.getAllProducts()
    }

    /*@PostMapping(value = ["/subscriptionCheckout"])
    @Throws(StripeException::class)
    fun startSubscription(@RequestBody requestBody: Map<String, String>): String {
        val customerEmail = requestBody["customerEmail"]
        val priceId = requestBody["priceId"]
        val existingCustomer = customerService.getExistingCustomer(customerEmail!!)
        val customerId = if (existingCustomer.isPresent) existingCustomer.get().id else customerService.createCustomer(customerEmail)
        val params = subscriptionService.createSubscriptionParams(customerId, priceId)
        return Session.create(params).url //redirect to this url
    }*/

    /*@PostMapping(value = ["/createCustomerPortalSession"])
    @Throws(StripeException::class)
    fun createCustomerPortalSession(@RequestBody requestBody: Map<String, String>): ResponseEntity<String> {
        val customerEmail = requestBody["customerEmail"]
        val existingCustomer = customerService.getExistingCustomer(customerEmail!!)
        if (existingCustomer.isEmpty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found")
        }
        val customerId = existingCustomer.get().id
        val session = customerService.requestCustomerPortal(customerId)
        return ResponseEntity.ok(session!!.url) //redirect to this url
    }*/

    @ExceptionHandler(StripeException::class)
    fun handleError(model: Model?, ex: StripeException): String {
        return ex.toString()
    }
}