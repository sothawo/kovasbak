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
    lateinit var name: String
    lateinit var chat: TextArea

    /**
     * called to initialize the UI. adds the chat display and the input controls and asks for the user name.
     * @param vaadinRequest ignored here
     */
    override fun init(vaadinRequest: VaadinRequest?) {
        content = VerticalLayout().apply {
            setWidth(100F, Sizeable.Unit.PERCENTAGE)

            chat = createChatDisplay()
            addComponent(chat)
            addComponent(createInputs())
            askForUserName()
        }
    }

    /**
     * creates the TextArea to display the chat data.
     * @return the TextArea
     */
    private fun createChatDisplay(): TextArea {
        return TextArea().apply {
            isReadOnly = true
            setWidth(100F, Sizeable.Unit.PERCENTAGE)
            rows = 20
        }
    }

    /**
     * creates the input controls to enter the messages to send and the button to submit the send.
     */
    private fun createInputs(): Component {
        return HorizontalLayout().apply {
            setWidth(100F, Sizeable.Unit.PERCENTAGE)

            val message = TextField().apply {
                setWidth(100F, Sizeable.Unit.PERCENTAGE)
            }
            addComponent(message)
            setExpandRatio(message, 1F)

            val button = Button("Send").apply {
                setClickShortcut(ShortcutAction.KeyCode.ENTER)
                addClickListener {
                    sendMessage(message.value)
                    message.clear()
                }
            }
            addComponent(button)
        }
    }

    /**
     * shows a modal dialog to ask for the user name and sets the correspondig field.
     */
    private fun askForUserName() {
        addWindow(Window("your name:").apply {
            isModal = true
            isClosable = false
            isResizable = false
            content = VerticalLayout().apply {
                val nameField = TextField().apply {
                    focus()
                }
                addComponent(nameField)

                val button = Button("OK").apply {
                    setClickShortcut(ShortcutAction.KeyCode.ENTER)
                    addClickListener {
                        name = nameField.value
                        if (!name.isNullOrEmpty()) {
                            close()
                            log.info("name entered: {}", name)
                        }
                    }
                }
                addComponent(button)
            }
            center()
        })
    }

    /**
     * sends the message from the input text fiueld to the backend.
     */
    private fun sendMessage(message: String) {
        log.warn("{} sending message \"{}\"", name, message)
        showMessage(name, message)
    }

    /**
     * adds the received message to the display.
     * @param user the user who sent the message
     * @param message the message from the user
     */
    private fun showMessage(user: String, message: String) {
        log.info("got message \"{}\" from {}", message, user)
        chat.value += user + ": " + message + "\n"
    }

    /**
     * companion object containing the logger.
     */
    companion object {
        val log: Logger = LoggerFactory.getLogger(ChatUI::class.java)
    }
}
