@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.rms.detail

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.RmData
import com.mmfsin.noexcusescompose.domain.models.getRmsExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingFullScreen
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.util.deletePointZero

@Preview
@Composable
fun RmDetailScreenPV() {
    RmDetailContent(
        uiStates = RmDetailStates(
            isLoading = false,
            rm = getRmsExamples().first()
        ),
        {}, {}, {}
    )
}

@Composable
fun RmDetailScreen(
    viewModel: RmDetailViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    RmDetailContent(
        uiStates = uiStates,
        goBack = { goBack() },
        toExerciseDetail = { goToExerciseDetail(it) },
        addRm = { },
    )
}

@Composable
fun RmDetailContent(
    uiStates: RmDetailStates,
    goBack: () -> Unit,
    toExerciseDetail: (String) -> Unit,
    addRm: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
            )
        }
    ) { innerPadding ->

        uiStates.rm?.let { rm ->
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .background(GrayMedium)
                        .padding(innerPadding),
                    state = rememberLazyListState(),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    item {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                painterResource(R.drawable.ic_eye), null,
                                tint = GrayHard,
                                modifier = Modifier.size(36.dp)
                                    .align(Alignment.TopEnd)
                                    .clip(CircleShape)
                                    .clickable(onClick = { toExerciseDetail(rm.exercise.id) })
                                    .padding(top = 4.dp)
                                    .padding(end = 4.dp)
                                    .background(White, CircleShape)
                                    .padding(4.dp)
                            )

                            Column(Modifier.fillMaxWidth()) {
                                ImageGif(
                                    url = rm.exercise.gifURL,
                                    modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
                                )

                                SpacerSmall()

                                MediumText(
                                    text = rm.exercise.name,
                                    fontFamily = montserrat_bold,
                                )

                                SpacerSmall()

                                rm.data.forEachIndexed { i, data ->
                                    RmDataLine(
                                        position = i + 1,
                                        data = data,
                                        deleteData = {}
                                    )
                                    SpacerMini()
                                }

                                SpacerMedium()
                                OutlinedButtonCustomIcon(
                                    onClick = { addRm() },
                                    text = R.string.rms_create,
                                    icon = R.drawable.ic_add,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (uiStates.isLoading) LoadingFullScreen()
    if (uiStates.sww) ErrorDialog { goBack() }
}

@Composable
fun RmDataLine(
    position: Int,
    data: RmData,
    deleteData: () -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        MediumText(
            text = "${position}.",
            fontFamily = montserrat_bold
        )

        SpacerSmall(horizontal = true)

        MediumText(
            text = "${data.date}",
            gravity = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        SpacerSmall(horizontal = true)

        Row(modifier = Modifier.weight(1f)) {
            MediumText(
                text = "${data.kgs.deletePointZero()}",
                fontFamily = montserrat_bold
            )

            SpacerMini(horizontal = true)

            MediumText(
                text = R.string.rms_kgs,
                fontFamily = montserrat_bold
            )
        }

        SpacerSmall(horizontal = true)

        Icon(
            painterResource(R.drawable.ic_cross), null,
            tint = GrayHard,
            modifier = Modifier.size(24.dp)
                .clip(CircleShape)
                .clickable(onClick = { deleteData() })
        )
    }
}