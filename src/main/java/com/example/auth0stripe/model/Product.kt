package com.example.auth0stripe.model

class Product(val description: String, val productId: String, val images: List<String>, val prices: List<Price>)
