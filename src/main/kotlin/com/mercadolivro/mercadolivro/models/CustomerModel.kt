package com.mercadolivro.mercadolivro.models

import javax.persistence.*

@Entity(name = "tb_customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String,

    @Column
    var email: String
)
