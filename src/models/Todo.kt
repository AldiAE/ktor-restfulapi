package com.aldi.ktor.models

import org.jetbrains.exposed.sql.Table

object TodoTable : Table(name = "todo") {
    val id = integer("id").autoIncrement()
    val judul = varchar("judul", 255)
    val nama = varchar("nama", 255)
    val isi = varchar("isi", 255)

    override val primaryKey = PrimaryKey(id)
}

data class Todo(
    val id: Int,
    val judul: String,
    val nama: String,
    val isi: String
)

data class NewTodo(
    val judul: String,
    val nama: String,
    val isi: String
)