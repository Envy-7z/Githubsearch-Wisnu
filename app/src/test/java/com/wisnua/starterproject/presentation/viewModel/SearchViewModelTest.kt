package com.wisnua.starterproject.presentation.viewModel

import app.cash.turbine.test
import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.usecase.GetUsersFromLocalUseCase
import com.wisnua.starterproject.domain.usecase.SaveUsersToLocalUseCase
import com.wisnua.starterproject.domain.usecase.SearchUserUseCase
import com.wisnua.starterproject.utils.TestCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: SearchViewModel
    private val searchUserUseCase: SearchUserUseCase = mockk()
    private val getUsersFromLocalUseCase: GetUsersFromLocalUseCase = mockk()
    private val saveUsersToLocalUseCase: SaveUsersToLocalUseCase = mockk()

    @Before
    fun setup() {
        // Setup default mock behavior to avoid "no answer found" exceptions
        coEvery { getUsersFromLocalUseCase.invoke() } returns flowOf(emptyList())

        viewModel = SearchViewModel(searchUserUseCase, getUsersFromLocalUseCase, saveUsersToLocalUseCase)
    }

    @Test
    fun `loadUsersFromLocal should update searchResults when local data exists`() = runTest {
        // Given: Local data exists
        val mockUsers = listOf(UserItem(id = 1, login = "Wisnu"))

        // Clear default mock and set specific behavior for this test
        clearMocks(getUsersFromLocalUseCase)
        coEvery { getUsersFromLocalUseCase.invoke() } returns flowOf(mockUsers)

        // When: Create new ViewModel to trigger init block
        viewModel = SearchViewModel(searchUserUseCase, getUsersFromLocalUseCase, saveUsersToLocalUseCase)
        advanceUntilIdle()

        // Then: searchResults should be updated with local data
        viewModel.searchResults.test {
            assertEquals(mockUsers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: getUsersFromLocalUseCase was called
        coVerify { getUsersFromLocalUseCase.invoke() }
    }

    @Test
    fun `loadUsersFromLocal should not update searchResults when local data is empty`() = runTest {
        // Given: Local data is empty
        val emptyUsers = emptyList<UserItem>()

        // Clear default mock and set specific behavior for this test
        clearMocks(getUsersFromLocalUseCase)
        coEvery { getUsersFromLocalUseCase.invoke() } returns flowOf(emptyUsers)

        // When: ViewModel is initialized
        viewModel = SearchViewModel(searchUserUseCase, getUsersFromLocalUseCase, saveUsersToLocalUseCase)
        advanceUntilIdle()

        // Then: searchResults should remain empty
        viewModel.searchResults.test {
            assertEquals(emptyUsers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: getUsersFromLocalUseCase was called
        coVerify { getUsersFromLocalUseCase.invoke() }
    }

    @Test
    fun `searchUsers updates searchResults on success`() = runTest {
        // Given: Setup successful search
        val mockUsers = listOf(UserItem(id = 1, login = "Andrian"))
        coEvery { searchUserUseCase.invoke("wisnu", 30, 1) } returns mockUsers
        coEvery { saveUsersToLocalUseCase.invoke(mockUsers) } just Runs

        // When: Search is performed
        viewModel.searchUsers("wisnu")
        advanceUntilIdle()

        // Then: searchResults should be updated with search results
        viewModel.searchResults.test {
            assertEquals(mockUsers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: Both use cases were called
        coVerify { searchUserUseCase.invoke("wisnu", 30, 1) }
        coVerify { saveUsersToLocalUseCase.invoke(mockUsers) }
    }

    @Test
    fun `searchUsers sets isLoading to false after completion`() = runTest {
        // Given: Setup successful response
        val mockUsers = listOf(UserItem(id = 1, login = "Andrian"))
        coEvery { searchUserUseCase.invoke("wisnu", 30, 1) } returns mockUsers
        coEvery { saveUsersToLocalUseCase.invoke(mockUsers) } just Runs

        // When: Search is performed and completed
        viewModel.searchUsers("wisnu")
        advanceUntilIdle()

        // Then: isLoading should be false after the operation completes
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `searchUsers sets isLoading to false after error`() = runTest {
        // Given: Search will fail
        coEvery { searchUserUseCase.invoke("error", 30, 1) } throws RuntimeException("Network Error")

        // When: Search is performed and fails
        viewModel.searchUsers("error")
        advanceUntilIdle()

        // Then: isLoading should be false after error
        assertEquals(false, viewModel.isLoading.value)
    }


    @Test
    fun `searchUsers sets errorMessage on failure`() = runTest {
        // Given: Search will fail
        val errorMessage = "Network Error"
        coEvery { searchUserUseCase.invoke("wisnu", 30, 1) } throws RuntimeException(errorMessage)

        // When: Search is performed
        viewModel.searchUsers("wisnu")
        advanceUntilIdle()

        // Then: errorMessage should be set
        viewModel.errorMessage.test {
            assertEquals("Error: $errorMessage", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        // Also verify isLoading is set back to false
        viewModel.isLoading.test {
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchUsers clears previous error message on new search`() = runTest {
        // Given: First search fails
        coEvery { searchUserUseCase.invoke("error", 30, 1) } throws RuntimeException("First error")

        // When: First search
        viewModel.searchUsers("error")
        advanceUntilIdle()

        // Then: Error is set
        assertEquals("Error: First error", viewModel.errorMessage.value)

        // Given: Second search succeeds
        val mockUsers = listOf(UserItem(id = 1, login = "Success"))
        coEvery { searchUserUseCase.invoke("success", 30, 1) } returns mockUsers
        coEvery { saveUsersToLocalUseCase.invoke(mockUsers) } just Runs

        // When: Second search
        viewModel.searchUsers("success")
        advanceUntilIdle()

        // Then: Error is cleared
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `searchUsers should save results to local database on success`() = runTest {
        // Given: Search succeeds
        val mockUsers = listOf(
            UserItem(id = 1, login = "user1"),
            UserItem(id = 2, login = "user2")
        )
        coEvery { searchUserUseCase.invoke("multiple", 30, 1) } returns mockUsers
        coEvery { saveUsersToLocalUseCase.invoke(mockUsers) } just Runs

        // When: Search is performed
        viewModel.searchUsers("multiple")
        advanceUntilIdle()

        // Then: Results should be saved to local database
        coVerify(exactly = 1) { saveUsersToLocalUseCase.invoke(mockUsers) }
    }
}