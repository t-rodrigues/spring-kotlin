package com.mercadolivro.services

import com.mercadolivro.models.PurchaseModel

interface PurchaseService {

    fun create(purchaseModel: PurchaseModel)

    fun update(purchaseModel: PurchaseModel)

}
