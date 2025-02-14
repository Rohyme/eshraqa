package com.kheer.eshraqa.presentation.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity.MainActivityViewModel
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatItemDetailsScreen.EshraqatItemDetailsViewModel
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen.EshraqatMainListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(MainActivityViewModel::class)
  internal abstract fun GuestSessionViewModel(viewModel: MainActivityViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(EshraqatItemDetailsViewModel::class)
  internal abstract fun EshraqatItemDetailsViewModel(viewModel: EshraqatItemDetailsViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(EshraqatMainListViewModel::class)
  internal abstract fun EshraqatMainListViewModel(viewModel: EshraqatMainListViewModel): ViewModel


}