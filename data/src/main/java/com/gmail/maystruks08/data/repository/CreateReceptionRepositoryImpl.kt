package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Ingredient
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateReceptionRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    CreateReceptionRepository {

    override fun getStartInquirerInfo(): Single<StartInquirerInfo> {
        return Single.just(menuInfo.startInquirerInfo!!)
    }

}


