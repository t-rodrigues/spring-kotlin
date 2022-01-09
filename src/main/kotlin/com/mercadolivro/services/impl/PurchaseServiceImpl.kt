package com.mercadolivro.services.impl

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.models.PurchaseModel
import com.mercadolivro.repositories.PurchaseRepository
import com.mercadolivro.services.PurchaseService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PurchaseService {

    override fun create(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    override fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

}
