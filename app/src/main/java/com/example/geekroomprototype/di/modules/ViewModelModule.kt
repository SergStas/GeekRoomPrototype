package com.example.geekroomprototype.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geekroomprototype.ui.auth.vm.AuthViewModel
import com.example.geekroomprototype.ui.feed.FeedViewModel
import com.example.geekroomprototype.ui.feed.articles.ReadArticleViewModel
import com.example.geekroomprototype.ui.feed.articles.create.NewArticleViewModel
import com.example.geekroomprototype.ui.messenger.MessengerViewModel
import com.example.geekroomprototype.ui.messenger.chat.ChatViewModel
import com.example.geekroomprototype.ui.messenger.newchat.NewChatViewModel
import com.example.geekroomprototype.ui.profile.ProfileViewModel
import com.example.geekroomprototype.ui.search.vm.SearchViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindMainViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessengerViewModel::class)
    abstract fun bindMessengerViewModel(viewModel: MessengerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewChatViewModel::class)
    abstract fun bindNewChatViewModel(viewModel: NewChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReadArticleViewModel::class)
    abstract fun bindReadArticleViewModel(viewModel: ReadArticleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewArticleViewModel::class)
    abstract fun bindNewArticleViewModel(viewModel: NewArticleViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory @Inject constructor(
        private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val viewModelProvider = viewModels[modelClass]
                ?: throw IllegalStateException("viewModel $modelClass not found")
            return viewModelProvider.get() as T
        }
    }
}