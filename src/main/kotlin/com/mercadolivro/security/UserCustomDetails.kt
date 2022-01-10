package com.mercadolivro.security

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.models.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(private val customerModel: CustomerModel) : UserDetails {

    val id: Long = customerModel.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        customerModel.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword(): String = customerModel.password

    override fun getUsername(): String = customerModel.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = customerModel.status == CustomerStatus.ACTIVE

}
