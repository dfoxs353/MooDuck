package com.example.mooduck.common

interface EventHandler<E> {
    fun obtainEvent(event: E)

}