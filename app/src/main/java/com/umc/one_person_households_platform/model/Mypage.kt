package com.umc.one_person_households_platform.model
data class CommunityPostsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<PostResult>
)

data class PostResult(
    val postIdx: Int,
    val categoryIdx: Int,
    val category: String,
    val title: String,
    val createAt: String,
    val commentCount: Int
)

data class CommunityCommentsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<CommentResult>
)

data class CommentResult(
    val postIdx: Int,
    val categoryIdx: Int,
    val category: String,
    val title: String,
    val createAt: String,
    val commentCount: Int
)

data class CommunityLikesResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<LikeResult>
)

data class LikeResult(
    val postIdx: Int,
    val categoryIdx: Int,
    val category: String,
    val title: String,
    val createAt: String,
    val commentCount: Int
)




data class ParticipatedGroupPurchaseResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<ParticipatedPost>
)

data class ParticipatedPost(
    val postIdx: Int,
    val categoryIdx: Int,
    val category: String,
    val title: String,
    val productName: String,
    val createAt: String,
    val commentCount: Int,
    val remainDay: Int
)

data class CommunityHeartsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<CommunityBuyPost>
)

data class CommunityBuyPost(
    val postIdx: Int,
    val categoryIdx: Int,
    val category: String,
    val title: String,
    val productName: String,
    val createAt: String,
    val commentCount: Int,
    val remainDay: Int
)





