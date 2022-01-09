package com.mercadolivro.controllers

import com.mercadolivro.controllers.requests.PostPurchaseRequest
import com.mercadolivro.controllers.requests.mapper.PurchaseMapper
import com.mercadolivro.services.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPurchase(@RequestBody @Valid postPurchaseRequest: PostPurchaseRequest) {
        return purchaseService.create(purchaseMapper.toModel(postPurchaseRequest))
    }

}
