package com.diptanil.surveyassignment.data.repositories

import androidx.lifecycle.LiveData
import com.diptanil.surveyassignment.data.dao.SurveyDao
import com.diptanil.surveyassignment.data.model.QuestionAnswer
import com.diptanil.surveyassignment.data.model.SurveyResponse
import com.diptanil.surveyassignment.utils.ResultWrapper
import com.diptanil.surveyassignment.data.remote.SurveyApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


interface SurveyRepository {
    suspend fun fetchSurvey(): Flow<ResultWrapper<SurveyResponse>>

    suspend fun getCachedSurvey(): List<SurveyResponse>

    suspend fun cacheSurvey(survey: SurveyResponse)

    suspend fun saveAnswer(questionAnswer: List<QuestionAnswer>)

    suspend fun getSavedAnswers(): LiveData<List<QuestionAnswer>>

    suspend fun getUnploadedAnswers(): List<QuestionAnswer>

    suspend fun setAnswersAsUploaded(ids: List<Int>)
}

class SurveyRepositoryImpl @Inject constructor(
    private val surveyApiService: SurveyApiService,
    private val surveyDao: SurveyDao
) : SurveyRepository {
    override suspend fun fetchSurvey(): Flow<ResultWrapper<SurveyResponse>> {
        return flow {
            try {
                //get cached cached surveys
                val cachedSurveys = getCachedSurvey()

                if (cachedSurveys.isNotEmpty()) {

                    emit(ResultWrapper.success(data = cachedSurveys.first()))

                } else {
                    emit(ResultWrapper.loading(data = null))
                    val postResponse = surveyApiService.getSurvey()
                    if (postResponse.isSuccessful) {
                        postResponse.body().let {
                            if (it != null) {
                                cacheSurvey(survey = it)
                                emit(ResultWrapper.success(data = it))
                            }
                        }
                    } else {
                        emit(
                            ResultWrapper.error(
                                data = null,
                                message = postResponse.message()
                            )
                        )
                    }
                }
            } catch (e: Exception) {

                if (e is IOException) {
                    emit(
                        ResultWrapper.error(
                            data = null,
                            message = "Please check your internet connection and try again."
                        )
                    )
                } else {
                    emit(
                        ResultWrapper.error(
                            data = null,
                            message = e.message ?: e.toString()
                        )
                    )
                }
            }
        }
    }

    override suspend fun getCachedSurvey(): List<SurveyResponse> {
        return surveyDao.getCachedSurveyResponse()
    }

    override suspend fun cacheSurvey(survey: SurveyResponse) {
        try {
            surveyDao.cacheSurvey(survey)

        } catch (e: Exception) {

        }
    }

    override suspend fun saveAnswer(questionAnswer: List<QuestionAnswer>) {
        surveyDao.insertAnswer(questionAnswer)
    }

    override suspend fun getSavedAnswers(): LiveData<List<QuestionAnswer>> {

        return surveyDao.getSavedAnswers()
    }

    override suspend fun getUnploadedAnswers(): List<QuestionAnswer> {
        return surveyDao.getUnploadedAnswers()
    }

    override suspend fun setAnswersAsUploaded(ids: List<Int>) {
        surveyDao.setAnswersAsUploaded(ids)
    }
}