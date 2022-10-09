package com.example.domain.usecases.register

import com.example.domain.repo.IAuthRepo
import com.example.domain.usecases.register.models.RegistrationUCArgs
import javax.inject.Inject

class RegistrationUseCaseImpl @Inject constructor(
    private val repo: IAuthRepo,
): IRegistrationUseCase {
    override suspend fun register(args: RegistrationUCArgs) =
        repo.register(args.regData)
}