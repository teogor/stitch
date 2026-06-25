package dev.teogor.stitch.catalog.demo.domain.model

data class TaskModel(
    val id: Long = 0,
    val title: String,
    val isCompleted: Boolean,
)
