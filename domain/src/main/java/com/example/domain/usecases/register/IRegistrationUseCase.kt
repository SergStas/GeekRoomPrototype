package com.example.domain.usecases.register

import com.example.domain.usecases.register.models.RegistrationResult
import com.example.domain.usecases.register.models.RegistrationUCArgs

interface IRegistrationUseCase {
    suspend operator fun invoke(args: RegistrationUCArgs): RegistrationResult
}