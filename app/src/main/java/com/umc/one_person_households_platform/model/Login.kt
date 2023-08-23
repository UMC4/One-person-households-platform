package com.umc.one_person_households_platform.model



data class LoginRequest(val loginId: String, val password: String)

data class LoginResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: ResultData
)

data class ResultData(
    val userIdx: Int,
    val jwt: String
)

data class ResetPasswordResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
data class FindIdResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: UserResult?
)

data class UserResult(val loginId: String)
