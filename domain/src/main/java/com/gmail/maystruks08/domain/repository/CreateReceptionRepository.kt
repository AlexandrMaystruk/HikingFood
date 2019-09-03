package com.gmail.maystruks08.domain.repository


import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import io.reactivex.Single

interface CreateReceptionRepository {

    fun getStartInquirerInfo(): Single<StartInquirerInfo>
}