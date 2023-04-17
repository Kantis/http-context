package com.github.kantis.httpcontext

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

interface HttpContext {
    fun http(request: Request): Response
}

class HttpClient(val delegate: HttpHandler) : HttpContext {
    override fun http(request: Request): Response {
        return delegate(request)
    }
}

class TestHttpContext : HttpContext {
    val responses: Map<Request, Response> = emptyMap()
    val recordedRequests: MutableList<Request> = mutableListOf()

    override fun http(request: Request): Response {
        recordedRequests.add(request)
        return responses[request] ?: Response(Status.NOT_FOUND) // TODO: Make this configurable
    }
}
