package com.wisnua.starterproject.presentation.viewModel

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.usecase.GetDetailUserUseCase
import com.wisnua.starterproject.domain.usecase.GetUserReposUseCase
import com.wisnua.starterproject.utils.TestCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: UserDetailViewModel
    private val getDetailUserUseCase: GetDetailUserUseCase = mockk()
    private val getUserReposUseCase: GetUserReposUseCase = mockk()

    @Before
    fun setup() {
        viewModel = UserDetailViewModel(
            getDetailUserUseCase,
            getUserReposUseCase
        )
    }

    @Test
    fun `getUserDetail updates userDetail state on success`() = runTest {
        // Given
        val mockUsername = "wisnuandrn"
        val mockUserDetail = DetailResponse(
            login = "wisnuandrn",
            id = 12345,
            name = "Wisnu Andrian",
            avatarUrl = "https://example.com/avatar.jpg"
        )
        coEvery { getDetailUserUseCase.invoke(mockUsername) } returns mockUserDetail

        // When
        viewModel.getUserDetail(mockUsername)
        advanceUntilIdle()

        // Then
        assertEquals(mockUserDetail, viewModel.userDetail.value)

        // Verify loading states
        assertFalse(viewModel.isLoading.value)

        // Verify method was called
        coVerify { getDetailUserUseCase.invoke(mockUsername) }
    }

    @Test
    fun `getUserDetail sets errorMessage on failure`() = runTest {
        // Given
        val mockUsername = "wisnuandrn"
        val errorMessage = "Network Error"
        coEvery { getDetailUserUseCase.invoke(mockUsername) } throws RuntimeException(errorMessage)

        // When
        viewModel.getUserDetail(mockUsername)
        advanceUntilIdle()

        // Then
        assertEquals("Error: $errorMessage", viewModel.errorMessage.value)
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.userDetail.value)
    }

    @Test
    fun `getUserRepos updates repositories state on success`() = runTest {
        // Given
        val mockUsername = "wisnuandrn"
        val mockRepos = listOf(
            RepoResponseItem(
                id = 1,
                name = "repo1",
                description = "Test repo 1",
                stargazersCount = 10
            ),
            RepoResponseItem(
                id = 2,
                name = "repo2",
                description = "Test repo 2",
                stargazersCount = 20
            )
        )
        coEvery { getUserReposUseCase.invoke(mockUsername) } returns mockRepos

        // When
        viewModel.getUserRepos(mockUsername)
        advanceUntilIdle()

        // Then
        assertEquals(mockRepos, viewModel.repositories.value)
        assertFalse(viewModel.isLoading.value)
        coVerify { getUserReposUseCase.invoke(mockUsername) }
    }

    @Test
    fun `getUserRepos sets errorMessage on failure`() = runTest {
        // Given
        val mockUsername = "wisnuandrn"
        val errorMessage = "Repository not found"
        coEvery { getUserReposUseCase.invoke(mockUsername) } throws RuntimeException(errorMessage)

        // When
        viewModel.getUserRepos(mockUsername)
        advanceUntilIdle()

        // Then
        assertEquals("Error: $errorMessage", viewModel.errorMessage.value)
        assertFalse(viewModel.isLoading.value)
        assertEquals(emptyList<RepoResponseItem>(), viewModel.repositories.value)
    }

    @Test
    fun `getUserDetail updates loading state correctly`() = runTest {
        // Given
        val mockUsername = "wisnuandrn"
        val mockUserDetail = DetailResponse(
            login = "wisnuandrn",
            id = 12345,
            name = "Wisnu Andrian",
            avatarUrl = "https://example.com/avatar.jpg"
        )

        // Make the use case take some time to respond
        coEvery { getDetailUserUseCase.invoke(mockUsername) } coAnswers {
            // Record loading states
            val initialLoading = viewModel.isLoading.value
            assertTrue("Loading should be true when operation starts", initialLoading)

            // Return the result
            mockUserDetail
        }

        // When
        viewModel.getUserDetail(mockUsername)
        advanceUntilIdle()

        // Then
        assertFalse("Loading should be false after operation completes", viewModel.isLoading.value)
    }

    @Test
    fun `getUserRepos updates loading state correctly`() = runTest {
        // Given
        val mockUsername = "wisnuandrn"
        val mockRepos = listOf(
            RepoResponseItem(
                id = 1,
                name = "repo1",
                description = "Test repo 1",
                stargazersCount = 10
            )
        )

        // Make the use case take some time to respond
        coEvery { getUserReposUseCase.invoke(mockUsername) } coAnswers {
            // Record loading states
            val initialLoading = viewModel.isLoading.value
            assertTrue("Loading should be true when operation starts", initialLoading)

            // Return the result
            mockRepos
        }

        // When
        viewModel.getUserRepos(mockUsername)
        advanceUntilIdle()

        // Then
        assertFalse("Loading should be false after operation completes", viewModel.isLoading.value)
    }
}