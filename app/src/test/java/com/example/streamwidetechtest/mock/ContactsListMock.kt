package com.example.streamwidetechtest.mock

import com.example.streamwidetechtest.domain.model.Contact

object ContactsListMock {
        private val person1 = Contact(
            id = 0,
            name = "nassim",
            phoneNumber = "55432876",
        )
        private val person2 = Contact(
            id = 1,
            name = "boussami",
            phoneNumber = "26543662",
        )
        private val person3 = Contact(
            id = 2,
            name = "amine",
            phoneNumber = "98432126",
        )
        private val person4 = Contact(
            id = 3,
            name = "ahmed",
            phoneNumber = "24354675",
        )
    val contactList = listOf(person1, person2, person3, person4)
    val searchByName = "am" to listOf(person2, person3)
    val searchByNumber = "54" to listOf(person1, person2, person4)
}