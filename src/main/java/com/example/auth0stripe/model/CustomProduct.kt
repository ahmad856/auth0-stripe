package com.example.auth0stripe.model

class CustomProduct(val description: String, val productId: String, val images: List<String>, val name: String, val prices: List<CustomPrice>)
