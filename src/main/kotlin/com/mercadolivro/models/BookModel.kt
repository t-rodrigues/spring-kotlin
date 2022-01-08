package com.mercadolivro.models

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exceptions.BadRequestException
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "tb_book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null

) {

    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.CANCELED || field == BookStatus.DELETED) {
                throw BadRequestException("Não é possível alterar um livro com status ${field.toString()}")
            }
            field = value
        }

    constructor(
        id: Long? = null,
        title: String,
        price: BigDecimal,
        customer: CustomerModel? = null,
        status: BookStatus?
    ) : this(id, title, price, customer) {
        this.status = status
    }

}
