package org.sopt.sample.presentation.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.sample.domain.entity.Follower
import org.sopt.sample.domain.repositories.FollowerRepository
import org.sopt.sample.presentation.types.ProcessState
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(private val followerRepository: FollowerRepository) :
    ViewModel() {
    private val _loadingState = MutableLiveData(ProcessState.IDLE)
    val loadingState: LiveData<ProcessState> get() = _loadingState

    private val _followers = MutableLiveData<List<Follower>?>()
    val followers: LiveData<List<Follower>?> get() = _followers

    init {
        fetchFollowerList()
    }

    private fun fetchFollowerList() {
        viewModelScope.launch {
            _loadingState.value = ProcessState.IN_PROGRESS
            delay(2000) // 서버에서 응답값을 바로 받아오기 때문에 로딩뷰가 순식간에 사라지는 이슈가 있어 2초 딜레이를 줌
            _followers.value = followerRepository.fetchFollowerList()
            _loadingState.value = ProcessState.IDLE
        }
    }
}