package com.mmfsin.noexcusescompose.presentation.rms.rms

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupColor
import com.mmfsin.noexcusescompose.domain.models.Rm
import com.mmfsin.noexcusescompose.domain.models.getRmsExamples
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.rms.rms.components.DeleteRmDialog
import com.mmfsin.noexcusescompose.util.NAV_RM_ADD
import com.mmfsin.noexcusescompose.util.NAV_RM_DETAIL
import com.mmfsin.noexcusescompose.util.deletePointZero
import com.mmfsin.noexcusescompose.util.openBedRockActivity

@Preview
@Composable
fun RmsPV() {
    RmsContent(
        uiStates = RmsStates(
            isLoading = false,
            rms = getRmsExamples()
        ),
        {}, {}, {}, {}
    )
}

@Composable
fun RmsScreen(viewModel: RmsViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    RmsContent(
        uiStates = uiStates,
        createRm = { context.openBedRockActivity(NAV_RM_ADD) },
        goToRmDetail = { exerciseId -> context.openBedRockActivity(NAV_RM_DETAIL, exerciseId) },
        updateRmToDelete = { viewModel.updateRmIdToDelete(it) },
        deleteRm = { viewModel.deleteRm() },
    )
}

@Composable
fun RmsContent(
    uiStates: RmsStates,
    createRm: () -> Unit,
    goToRmDetail: (String) -> Unit,
    updateRmToDelete: (Rm?) -> Unit,
    deleteRm: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize()
            .background(GrayMedium)
            .padding(horizontal = 16.dp)
    ) {
        if (uiStates.rms.isEmpty()) {
            EmptyRms(
                createRm = { createRm() }
            )
        } else {
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {

                    itemsIndexed(
                        items = uiStates.rms,
                        key = { i, _ -> i }
                    ) { i, rm ->
                        RmBox(
                            rm = rm,
                            goToRmDetail = { goToRmDetail(it) },
                            updateRmToDelete = { updateRmToDelete(it) }
                        )
                    }

                    item {
                        SpacerSmall()
                        OutlinedButtonCustomIcon(
                            onClick = { createRm() },
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

    if (uiStates.rmToDelete != null) {
        DeleteRmDialog(
            exerciseName = uiStates.rmToDelete.exercise.name,
            cancel = { updateRmToDelete(null) },
            delete = { deleteRm() }
        )
    }

    if (uiStates.isLoading) LoadingLottie()
}

@Composable
fun RmBox(
    rm: Rm,
    goToRmDetail: (String) -> Unit,
    updateRmToDelete: (Rm) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { goToRmDetail(rm.exercise.id) },
                onLongClick = { updateRmToDelete(rm) }
            ),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageGif(
                url = rm.exercise.gifURL,
                modifier = Modifier.size(100.dp)
            )

            SpacerSmall(horizontal = true)

            Column() {
                SmallText(
                    text = rm.exercise.category.replaceFirstChar { it.uppercase() },
                    modifier = Modifier.fillMaxWidth().background(
                        color = getMuscularGroupColor(rm.exercise.category),
                        shape = RoundedCornerShape(8.dp)
                    ),
                    gravity = TextAlign.Center
                )
                SpacerMini()

                MediumText(
                    text = rm.exercise.name,
                    fontFamily = montserrat_bold
                )

                SpacerSmall()

                val kgs = rm.data.map { it.kgs }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallText(text = R.string.rms_last_kgs)
                    SpacerSmall(horizontal = true)
                    MediumText(
                        text = "${kgs.last().deletePointZero()}",
                        fontFamily = montserrat_bold
                    )
                    SpacerMini(horizontal = true)
                    MediumText(
                        text = R.string.rms_kgs,
                        fontFamily = montserrat_bold
                    )
                }

                SpacerMini()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallText(text = R.string.rms_max_kgs)
                    SpacerSmall(horizontal = true)
                    MediumText(
                        text = "${kgs.max().deletePointZero()}",
                        fontFamily = montserrat_bold
                    )
                    SpacerMini(horizontal = true)
                    MediumText(
                        text = R.string.rms_kgs,
                        fontFamily = montserrat_bold
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyRms(createRm: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumText(
            text = R.string.rms_empty,
            gravity = TextAlign.Center,
        )
        SpacerMedium()
        OutlinedButtonCustom(
            onClick = { createRm() },
            text = R.string.rms_create,
            color = Black
        )
        Spacer(Modifier.weight(1f))
    }
}