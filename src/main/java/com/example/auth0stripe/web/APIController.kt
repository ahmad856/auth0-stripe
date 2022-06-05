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

    @GetMapping(value = ["/private"])
    fun privateEndpoint(): Message {
        return Message("All good. You can see this because you are Authenticated.")
    }

    @GetMapping(value = ["/posts"])
    fun getPosts(): Message {
        return Message("All good. You can see this because you are Authenticated with a Token granted the 'read:posts' scope")
    }

    @GetMapping(value = ["/messages"])
    fun getMessages(): Message {
        return Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope")
    }

}
