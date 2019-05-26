package com.domain.skeleton.core.network.converter

import com.domain.skeleton.core.network.response.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiBodyConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, ApiResponse.Envelope<*>>? {
        val envelopeType = object : ParameterizedType {

            override fun getRawType() = ApiResponse.Envelope::class.java

            override fun getOwnerType() = null

            override fun getActualTypeArguments() = arrayOf(type)
        }

        val delegate = retrofit?.nextResponseBodyConverter<ApiResponse.Envelope<*>>(this, envelopeType, annotations!!)

        return EnvelopeConverter(delegate)
    }

    private inner class EnvelopeConverter<T>(val delegate: Converter<ResponseBody, T>?) : Converter<ResponseBody, T> {

        override fun convert(responseBody: ResponseBody): T? = delegate?.convert(responseBody)
    }

    companion object {

        fun create() = ApiBodyConverterFactory()
    }
}