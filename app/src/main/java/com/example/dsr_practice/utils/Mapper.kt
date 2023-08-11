package com.example.dsr_practice.utils

interface Mapper<Domain, T> {
    fun fromDomain(domain: Domain): T
    fun toDomain(t: T): Domain
}