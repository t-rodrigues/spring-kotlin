package com.mercadolivro.models

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import javax.persistence.*

@Entity(name = "tb_customer")
data class CustomerModel(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @Column val name: String,

    @Column val email: String,

    @Column val password: String,

    @Enumerated(EnumType.STRING) var status: CustomerStatus,

    @Column(name = "role")
    @CollectionTable(
        name = "tb_customer_roles",
        joinColumns = [JoinColumn(name = "customer_id")]
    )
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: Set<Role> = setOf()

)
