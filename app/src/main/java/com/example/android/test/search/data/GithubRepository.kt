package com.example.android.test.search.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.android.test.search.api.GithubService
import com.example.android.test.search.api.searchRepos
import com.example.android.test.search.model.Repo
import com.example.android.test.search.model.RepoSearchResult

/**
 * Repository class that works with local and remote data sources.
 */
class GithubRepository(
        private val service: GithubService) {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1

        // Get data from the local cache
        val data =  requestData(query)


        return RepoSearchResult(data, networkErrors)
    }


    private fun requestData(query: String) : LiveData<List<Repo>>{
  //      if (isRequestInProgress) return
        val data = MutableLiveData<List<Repo>>();
        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->

            data.postValue(repos);
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
        return data;
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}