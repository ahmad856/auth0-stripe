package com.example.auth0stripe.stripe

import com.example.auth0stripe.model.CustomPrice
import com.example.auth0stripe.model.CustomProduct
import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Price
import com.stripe.model.Product
import com.stripe.param.PriceListParams
import com.stripe.param.ProductListParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ProductService {

    @Value("\${stripe.keys.private}")
    private val secretStripeKey: String? = null

    @PostConstruct
    fun init() {
        Stripe.apiKey = secretStripeKey
    }

    private fun createProductListParams(): ProductListParams? {
        return ProductListParams.Builder()
            .setLimit(10L)
            .build()
    }

    private fun createPriceListParams(productId: String): PriceListParams? {
        return PriceListParams.Builder()
            .setProduct(productId)
            .build()
    }

    @Throws(StripeException::class)
    private fun getProductPrices(productId: String): List<CustomPrice> {
        val prices: MutableList<CustomPrice> = ArrayList()
        val priceCollection = Price.list(createPriceListParams(productId))
        for (price in priceCollection.data) {
            val amount = (price.unitAmount / 100).toInt()
            val decimal = (price.unitAmount % 100).toInt()
            prices.add(CustomPrice(price.id, price.currency, "$amount.$decimal", price.recurring.interval))
        }
        return prices
    }

    @Throws(StripeException::class)
    fun getAllProducts(): List<CustomProduct> {
        val products: MutableList<CustomProduct> = ArrayList()
        val productCollection = Product.list(createProductListParams())
        for (product in productCollection.data) {
            products.add(CustomProduct(product.description, product.id, product.images, product.name, getProductPrices(product.id)))
        }
        return products
    }
}