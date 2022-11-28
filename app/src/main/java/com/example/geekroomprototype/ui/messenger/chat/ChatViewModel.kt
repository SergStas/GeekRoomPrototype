package com.example.geekroomprototype.ui.messenger.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ChatData
import com.example.domain.models.MessageData
import com.example.domain.models.UserData
import com.example.domain.usecases.feed.article.MarkMessageAsReadUseCase
import com.example.domain.usecases.login.GetLoggedInUserUseCase
import com.example.domain.usecases.messenger.chat.CreateMessageUseCase
import com.example.domain.usecases.messenger.chat.GetMessagesForChatUseCase
import com.example.geekroomprototype.ui.messenger.chat.models.ChatMessageItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getMessagesForChat: GetMessagesForChatUseCase,
    private val getLoggedInUser: GetLoggedInUserUseCase,
    private val createMessage: CreateMessageUseCase,
    private val markMessageAsRead: MarkMessageAsReadUseCase,
): ViewModel() {
    private val _state = MutableLiveData<State>(State.Waiting)
    val state: LiveData<State> get() = _state

    private val _title = MutableLiveData("")
    val title: LiveData<String> get() = _title

    private var chat: ChatData? = null

    fun loadMessages(chatData: ChatData) {
        viewModelScope.launch {
            chat = chatData
            _title.value = chatData.title
            _state.value = State.Loading
            val messages = getAndReadMessages()
            _state.value = State.Loaded(messages.map { mapToItem(it) }.reversed())
        }
    }

    fun createMessage(content: String) {
        viewModelScope.launch {
            val message = MessageData(
                sender = getLoggedInUser()!!,
                content = content,
                sentTime = System.currentTimeMillis(),
                readUsers = listOf(getLoggedInUser()!!),
            )
            createMessage.invoke(chat!!, message)
            loadMessages(chat!!)
        }
    }

    private suspend fun getAndReadMessages(): List<MessageData> {
        val user = getLoggedInUser()!!
        getMessagesForChat(chat!!).forEach {
            if (user.username !in it.readUsers.map(UserData::username)) {
                markMessageAsRead(it)
            }
        }
        return getMessagesForChat(chat!!)
    }

    private suspend fun mapToItem(messageData: MessageData): ChatMessageItem {
        val user = getLoggedInUser()!!
        val isSelf = messageData.sender.username == user.username
        val senderName = messageData.sender.username.takeIf { chat!!.isGroup }
        val isRead = messageData.readUsers
            .map(UserData::username)
            .any { it != user.username }
            .takeIf { isSelf }
        return ChatMessageItem(
            message = messageData.content,
            isSelf = isSelf,
            senderAvatarUrl = messageData.sender.avatarUrl,
            senderName = senderName,
            isRead = isRead,
        )
    }

    sealed class State {
        object Waiting: State()
        object Loading: State()
        data class Loaded(val messages: List<ChatMessageItem>): State()
    }
}