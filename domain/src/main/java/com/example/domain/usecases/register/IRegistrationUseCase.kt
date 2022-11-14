package com.example.domain.usecases.register

import com.example.domain.usecases.register.models.RegistrationResult
import com.example.domain.usecases.register.models.RegistrationArgs

interface IRegistrationUseCase {
    suspend operator fun invoke(args: RegistrationArgs): RegistrationResult
}