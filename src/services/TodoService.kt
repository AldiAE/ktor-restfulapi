package com.aldi.ktor.services

import com.aldi.ktor.models.NewTodo
import com.aldi.ktor.models.Todo
import com.aldi.ktor.models.TodoTable
import com.aldi.ktor.services.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class TodoService {

    suspend fun getAllTodos(): List<Todo> = dbQuery {
        TodoTable.selectAll().orderBy(TodoTable.id to SortOrder.ASC).map { it.toModel() }
    }

    suspend fun getTodoById(id: Int): Todo? = dbQuery {
        TodoTable.select { TodoTable.id eq id }.mapNotNull { it.toModel() }.singleOrNull()
    }

    suspend fun addTodo(todo: NewTodo): Todo? {
        var key = 0
        dbQuery {
            key = (TodoTable.insert {
                it[judul] = todo.judul
                it[nama] = todo.nama
                it[isi] = todo.isi
            } get TodoTable.id)
        }

        return getTodoById(key)
    }

    suspend fun updateTodo(id: Int, todo: NewTodo): Todo? {
        dbQuery {
            TodoTable.update({ TodoTable.id eq id }) {
                it[judul] = todo.judul
                it[nama] = todo.nama
                it[isi] = todo.isi
            }
        }

        return getTodoById(id)
    }

    suspend fun deleteTodo(id: Int): Boolean = dbQuery {
        TodoTable.deleteWhere { TodoTable.id eq id } > 0
    }

    private fun ResultRow.toModel(): Todo {
        return Todo(
            id = this[TodoTable.id],
            judul = this[TodoTable.judul],
            nama = this[TodoTable.nama],
            isi = this[TodoTable.isi]
        )
    }
}