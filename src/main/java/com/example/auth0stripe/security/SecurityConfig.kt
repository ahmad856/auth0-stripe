package com.example.auth0stripe.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import java.time.Duration

@EnableWebSecurity
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${auth0.audience}")
    lateinit var audience: String

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    lateinit var issuer: String

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .mvcMatchers("/api/public", "/api/getAllProducts").permitAll()
            .mvcMatchers("/api/private", "/api/subscriptionCheckout", "/api/createCustomerPortalSession").authenticated()
            .mvcMatchers("/api/posts").hasAuthority("SCOPE_read:posts")
            .mvcMatchers("/api/messages").hasAuthority("SCOPE_read:messages")
            .and().cors()
            .and().oauth2ResourceServer().jwt()
    }

    @Bean
    open fun jwtDecoder(builder: RestTemplateBuilder): JwtDecoder {
        val rest = builder
            .setConnectTimeout(Duration.ofSeconds(5))
            .setReadTimeout(Duration.ofSeconds(5))
            .build()

        val jwkSetUri = "$issuer.well-known/jwks.json"
        val jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).restOperations(rest).build()

        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuer)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)

        jwtDecoder.setJwtValidator(withAudience)

        return jwtDecoder
    }

}