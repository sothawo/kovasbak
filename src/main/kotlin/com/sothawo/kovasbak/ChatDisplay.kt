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
        text.value = when {
            text.value.isNullOrEmpty() -> "<em>$user:</em> $message"
            else -> text.value + "<br/><em>$user:</em> $message"
        }
        scrollTop = Int.MAX_VALUE
    }
}
