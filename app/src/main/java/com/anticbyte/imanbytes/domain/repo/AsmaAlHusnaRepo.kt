package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.domain.model.Asma

interface AsmaAlHusnaRepo {
    suspend fun getSingleAsma(number: String): Result<Asma>
    suspend fun getAllAsma(): Result<List<Asma>>
}