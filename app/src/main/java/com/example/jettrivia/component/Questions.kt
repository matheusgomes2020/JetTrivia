package com.example.jettrivia.component

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.jettrivia.screens.QuestionsViewModel

@Composable
fun Questions( viewModel: QuestionsViewModel){

    val questions = viewModel.data.value.data?.toMutableList()

    if ( viewModel.data.value.loading == true ) {

        CircularProgressIndicator()

        Log.d("Loading", "Questions: ...loading...")

    }else{

        Log.d("Loading", "Questions: Loading STOPPED...")

            questions?.forEach { questionItem ->

                Log.d("Result", "Questions: ${questionItem.question}}")

            }
        }

    }

