package com.example.firstcomposeproject.samples.lesson2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class InstagramMainVM : ViewModel() {

    private val initialList = mutableListOf<InstagramModel>().apply {
        repeat(50) {
            add(
                InstagramModel(
                    id = it,
                    title = "Title: $it",
                    isFollowed = Random.nextBoolean()
                )
            )
        }
    }

    private val _models = MutableStateFlow(initialList)
    val models: StateFlow<List<InstagramModel>> = _models

    fun changeFollowingStatus(model: InstagramModel) {
        val modifiedList = _models.value.toMutableList().apply {
            replaceAll {
                if (it == model) {
                    it.copy(isFollowed = !it.isFollowed)
                } else {
                    it
                }
            }

        }

        _models.value = modifiedList

    }

    fun deleteProfile(model: InstagramModel) {
        val modifiedList = _models.value.toMutableList().apply {
            remove(model)
        }
        _models.value = modifiedList
    }
}