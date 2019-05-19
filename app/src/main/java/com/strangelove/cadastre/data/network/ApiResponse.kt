package com.strangelove.cadastre.data.network

import android.util.Log
import com.google.gson.Gson
import com.strangelove.cadastre.data.model.network.ApiErrorModel
import retrofit2.Response
import java.util.regex.Pattern

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            val apiErrorModel: ApiErrorModel? = try {
                Gson().fromJson(error.message, ApiErrorModel::class.java)
            } catch (t: Throwable) {
                Log.e("ApiResponse", error.message)
                null
            }
            return ApiErrorResponse(apiErrorModel ?: ApiErrorModel())
        }

        fun <T> create(response: Response<T>, gson: Gson): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        linkHeader = response.headers().get("link")
                    )
                }
            } else {
                val msg = response.errorBody()?.string()

                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }

                val apiErrorModel = gson.fromJson(errorMsg, ApiErrorModel::class.java)

                if (apiErrorModel.errorCode.isEmpty()) {
                    apiErrorModel.errorCode = response.code().toString()
                }

                ApiErrorResponse(apiErrorModel ?: ApiErrorModel())
            }
        }
    }
}
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorModel: ApiErrorModel) : ApiResponse<T>()