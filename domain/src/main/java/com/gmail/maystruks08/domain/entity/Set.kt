package com.gmail.maystruks08.domain.entity

class Set(id: Int, name: String, portion: Portion, val products: List<Product>) : Product(id, name, portion)