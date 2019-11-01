package com.gmail.maystruks08.domain.interactor.purchaselist

import com.gmail.maystruks08.domain.repository.PurchaseListRepository
import javax.inject.Inject

class PurchaseListInteractorImpl @Inject constructor(private val repository: PurchaseListRepository) : PurchaseListInteractor
