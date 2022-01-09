package com.mercadolivro.models

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "tb_purchase")
data class PurchaseModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel,

    @ManyToMany
    @JoinTable(
        name = "tb_purchase_book",
        joinColumns = [JoinColumn(name = "purchase_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: List<BookModel>,

    val nfe: String? = null,
    val price: BigDecimal,
    val createdAt: LocalDateTime

)
