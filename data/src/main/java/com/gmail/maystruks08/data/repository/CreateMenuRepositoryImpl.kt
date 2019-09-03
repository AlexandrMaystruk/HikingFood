package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import com.gmail.maystruks08.domain.exceptions.HasNotStartInquirerInfoException
import com.gmail.maystruks08.domain.repository.CreateMenuRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CreateMenuRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    CreateMenuRepository {

    override fun getDefaultIngredientPortions(): Single<List<Product>> {
        return Single.just(menuInfo.defaultProductPortionList)
    }

    override fun getStartInquirerData(): Single<StartInquirerInfo> {
        return Single.create { callable ->
            if (menuInfo.startInquirerInfo == null) {
                callable.onError(HasNotStartInquirerInfoException())
            } else {
                callable.onSuccess(menuInfo.startInquirerInfo!!)
            }
        }
    }

    override fun saveStartInquirerData(startInquirerInfo: StartInquirerInfo): Completable {
        return Completable.fromAction {
            menuInfo.startInquirerInfo = startInquirerInfo
        }
    }

    override fun clearStartInquirerData(): Completable {
        return Completable.fromAction {
            menuInfo.startInquirerInfo = null
        }
    }
}


