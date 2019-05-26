package com.domain.skeleton.core.repository

import com.domain.skeleton.core.network.json.Empty
import com.domain.skeleton.core.network.response.ApiResponse

class ServerRepository : ApiRepository() {

    suspend fun ping(): ApiResponse<Empty> {
        return datasource.serverPing().await()
    }
}