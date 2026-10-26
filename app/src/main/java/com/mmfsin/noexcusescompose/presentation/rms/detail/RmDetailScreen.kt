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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.rms.detail.components.DeleteRmDataDialog
import com.mmfsin.noexcusescompose.presentation.rms.exercises.components.AddRmDialogDialog
import com.mmfsin.noexcusescompose.util.deletePointZero
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview
@Composable
fun RmDetailScreenPV() {
    RmDetailContent(
        uiStates = RmDetailStates(
            isLoading = false,
            rm = getRmsExamples().first()
        ),
        {}, {}, {}, { _, _, _ -> }, {},
        {}
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
        showAddRmDialog = { viewModel.showAddRmDialog(it) },
        addRm = { exerciseId, date, weight -> viewModel.addRm(exerciseId, date, weight) },
        updateRmIdToDelete = { viewModel.updateRmIdToDelete(it) },
        deleteRm = { viewModel.deleteRmData(it) },
    )
}

@Composable
fun RmDetailContent(
    uiStates: RmDetailStates,
    goBack: () -> Unit,
    toExerciseDetail: (String) -> Unit,
    showAddRmDialog: (Boolean) -> Unit,
    addRm: (String, Long, Double) -> Unit,
    updateRmIdToDelete: (String?) -> Unit,
    deleteRm: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                showIconRight = true,
                iconRight = R.drawable.ic_eye,
                iconRightClick = { uiStates.rm?.let { toExerciseDetail(it.exercise.id) } }
            )
        }
    ) { innerPadding ->

        uiStates.rm?.let { rm ->
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .background(White)
                        .padding(innerPadding),
                    state = rememberLazyListState(),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    item {
                        Box(Modifier.fillMaxWidth()) {
                            Column(Modifier.fillMaxWidth()) {
                                ImageGif(
                                    url = rm.exercise.gifURL,
                                    modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
                                )

                                SpacerSmall()

                                MediumText(
                                    text = rm.exercise.name,
                                    fontFamily = montserrat_bold,
                                )

                                SpacerSmall()

                                rm.data.sortedBy { it.millis }.forEachIndexed { i, data ->
                                    RmDataLine(
                                        position = i + 1,
                                        data = data,
                                        deleteData = { updateRmIdToDelete(data.id) }
                                    )
                                    SpacerMini()
                                }

                                SpacerMedium()
                                OutlinedButtonCustomIcon(
                                    onClick = { showAddRmDialog(true) },
                                    text = R.string.rms_create,
                                    icon = R.drawable.ic_add,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Black
                                )
                            }
                        }
                    }

                    item {
                        Column {
                            SpacerMedium()

                            MediumText(
                                text = R.string.rms_kgs,
                                fontFamily = montserrat_bold
                            )

                            SpacerSmall()

                            RmLineChart(rm.data)

                            SpacerLarge()
                        }
                    }
                }
            }

            if (uiStates.rmIdToDelete != null) {
                DeleteRmDataDialog(
                    cancel = { updateRmIdToDelete(null) },
                    delete = { deleteRm(uiStates.rmIdToDelete) }
                )
            }

            if (uiStates.showAddRmDialog) {
                AddRmDialogDialog(
                    onDismiss = { showAddRmDialog(false) },
                    exercise = rm.exercise,
                    seeExercise = { toExerciseDetail(rm.exercise.id) },
                    addRm = { date, kgs -> addRm(rm.exercise.id, date, kgs) }
                )
            }
        }
    }

    if (uiStates.isLoading) LoadingFullScreen()
    if (uiStates.shouldGoBack) goBack()
    if (uiStates.sww) ErrorDialog { goBack() }
}

@Composable
fun RmDataLine(
    position: Int,
    data: RmData,
    deleteData: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

@Composable
fun RmLineChart(
    data: List<RmData>,
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val sortedData = remember(data) { data.sortedBy { it.millis } }

    LaunchedEffect(sortedData) {
        modelProducer.runTransaction {
            lineSeries {
                series(
                    x = sortedData.indices.map { it.toFloat() },
                    y = sortedData.map { it.kgs.toFloat() }
                )
            }
        }
    }

    val dateFormatter = remember(sortedData) {
        val formatter = SimpleDateFormat("dd/MM", Locale.getDefault())

        CartesianValueFormatter { _, value, _ ->
            val index = value.toInt()

            sortedData.getOrNull(index)
                ?.let { formatter.format(Date(it.millis)) }
                ?: " "
        }
    }
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = dateFormatter,
                itemPlacer = remember { HorizontalAxis.ItemPlacer.aligned() }
            ),
        ),
        modelProducer = modelProducer,
    )
}
