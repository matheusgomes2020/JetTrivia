package com.example.jettrivia.repository

import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val api: QuestionApi)  {
    private val listofQuestions
    = DataOrException<ArrayList<QuestionItem>,
            Boolean,
            Exception>()
}