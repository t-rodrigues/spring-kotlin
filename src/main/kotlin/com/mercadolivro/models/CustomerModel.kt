package com.mercadolivro.models

import com.mercadolivro.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "tb_customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    val name: String,

    @Column
    val email: String,

    @Column
    val password: String,

    @Enumerated(EnumType.STRING)
    var status: CustomerStatus

)
