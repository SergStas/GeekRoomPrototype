package com.example.geekroomprototype.ui.messenger.newchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserData
import com.example.domain.usecases.messenger.QueryUserChatsUseCase
import com.example.domain.usecases.messenger.create.CreateChatUseCase
import com.example.domain.usecases.messenger.create.GetAllUsersUseCase
import com.example.geekroomprototype.ui.messenger.newchat.models.NewChatUserItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewChatViewModel @Inject constructor(
    private val getAllUsers: GetAllUsersUseCase,
    private val createChat: CreateChatUseCase,
    private val queryUserChats: QueryUserChatsUseCase,
): ViewModel() {
    private val _state = MutableLiveData<State>(State.Waiting)
    val state: LiveData<State> get() = _state

    private val _modifiedIndex = MutableLiveData<Int?>()
    val modifiedIndex: LiveData<Int?> get() = _modifiedIndex

    private val _errMsg = MutableLiveData<String?>()
    val errMsg: LiveData<String?> get() = _errMsg

    private val _chatName = MutableLiveData("")
    val chatName: LiveData<String> get() = _chatName

    private val users get() = (_state.value as? State.EditingParticipants)?.users

    fun reloadUsers() {
        viewModelScope.launch {
            _state.value = State.LoadingUsers
            val users = getAllUsers()
            _state.value = State.EditingParticipants(mapToItems(users))
        }
    }

    fun finish() {
        viewModelScope.launch {
            val checked = users?.filter { it.isChecked }
            if (checked.isNullOrEmpty()) {
                _errMsg.value = "Please add at least 1 member"
            } else if (chatName.value in queryUserChats().map { it.title }) {
                _errMsg.value = "Chat already exists"
            } else {
                createChat(mapToUsers(checked))
                _state.value = State.Created
                _state.value = State.Waiting
            }
        }
    }

    private fun mapToItems(users: List<UserData>) =
        users.map {
            NewChatUserItem(
                username = it.username,
                avatarUrl = it.avatarUrl,
                isChecked = false,
                _onTap = ::checkUser,
            )
        }

    private fun checkUser(user: NewChatUserItem) {
        val checked = user.copy(isChecked = !user.isChecked)
        val index = indexOfItem(user) ?: return
        updateItem(index, checked)
    }

    private fun indexOfItem(item: NewChatUserItem) =
        users?.indexOf(item)?.takeIf { it >= 0 }

    private fun updateItem(index: Int, item: NewChatUserItem) {
        _state.value = State.EditingParticipants(
            users?.toMutableList()?.apply {
                removeAt(index)
                add(index, item)
            } ?: return
        )
        _modifiedIndex.value = index
        updateTitle()
    }

    private fun updateTitle() {
        _chatName.value = users
            ?.filter { it.isChecked }
            ?.joinToString(", ") { it.username } ?: return
    }

    private fun mapToUsers(items: List<NewChatUserItem>) =
        items.map { UserData(it.username, it.avatarUrl) }

    sealed class State {
        object LoadingUsers: State()
        data class EditingParticipants(val users: List<NewChatUserItem>): State()
        object Created: State()
        object Waiting: State()
    }
}