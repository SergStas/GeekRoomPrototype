package com.example.geekroomprototype.ui.messenger

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ChatData
import com.example.domain.models.UserData
import com.example.domain.usecases.login.GetLoggedInUserUseCase
import com.example.domain.usecases.messenger.QueryUserChatsUseCase
import com.example.geekroomprototype.ui.messenger.models.MessengerChatBarItem
import com.example.geekroomprototype.util.extensions.formatDate
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessengerViewModel @Inject constructor(
    private val queryUserChats: QueryUserChatsUseCase,
    private val getLoggedInUser: GetLoggedInUserUseCase,
    private val context: Context,
): ViewModel() {
    private val _chats = MutableLiveData<State>(State.Loading)
    val chats: LiveData<State> get() = _chats

    private val _chatToOpen = MutableLiveData<OpenChatState>(OpenChatState.Waiting)
    val chatToOpen: LiveData<OpenChatState> get() = _chatToOpen

    fun loadChats() {
        viewModelScope.launch {
            _chats.value = State.Loaded(
                queryUserChats()
                    .sortedByDescending { chat ->
                        if (chat.messages.isEmpty()) {
                            System.currentTimeMillis()
                        } else {
                            chat.messages.maxOf { it.sentTime }
                        }
                    }
                    .map { mapToRvItem(it) },
            )
        }
    }

    private fun onOpen(item: MessengerChatBarItem) {
        viewModelScope.launch {
            val chat = queryUserChats().first { it.title == item.title }
            _chatToOpen.value = OpenChatState.Open(chat)
            _chatToOpen.value = OpenChatState.Waiting
        }
    }

    private suspend fun mapToRvItem(data: ChatData): MessengerChatBarItem {
        val user = getLoggedInUser()!!
        return MessengerChatBarItem(
            title = data.title,
            avatarUrl = data.avatarUrl,
            lastMessageContent = data.messages.firstOrNull()?.content ?: "[NO MESSAGES YET]",
            unreadCount = data.messages.count {
                user.username !in it.readUsers.map(UserData::username)
            },
            lastMessageDateToken = data.messages.firstOrNull()?.sentTime
                ?.let { formatDate(context, it) } ?: "",
            onOpen = ::onOpen,
        )
    }

    sealed class State {
        object Loading: State()
        data class Loaded(val chats: List<MessengerChatBarItem>): State()
    }

    sealed class OpenChatState {
        object Waiting: OpenChatState()
        data class Open(val chat: ChatData): OpenChatState()
    }
}