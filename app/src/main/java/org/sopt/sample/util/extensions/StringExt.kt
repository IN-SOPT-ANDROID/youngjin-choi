package org.sopt.sample.util.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String?.toJsonRequestBody(): RequestBody =
    requireNotNull(this).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())