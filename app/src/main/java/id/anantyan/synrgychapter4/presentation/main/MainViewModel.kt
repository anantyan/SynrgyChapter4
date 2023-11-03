package id.anantyan.synrgychapter4.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.anantyan.synrgychapter4.data.local.repository.PreferenceRepositoryImpl
import id.anantyan.synrgychapter4.domain.PreferenceUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))

    val getTheme = preferenceUseCase.executeGetTheme()
}