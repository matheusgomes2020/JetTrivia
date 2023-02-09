package com.example.jettrivia.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.Question
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.screens.QuestionsViewModel
import com.example.jettrivia.util.AppColors

@Composable
fun Questions( viewModel: QuestionsViewModel){

    /** Important note: since, Question class return an...*/
    val questions = viewModel.data.value.data?.toMutableList() //Important!

    if ( viewModel.data.value.loading == true ) {


        CircularProgressIndicator()


    }else{


        if (questions != null) {
            QuestionDisplay(question = questions.first() )
        }




        }

    }

//@Preview
@Composable
fun QuestionDisplay(

    question: QuestionItem,
   // questionIndex: MutableState<Int>,
    //viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit = {}

        ) {

    val choiceState = remember( question ) {

        question.choices.toMutableList()

    }

    val answerState = remember( question ) {

        mutableStateOf<Int?>( null )

    }

    val corretAnswerState = remember( question ) {

        mutableStateOf<Boolean?>( null )

    }

    val updateAnswer: (Int) -> Unit = remember( question ) {

        {

            answerState.value = it
            corretAnswerState.value = choiceState[it] == question.answer

        }

    }

    val pathEffect = PathEffect.dashPathEffect( floatArrayOf( 10f, 10f ), 0f )

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp),
            color = AppColors.mDarkPurple ) {

        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

            QuestionTracker()

            DrawDottedLine(pathEffect = pathEffect )

            Column {

                Text(text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp )

                //choices
                choiceState.forEachIndexed{ index, answerText ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(
                                    AppColors.mDarkPurple,
                                    AppColors.mDarkPurple
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomEndPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically ) {

                        RadioButton(selected = ( answerState.value == index ),
                            onClick = {

                                updateAnswer( index )

                            },

                                modifier = Modifier.padding(start = 16.dp),
                                colors = RadioButtonDefaults
                                    .colors(
                                        selectedColor =

                                        if (corretAnswerState.value == true
                                            && index == answerState.value
                                        ) {

                                            Color.Green
                                                .copy(alpha = 0.2f)

                                        } else {
                                            Color.Red.copy(alpha = 0.2f)
                                        })) //end rb

                        Text(text = answerText)

                    }
                }

            }

        }

    }

}

@Composable
fun DrawDottedLine( pathEffect: PathEffect ) {

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp),
        ){

        drawLine( color = AppColors.mLightGray,
                start = Offset( 0f, 0f ),
            end = Offset( size.width, y = 0f ),
                pathEffect = pathEffect )

    }

}

@Preview
@Composable
fun QuestionTracker( counter: Int = 10,
                    outOff: Int = 100) {

    Text(text = buildAnnotatedString {

        withStyle(style = ParagraphStyle( textIndent = TextIndent.None )){

            withStyle(style = SpanStyle(color = AppColors.mLightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp)){

                append("Question $counter/")

                withStyle(style = SpanStyle(color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp)){
                    append("$outOff")
                }

            }

        }


    }, modifier = Modifier.padding( 20.dp ))

}
