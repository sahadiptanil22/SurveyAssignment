package com.diptanil.surveyassignment.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diptanil.surveyassignment.data.converters.Converter
import com.diptanil.surveyassignment.data.dao.SurveyDao
import com.diptanil.surveyassignment.data.model.*
import com.diptanil.surveyassignment.utils.DB_VERSION


@Database(
    entities = [QuestionAnswer::class, SurveyResponse::class],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class SurveyDb : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
}