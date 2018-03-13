package com.jk.kotlinjpa

import javax.persistence.*

@Entity
@Table(name = "test_student")
data class Student(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long?, var name:String?) {
    constructor() : this(null,null) {
    }
}