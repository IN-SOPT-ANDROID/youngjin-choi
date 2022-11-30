package org.sopt.sample.presentation.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.sample.domain.entity.Follower
import org.sopt.sample.domain.repositories.FollowerRepository
import org.sopt.sample.util.Event
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(private val followerRepository: FollowerRepository) :
    ViewModel() {
    private val _followers = MutableLiveData<Event<List<Follower>?>>()
    val followers: LiveData<Event<List<Follower>?>> get() = _followers

    init {
        fetchFollowerList()
    }

    private fun fetchFollowerList() {
        viewModelScope.launch {
            _followers.value = Event(followerRepository.fetchFollowerList())
        }
    }
}