package com.sothawo.kovasbak

import com.vaadin.annotations.PreserveOnRefresh
import com.vaadin.annotations.Push
import com.vaadin.event.ShortcutAction
import com.vaadin.server.Sizeable
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.ui.ContentMode
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@SpringUI
@PreserveOnRefresh
@Push
class ChatUI : UI(), KafkaConnectorListener {

    lateinit var user: String
    lateinit var chatDisplay: ChatDisplay

    @Autowired
    lateinit var kafkaConnector: KafkaConnector

    override fun init(vaadinRequest: VaadinRequest?) {
        kafkaConnector.addListener(this)
        chatDisplay = ChatDisplay()
        content = VerticalLayout().apply {
            setSizeFull()
            addComponents(chatDisplay, createInputs())
            setExpandRatio(chatDisplay, 1F)
        }
        askForUserName()
    }

    override fun detach() {
        kafkaConnector.removeListener(this)
        super.detach()
        log.info("session ended for user $user")
    }

    private fun createInputs(): Component {
        return HorizontalLayout().apply {
            setWidth(100F, Sizeable.Unit.PERCENTAGE)
            val messageField = TextField().apply { setWidth(100F, Sizeable.Unit.PERCENTAGE) }
            val button = Button("Send").apply {
                setClickShortcut(ShortcutAction.KeyCode.ENTER)
                addClickListener {
                    kafkaConnector.send(user, messageField.value)
                    messageField.apply { clear(); focus() }
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
                            log.info("user entered: $user")
                        }
                    }
                })
            }
            center()
        })
    }

    override fun chatMessage(user: String, message: String) {
        access { chatDisplay.addMessage(user, message) }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ChatUI::class.java)
    }
}

class ChatDisplay : Panel() {
    val text: Label

    init {
        setSizeFull()
        text = Label().apply { contentMode = ContentMode.HTML }
        content = VerticalLayout().apply { addComponent(text) }
    }

    fun addMessage(user: String, message: String) {
        text.value = when {
            text.value.isNullOrEmpty() -> "<em>$user:</em> $message"
            else -> text.value + "<br/><em>$user:</em> $message"
        }
        scrollTop = Int.MAX_VALUE
    }
}
