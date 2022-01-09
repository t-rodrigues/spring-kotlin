package com.mercadolivro.repositories

import com.mercadolivro.models.PurchaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository : JpaRepository<PurchaseModel, Long> {

}
