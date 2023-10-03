package com.diptanil.surveyassignment.data.remote

import com.diptanil.surveyassignment.data.model.QuestionAnswer
import com.diptanil.surveyassignment.data.model.SavedAnswersResponse
import com.diptanil.surveyassignment.data.model.SurveyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyApiService {
    @GET("d494d3af-3f97-4cc5-a762-c60d855d9698")
    suspend fun getSurvey(): Response<SurveyResponse>

}