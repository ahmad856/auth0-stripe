package com.example.auth0stripe.web

import com.example.auth0stripe.model.Message
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = ["*"])
class APIController {

    @GetMapping(value = ["/public"])
    fun publicEndPoint(): Message {
        return Message("All good. You DO NOT need to be authenticated to call /api/public.")
    }

}
