package com.example.geekroomprototype.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geekroomprototype.ui.auth.vm.AuthViewModel
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
    abstract fun bindMainViewModel(mainViewModel: AuthViewModel): ViewModel

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