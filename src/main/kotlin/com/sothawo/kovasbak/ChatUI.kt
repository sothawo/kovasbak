package com.sothawo.kovasbak

import com.vaadin.event.ShortcutAction
import com.vaadin.server.Sizeable
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.ui.ContentMode
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@SpringUI
class ChatUI : UI() {
    lateinit var user: String
    lateinit var chatDisplay: ChatDisplay

    override fun init(vaadinRequest: VaadinRequest?) {
        content = VerticalLayout().apply {
            setSizeFull()
            chatDisplay = ChatDisplay()
            addComponents(chatDisplay, createInputs())
            setExpandRatio(chatDisplay, 1F)
        }
        askForUserName()
    }

    private fun createInputs(): Component {
        return HorizontalLayout().apply {
            setWidth(100F, Sizeable.Unit.PERCENTAGE)
            val messageField = TextField().apply {
                setWidth(100F, Sizeable.Unit.PERCENTAGE)
            }
            val button = Button("Send").apply {
                setClickShortcut(ShortcutAction.KeyCode.ENTER)
                addClickListener {
                    sendMessage(messageField.value)
                    messageField.clear()
                    messageField.focus()
                }
            }
            addComponents(messageField, button)
            setExpandRatio(messageField, 1F)
        }
    }

    private fun askForUserName() {
        addWindow(Window("your user:").apply {
            isModal = true
            isClosable = false
            isResizable = false
            content = VerticalLayout().apply {
                val nameField = TextField().apply { focus() }
                addComponent(nameField)
                addComponent(Button("OK").apply {
                    setClickShortcut(ShortcutAction.KeyCode.ENTER)
                    addClickListener {
                        user = nameField.value
                        if (!user.isNullOrEmpty()) {
                            close()
                            log.info("user entered: {}", user)
                        }
                    }
                })
            }
            center()
        })
    }

    private fun sendMessage(message: String) {
        log.warn("{} sending message \"{}\"", user, message)
        showMessage(user, message)
    }

    private fun showMessage(user: String, message: String) {
        log.info("got message \"{}\" from {}", message, user)
        chatDisplay.addMessage(user, message)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ChatUI::class.java)
    }
}

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
