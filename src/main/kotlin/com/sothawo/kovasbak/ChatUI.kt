package com.sothawo.kovasbak

import com.vaadin.event.ShortcutAction
import com.vaadin.server.Sizeable
import com.vaadin.server.VaadinRequest
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

    /**
     * called to initialize the UI. adds the chat display and the input controls and asks for the user's name.
     * @param vaadinRequest ignored here
     */
    override fun init(vaadinRequest: VaadinRequest?) {
        content = VerticalLayout().apply {
            setSizeFull()
            chatDisplay = ChatDisplay()
            addComponents(chatDisplay, createInputs())
            setExpandRatio(chatDisplay, 1F)
        }
        askForUserName()
    }

    /**
     * creates the input controls to enter the messages to send and the button to submit the send.
     */
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

    /**
     * shows a modal dialog to ask for the user user and sets the correspondig field.
     */
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

    /**
     * sends the message from the input text field to the backend.
     */
    private fun sendMessage(message: String) {
        log.warn("{} sending message \"{}\"", user, message)
        showMessage(user, message)
    }

    /**
     * adds the received message to the display.
     * @param user the user who sent the message
     * @param message the message from the user
     */
    private fun showMessage(user: String, message: String) {
        log.info("got message \"{}\" from {}", message, user)
        chatDisplay.addMessage(user, message)
    }

    /**
     * companion object containing the logger.
     */
    companion object {
        val log: Logger = LoggerFactory.getLogger(ChatUI::class.java)
    }
}
