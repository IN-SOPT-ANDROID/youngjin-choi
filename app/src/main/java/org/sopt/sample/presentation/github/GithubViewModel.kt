package org.sopt.sample.presentation.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.sopt.sample.domain.entity.Repository
import org.sopt.sample.util.Event

class GithubViewModel : ViewModel() {
    private val _repositories = MutableLiveData<Event<List<Repository>>>()
    val repositoryInfo: LiveData<Event<List<Repository>>> get() = _repositories

    fun fetchRepositoryList(jsonString: String) {
        _repositories.value = Event(Json.decodeFromString(jsonString))
    }
}