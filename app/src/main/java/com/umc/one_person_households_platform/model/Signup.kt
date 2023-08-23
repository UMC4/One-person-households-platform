package com.umc.one_person_households_platform.model


data class User(
    val userIdx: Int,
    val userName: String,
    val loginId: String,
    val email: String,
    val userImage: String?,
    val nickname: String
)

data class SignupRequest(
    val userName: String,
    val loginId: String,
    val password: String,
    val email: String,
    val nickname: String,
    val adPolicyAgreement: Int
)

data class SignupResponse(
    val isSuccess: String,
    val code: Int,
    val message: String,
    val result: SignupResult
)

data class SignupResult(
    val jwt: String,
    val userIdx: Int
)

data class ValidationResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: ResultData
) {
    data class ResultData(
        val code: String
    )
}
