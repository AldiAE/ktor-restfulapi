package com.aldi.ktor

import com.aldi.ktor.routes.todo
import com.aldi.ktor.services.DatabaseFactory
import com.aldi.ktor.services.TodoService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    DatabaseFactory.init()

    val todoService = TodoService()

    install(Routing) {
        todo(todoService)
    }
}

fun main() {
    embeddedServer(Netty, 8000, watchPaths = listOf("Application.kt"), module = Application::module).start(true)
}

