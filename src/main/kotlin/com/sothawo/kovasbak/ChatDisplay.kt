package com.sothawo.kovasbak

import com.vaadin.shared.ui.ContentMode
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.VerticalLayout

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
class ChatDisplay : Panel() {
    val text: Label

    init {
        setSizeFull()
        text = Label().apply {
            contentMode = ContentMode.HTML
        }
        content = VerticalLayout().apply {
            addComponent(text)
        }
    }

    fun addMessage(user: String, message: String) {
        val userMessage = user + ": " + message
        text.value = when {
            text.value.isNullOrEmpty() -> userMessage
            else -> text.value + "<br/>" + userMessage
        }
        scrollTop = Int.MAX_VALUE
    }
}
