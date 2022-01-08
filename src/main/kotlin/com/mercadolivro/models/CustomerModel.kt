package com.mercadolivro.models

import com.mercadolivro.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "tb_customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Enumerated(EnumType.STRING)
    var status: CustomerStatus

)
