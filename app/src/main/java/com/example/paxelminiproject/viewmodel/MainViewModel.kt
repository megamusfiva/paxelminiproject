package com.example.paxelminiproject.viewmodel

import android.text.TextUtils
import androidx.lifecycle.*
import com.example.paxelminiproject.data.remote.model.UsersResponse
import com.example.paxelminiproject.data.repository.MainRepository
import com.example.paxelminiproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _queryResultFlow = MutableStateFlow(
        Resource.success(
            UsersResponse(
                totalCount = 0,
                incompleteResults = false,
                items = emptyList()
            )
        )
    )
    val queryResultFlow = _queryResultFlow.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch(IO) {
            _queryResultFlow.value = Resource.loading(null)
            if (TextUtils.isEmpty(query)) {
                _queryResultFlow.value = Resource.success(null)
            } else {
                val response = repository.search(query)
                _queryResultFlow.value = response
            }
        }
    }
}
