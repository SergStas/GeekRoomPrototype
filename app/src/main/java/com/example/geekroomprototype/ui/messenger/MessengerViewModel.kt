package com.example.geekroomprototype.ui.messenger

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ChatData
import com.example.domain.usecases.login.GetLoggedInUserUseCase
import com.example.domain.usecases.messenger.QueryUserChatsUseCase
import com.example.geekroomprototype.ui.messenger.models.MessengerChatBarItem
import com.example.geekroomprototype.util.extensions.formatDate
import com.example.geekroomprototype.util.extensions.toastInDevelopment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessengerViewModel @Inject constructor(
    private val queryUserChats: QueryUserChatsUseCase,
    private val getLoggedInUser: GetLoggedInUserUseCase,
    private val context: Context,
): ViewModel() {
    private val _chats = MutableLiveData<State>(State.Loading)
    val chats: LiveData<State> get() = _chats

    fun loadChats() {
        viewModelScope.launch {
            _chats.value = State.Loaded(queryUserChats().map { mapToRvItem(it) } )
        }
    }

    private fun onOpen(item: MessengerChatBarItem) {
        context.toastInDevelopment()
    }

    private suspend fun mapToRvItem(data: ChatData) =
        MessengerChatBarItem(
            title = data.title,
            avatarUrl = data.avatarUrl,
            lastMessageContent = data.messages.firstOrNull()?.content ?: "",
            unreadCount = data.messages.count { getLoggedInUser() in it.readUsers },
            lastMessageDateToken = data.messages.firstOrNull()?.sentTime
                ?.let { formatDate(context, it) } ?: "",
            onOpen = ::onOpen,
        )

    sealed class State {
        object Loading: State()
        data class Loaded(val chats: List<MessengerChatBarItem>): State()
    }
}