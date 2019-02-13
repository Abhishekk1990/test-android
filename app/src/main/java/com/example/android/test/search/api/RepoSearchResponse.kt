package com.example.android.test.search.api


import com.example.android.test.search.model.Repo
import com.google.gson.annotations.SerializedName

/**
 * Data class to hold repo responses from searchRepo API calls.
 */
data class RepoSearchResponse(
        @SerializedName("total_count") val total: Int = 0,
        @SerializedName("items") val items: List<Repo> = emptyList(),
        val nextPage: Int? = null
)
