package com.umc.one_person_households_platform.network

import com.umc.one_person_households_platform.BuildConfig
import com.umc.one_person_households_platform.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {
    //로그인
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/users/logIn")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>


    //전체 유저 정보 불러오기
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/users")
    suspend fun getUsers(): Response<List<User>>

    //회원가입
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/users")
    suspend fun signupUser(@Body request: SignupRequest): Response<SignupResponse>

    //이메일 인증
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/mail/Validation")
    suspend fun sendValidationEmail(@Query("email") email: String): Response<ValidationResponse>

    //비밀번호 찾기
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/users/setPwd?")
    suspend fun resetPassword(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ResetPasswordResponse>

    //아이디 찾기
    @GET("/app/users/getId")
    fun getUserId(@Query("email") email: String): Call<FindIdResponse>



    //닉네임 변경
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @PATCH("/app/users/setEmail")
    suspend fun setEmail(
        @Body email: Map<String, String>
    ): Response<SetNicknameResponse>


    // 홈 화면 마감 임박 공구 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/home/grouppurchase")
    suspend fun getGroupBuyingCategories(): Response<GroupBuying>

    // 홈 화면 커뮤니티 인기글 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/home/community")
    suspend fun getCommunityCategories(): Response<Community>

    // 홈 화면 금주 HOT 레시피 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/home/recipe")
    suspend fun getHotRecipeCategory(): Response<HotRecipe>

    // 공동 구매 화면 최신 공구, 마감 임박 공구 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/boards/grouppurchase")
    suspend fun getGroupBuyingSort(
        @Query("sort") sort: String, @Query("startIdx") startIdx: Int, @Query("size") size: Int
    ): Response<GroupBuying>

    // 공동 구매 화면 나머지 카테고리 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/boards/grouppurchase")
    suspend fun getGroupBuyingCategory(
        @Query("category") category: String, @Query("startIdx") startIdx: Int, @Query("size") size: Int
    ): Response<GroupBuying>

    // 공동 구매 검색 화면 검색 결과 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/boards/grouppurchase/search")
    suspend fun getGroupBuyingSearch(
        @Query("query") query: String, @Query("startIdx") startIdx: Int, @Query("size") size: Int
    ): Response<GroupBuying>

    // 공동 구매 상세 화면 결과 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("app/post/get")
    suspend fun getGroupBuyingDetail(
        @Query("postIdx") postIdx: Int
    ): Response<GroupBuyingDetail>

    // 커뮤니티 리스트 출력
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/boards/community")
    fun getCommunity(
        @Query("category") query: String, @Query("limit") limit: Int // 검색어 파라미터
    ): Call<CommunityDTO>

    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/boards/community")
    fun getCommunitySort(
        @Query("sort") query: String // 검색어 파라미터
    ): Call<CommunityDTO>

    // 커뮤니티 게시글 검색
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/boards/community/search")
    fun getCommunitySearch(
        @Query("query") query: String // 검색어 파라미터
    ): Call<CommunityDTO>

    // 커뮤니티 게시글 디테일 화면
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/post/get")
    fun getPostDetail(@Query("postIdx") postId: Int): Call<CommunityDetailDTO>

    // 커뮤니티 게시글 작성
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/post/create")
    fun addCommunityPost(@Body postData: CommunityAddpostDTO): Call<ApiResponse>

    // 커뮤니티 댓글 불러오기
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/comment/get")
    fun getCommunityComment(
        @Query("commentIdx") commentIdx: Int // 검색어 파라미터
    ): Call<CommunityComment>

    // 커뮤니티 댓글 작성
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/comment/create")
    fun addCommunityComment(@Body commentData: CommentAddItems): Call<CommentAddResult>

    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @DELETE("/app/comment/delete")
    fun deleteComment(@Query("commentIdx") commentIdx: Int): Call<CommonPostResult>

    //게시글 삭제
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @DELETE("/app/post/delete")
    fun deletePost(@Query("postIdx") postIdx: Int): Call<CommonPostResult>

    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/post/heart")
    fun addHeartPost(@Body scrapitem: PostHeartDTO): Call<CommonPostResult>

    // 게시글 공감하기
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/post/heart/cancel")
    fun cancelHeartPost(@Body scrapitem: PostHeartDTO): Call<CommonPostResult>

    // 레시피 목록 가져오기
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/boards/recipe")
    fun getRecipe(
        @Query("sort") sort: String
    ): Call<RecipeDTO>

    // 레시피 북마크 추가 OR 삭제
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/post/scrap")
    fun addRecipeBookmark(@Body scrapitem: RecipeScrapBody): Call<RecipeScrapResponse>

    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @POST("/app/post/scrap/cancel")
    fun cancelRecipeBookmark(@Body scrapitem: RecipeScrapBody): Call<RecipeScrapResponse>

    // 레시피 검색
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/boards/recipe/search")
    fun getRecipeSearch(
        @Query("query") query: String // 검색어 파라미터
    ): Call<RecipeDTO>

    // 레시피 디테일
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/post/get")
    fun getRecipeDetail(@Query("postIdx") postId: Int): Call<RecipeDetail>

    //마이페이지 닉네임 변경
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @PATCH("/app/users/setNickname")
    suspend fun setNickname(@Query("nickname") nickname: String, nickname1: String): Response<SetNicknameResponse>

    //마이페이지 글 작성 목록
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/mypage/community/posts")
    suspend fun getUserCommunityPosts(
        @Query("startIdx") startIdx: Int = 0,  // default 값 설정
        @Query("size") size: Int = 10         // default 값 설정
    ): Response<CommunityPostsResponse>

    //마이페이지 댓글 작성 목록
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/mypage/community/comments")
    suspend fun getUserCommunityComments(
        @Query("startIdx") startIdx: Int = 0,
        @Query("size") size: Int = 10
    ): Response<CommunityCommentsResponse>

    //마이페이지 관심표시 글 목록
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/mypage/community/likes")
    suspend fun getUserCommunityLikes(
        @Query("startIdx") startIdx: Int = 0,
        @Query("size") size: Int = 10
    ): Response<CommunityLikesResponse>

    //마이페이지 공감
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/mypage/community/hearts")
    suspend fun getCommunityHearts(
        @Query("startIdx") startIdx: Int = 0,
        @Query("size") size: Int = 10
    ): Response<CommunityHeartsResponse>

    //마이페이지 참여 공구
    @Headers("X-ACCESS-TOKEN: ${BuildConfig.JWT_KEY}")
    @GET("/app/mypage/grouppurchase/participated")
    suspend fun getParticipatedGroupPurchase(
        @Query("startIdx") startIdx: Int = 0,
        @Query("size") size: Int = 10
    ): Response<ParticipatedGroupPurchaseResponse>



    companion object {

        private const val baseUrl = "https://www.jachsang.shop/"

        fun create(): ApiClient {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}