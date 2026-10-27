package com.mmfsin.noexcuses.presentation.rms.rms

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.domain.models.MuscularGroupType.Companion.getMuscularGroupColor
import com.mmfsin.noexcuses.domain.models.Rm
import com.mmfsin.noexcuses.presentation.core.components.ImageGif
import com.mmfsin.noexcuses.presentation.core.components.LoadingLottie
import com.mmfsin.noexcuses.presentation.core.components.MediumText
import com.mmfsin.noexcuses.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcuses.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcuses.presentation.core.components.SmallText
import com.mmfsin.noexcuses.presentation.core.components.SpacerLarge
import com.mmfsin.noexcuses.presentation.core.components.SpacerMedium
import com.mmfsin.noexcuses.presentation.core.components.SpacerMini
import com.mmfsin.noexcuses.presentation.core.components.SpacerSmall
import com.mmfsin.noexcuses.presentation.core.theme.Black
import com.mmfsin.noexcuses.presentation.core.theme.GrayHard
import com.mmfsin.noexcuses.presentation.core.theme.GrayMedium
import com.mmfsin.noexcuses.presentation.core.theme.OrangeHard
import com.mmfsin.noexcuses.presentation.core.theme.RedLight
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcuses.presentation.rms.rms.components.DeleteRmDialog
import com.mmfsin.noexcuses.util.NAV_RM_ADD
import com.mmfsin.noexcuses.util.NAV_RM_DETAIL
import com.mmfsin.noexcuses.util.deletePointZero
import com.mmfsin.noexcuses.util.openBedRockActivity

@Preview
@Composable
fun RmsPV() {
    RmsContent(
        uiStates = RmsStates(
            isLoading = false,
            rms = emptyList()
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
            Modifier.fillMaxWidth().padding(12.dp).padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageGif(
                url = rm.exercise.gifURL,
                modifier = Modifier.size(100.dp)
            )

            SpacerSmall(horizontal = true)

            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallText(
                        text = rm.exercise.category.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.weight(1f).background(
                            color = getMuscularGroupColor(rm.exercise.category),
                            shape = RoundedCornerShape(8.dp)
                        ),
                        gravity = TextAlign.Center
                    )
                    SpacerSmall(horizontal = true)
                    Icon(
                        painterResource(R.drawable.ic_graph), null,
                        modifier = Modifier.size(16.dp)
                    )
                }
                SpacerMini()

                MediumText(
                    text = rm.exercise.name,
                    fontFamily = montserrat_bold
                )

                SpacerSmall()

                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(1f)) {
                        val last = rm.data.first()

                        SpacerMini()

                        SmallText(text = R.string.rms_last_kgs)

                        Row {
                            MediumText(
                                text = "${last.kgs.deletePointZero()}",
                                fontFamily = montserrat_bold
                            )
                            SpacerMini(horizontal = true)
                            MediumText(
                                text = R.string.rms_kgs,
                                fontFamily = montserrat_bold
                            )
                        }

                        SmallText(
                            text = "${last.date}",
                        )
                    }

                    Column(Modifier.weight(1f)) {
                        val rm = rm.data.maxBy { it.kgs }
                        SpacerMini()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SmallText(text = R.string.rms_max_kgs)
                            SpacerMini(horizontal = true)
                            Icon(
                                painterResource(R.drawable.ic_fav_on), null,
                                modifier = Modifier.size(12.dp), tint = OrangeHard
                            )
                        }
                        Row {
                            MediumText(
                                text = "${rm.kgs.deletePointZero()}",
                                fontFamily = montserrat_bold
                            )
                            SpacerMini(horizontal = true)
                            MediumText(
                                text = R.string.rms_kgs,
                                fontFamily = montserrat_bold
                            )
                        }

                        SmallText(
                            text = "${rm.date}",
                        )
                    }
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

        Box(
            modifier = Modifier.size(64.dp).background(RedLight, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(R.drawable.ic_graph), null,
                modifier = Modifier.size(24.dp),
                tint = GrayHard
            )
        }

        SpacerLarge()

        MediumText(
            text = R.string.rms_empty_title,
            gravity = TextAlign.Center,
            fontFamily = montserrat_bold
        )
        SpacerSmall()
        SmallText(
            text = R.string.rms_empty_subtitle,
            gravity = TextAlign.Center,
        )
        SpacerMedium()
        OutlinedButtonCustom(
            onClick = { createRm() },
            text = R.string.rms_create,
            color = Black,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
    }
}