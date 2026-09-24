package com.mmfsin.noexcusescompose.presentation.exercises.detail

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.YellowHard
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun ExerciseDetailScreenPV() {
    ExerciseDetailContent(
        uiStates = ExerciseDetailStates(
            isLoading = true,
            exerciseFav = true,
            exercise = getExercisesExamples().first()
        ),
        {}, { }
    )
}

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseDetailViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    ExerciseDetailContent(
        uiStates = uiStates,
        goBack = { goBack() },
        selectFav = { viewModel.selectFavExercise(it) },
    )
}

@Composable
fun ExerciseDetailContent(
    uiStates: ExerciseDetailStates,
    goBack: () -> Unit,
    selectFav: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(goBack = { goBack() })
        }
    ) { innerPadding ->
        CompositionLocalProvider(
            LocalOverscrollFactory provides null
        ) {
            Column(
                Modifier.fillMaxSize()
                    .background(White)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                SpacerMedium()

                uiStates.exercise?.let { e ->
                    ImageGif(
                        url = e.gifURL,
                        modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally)
                    )

                    SpacerLarge()

                    MediumText(
                        text = e.name,
                        fontFamily = montserrat_bold,
                        fontSize = 22.sp
                    )

                    SpacerMedium()

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .background(GrayLight, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        MediumText(
                            text = R.string.exercise_detail_group,
                            fontFamily = montserrat_bold
                        )
                        SpacerMini(horizontal = true)
                        MediumText(
                            text = e.category.replaceFirstChar { it.uppercase() },
                        )
                    }

                    SpacerSmall()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .background(GrayLight, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        val text = if (uiStates.exerciseFav) R.string.exercise_detail_fav_on
                        else R.string.exercise_detail_fav_off

                        MediumText(
                            text = text,
                            fontFamily = montserrat_bold
                        )
                        Spacer(Modifier.weight(1f))
                        Switch(
                            uiStates.exerciseFav, { selectFav(it) },
                            thumbContent = {
                                val icon = if (uiStates.exerciseFav) R.drawable.ic_fav_on else R.drawable.ic_fav_off
                                Icon(
                                    painterResource(icon), null,
                                    tint = if (uiStates.exerciseFav) YellowHard else White
                                )
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = White,
                                checkedTrackColor = YellowHard,
                                uncheckedThumbColor = GrayHard,
                                uncheckedTrackColor = GrayMedium,
                                uncheckedBorderColor = GrayHard
                            ),
                        )
                    }

                    SpacerMedium()

                    MediumText(
                        text = R.string.exercise_detail_description,
                        fontFamily = montserrat_bold
                    )

                    SpacerMini()

                    SmallText(text = e.description)

                    SpacerMedium()

                    MediumText(
                        text = R.string.exercise_detail_involved_muscles,
                        fontFamily = montserrat_bold
                    )

                    SpacerMini()

                    SmallText(text = e.involvedMuscles)

                    SpacerLarge()

                } ?: run {
                    LoadingLottie()
                }
            }
        }
    }
}

@Composable
fun TitleDetail(title: Int) {
    Box(
        modifier = Modifier
            .background(GrayLight, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        SpacerSmall(horizontal = true)
        MediumText(
            text = title,
            fontWeight = FontWeight.SemiBold
        )
    }
}