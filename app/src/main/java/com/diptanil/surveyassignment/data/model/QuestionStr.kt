package com.diptanil.surveyassignment.data.model


import com.google.gson.annotations.SerializedName

data class QuestionStr(
    @SerializedName("opt_female")
    val optFemale: String,
    @SerializedName("opt_male")
    val optMale: String,
    @SerializedName("opt_other")
    val optOther: String,
    @SerializedName("q_employee_gender")
    val qEmployeeGender: String,
    @SerializedName("q_employee_name")
    val qEmployeeName: String,
    @SerializedName("q_size_of_farm")
    val qSizeOfEmp: String
)