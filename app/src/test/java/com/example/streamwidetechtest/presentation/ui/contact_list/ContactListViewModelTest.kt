package com.example.streamwidetechtest.presentation.ui.contact_list

import com.example.streamwidetechtest.domain.repository.ContactsRepository
import com.example.streamwidetechtest.mock.ContactsListMock
import com.example.streamwidetechtest.util.MainDispatcherRule
import com.example.streamwidetechtest.util.Resource
import com.example.streamwidetechtest.util.UIEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ContactListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val rule = MainDispatcherRule()

    private val repository = spyk<ContactsRepository>()
    private lateinit var viewModel: ContactListViewModel

    @Before
    fun setUp() {
        viewModel = ContactListViewModel(repository)
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `fetchContacts should update state on success`() = runTest {
        // Arrange
        coEvery { repository.getAllContacts() } returns Resource.Success(ContactsListMock.contactList)

        // Act
        viewModel.fetchContacts()

        // Assert
        val expectedState =
            ContactsListState(contactList = ContactsListMock.contactList, isLoading = false)
        Assert.assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `fetchContacts should emit error event on failure`() = runTest {
        // Arrange
        val errorMessage = "Failed to fetch contacts"
        coEvery { repository.getAllContacts() } returns Resource.Error(errorMessage)

        // Act
        viewModel.fetchContacts()

        // Assert
        val expectedEvent = UIEvent.ShowSnackbar(errorMessage)
        Assert.assertEquals(expectedEvent, viewModel.eventFlow.first())
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `searchByName should update state on success`() = runTest {
        // Arrange
        val searchName = ContactsListMock.searchByName.first
        coEvery { repository.selectContactsByName(any()) } returns Resource.Success(ContactsListMock.searchByName.second)

        // Act
        viewModel.searchByName(searchName)

        // Assert
        val expectedState =
            ContactsListState(contactList = ContactsListMock.searchByName.second, isLoading = false)
        Assert.assertEquals(expectedState, viewModel.state.value)
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `searchByName should emit error event on failure`() = runTest {
        // Arrange
        val searchName = "Jo"
        val errorMessage = "Failed to search contacts by name"
        coEvery { repository.selectContactsByName(any()) } returns Resource.Error(errorMessage)

        // Act
        viewModel.searchByName(searchName)

        // Assert
        val expectedEvent = UIEvent.ShowSnackbar(errorMessage)
        Assert.assertEquals(expectedEvent, viewModel.eventFlow.first())
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `searchByPhoneNumber should update state on success`() = runTest {
        // Arrange
        val phoneNumber = ContactsListMock.searchByNumber.first
        coEvery { repository.selectContactsByPhoneNumber(any()) } returns Resource.Success(
            ContactsListMock.searchByNumber.second
        )

        // Act
        viewModel.searchByPhoneNumber(phoneNumber)

        // Assert
        coVerify { repository.selectContactsByPhoneNumber(any()) }
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `searchByPhoneNumber should emit error event on failure`() = runTest {
        // Arrange
        val phoneNumber = "3456"
        val errorMessage = "Failed to search contacts by phone number"
        coEvery { repository.selectContactsByPhoneNumber(any()) } returns Resource.Error(
            errorMessage
        )

        // Act
        viewModel.searchByPhoneNumber(phoneNumber)

        // Assert
        val expectedEvent = UIEvent.ShowSnackbar(errorMessage)
        Assert.assertEquals(expectedEvent, viewModel.eventFlow.first())
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `setSearchString should update searchString`() {
        // Arrange
        val query = "nas"

        // Act
        viewModel.setSearchString(query)

        // Assert
        Assert.assertEquals(query, viewModel.searchString.value)
    }
}