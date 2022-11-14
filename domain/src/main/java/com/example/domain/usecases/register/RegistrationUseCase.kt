package com.example.domain.usecases.register

import com.example.domain.repo.IAuthRepo
import com.example.domain.usecases.register.models.RegistrationArgs
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repo: IAuthRepo,
): IRegistrationUseCase {
    override suspend fun invoke(args: RegistrationArgs) = repo.register(args.regData)
}