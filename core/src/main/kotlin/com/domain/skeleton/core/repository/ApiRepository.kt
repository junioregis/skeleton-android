package com.domain.skeleton.core.repository

import com.domain.skeleton.core.datasource.remote.ApiDatasource
import org.koin.core.inject

abstract class ApiRepository : AppRepository<ApiDatasource>() {

    override val datasource: ApiDatasource by inject()
}