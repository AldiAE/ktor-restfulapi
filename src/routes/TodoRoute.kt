package com.aldi.ktor.routes

import com.aldi.ktor.models.NewTodo
import com.aldi.ktor.models.Response
import com.aldi.ktor.services.TodoService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.text.get

fun Route.todo(todoService: TodoService) {
    route("/todo") {
        get {
            call.respond(todoService.getAllTodos())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0

            val todo = todoService.getTodoById(id)
            if (todo == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(todo)
        }

        post {
            val todo = call.receive<NewTodo>()
            val inserted = todoService.addTodo(todo)

            if (inserted != null) {
                call.respond(inserted)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0
            val todo = call.receive<NewTodo>()
            val updated = todoService.updateTodo(id, todo)

            if (updated != null) {
                call.respond(updated)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0
            val success = todoService.deleteTodo(id)

            if (success) {
                call.respond(Response(message = "Delete successful"))
            } else {
                call.respond(HttpStatusCode.NotFound, "Delete Failed")
            }
        }
    }
}