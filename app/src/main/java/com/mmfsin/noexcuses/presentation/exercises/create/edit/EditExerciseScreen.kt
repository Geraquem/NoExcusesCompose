package com.mmfsin.noexcuses.presentation.exercises.create.edit

import android.net.Uri
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.domain.models.MuscularGroupType.Companion.getMuscularGroupName
import com.mmfsin.noexcuses.presentation.core.components.CustomTextField
import com.mmfsin.noexcuses.presentation.core.components.CustomToolbar
import com.mmfsin.noexcuses.presentation.core.components.ErrorDialog
import com.mmfsin.noexcuses.presentation.core.components.MediumText
import com.mmfsin.noexcuses.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcuses.presentation.core.components.SmallText
import com.mmfsin.noexcuses.presentation.core.components.SpacerLarge
import com.mmfsin.noexcuses.presentation.core.components.SpacerMini
import com.mmfsin.noexcuses.presentation.core.components.SpacerSmall
import com.mmfsin.noexcuses.presentation.core.theme.Black
import com.mmfsin.noexcuses.presentation.core.theme.GrayHard
import com.mmfsin.noexcuses.presentation.core.theme.GrayLight
import com.mmfsin.noexcuses.presentation.core.theme.GrayMedium
import com.mmfsin.noexcuses.presentation.core.theme.RedHard
import com.mmfsin.noexcuses.presentation.core.theme.RedMedium
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.YellowHard
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcuses.presentation.exercises.create.ImagePicker
import com.mmfsin.noexcuses.presentation.exercises.create.edit.components.DeleteExerciseDialog

@Preview
@Composable
fun EditExercisePV() {
    EditExerciseContent(
        uiStates = EditExerciseStates(
            isLoading = true,
            mGroupId = "pecho"
        ),
        {}, {}, {}, {},
        {}, {}, {}, {},
        {}
    )
}

@Composable
fun EditExerciseScreen(
    viewModel: EditExerciseViewModel = hiltViewModel(),
    goBack: () -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    EditExerciseContent(
        uiStates = uiStates,
        goBack = { goBack() },
        editImageUrl = { viewModel.editImageUrl(it, context) },
        editName = { viewModel.editName(it) },
        editDescription = { viewModel.editDescription(it) },
        editInvolvedMuscles = { viewModel.editInvolvedMuscles(it) },
        editFav = { viewModel.editFav(it) },
        editExercise = { viewModel.editExercise() },
        showDeleteExerciseDialog = { viewModel.showDeleteExercise(it) },
        deleteExercise = { viewModel.deleteExercise() }
    )
}

@Composable
fun EditExerciseContent(
    uiStates: EditExerciseStates,
    goBack: () -> Unit,
    editImageUrl: (Uri) -> Unit,
    editName: (String) -> Unit,
    editDescription: (String) -> Unit,
    editInvolvedMuscles: (String) -> Unit,
    editFav: (Boolean) -> Unit,
    editExercise: () -> Unit,
    showDeleteExerciseDialog: (Boolean) -> Unit,
    deleteExercise: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(goBack = { goBack() })
        }
    ) { innerPadding ->
        CompositionLocalProvider(
            LocalOverscrollFactory provides null
        ) {
            println("-------------------------------------------------------------------------------------------")
            println("image: ${uiStates.imageUrl}")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .background(GrayMedium)
                    .padding(innerPadding)
                    .padding(12.dp),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {

                item {
                    Column {
                        if (uiStates.imageUrl.isNotBlank()) {
                            AsyncImage(
                                model = uiStates.imageUrl,
                                contentDescription = null,
                                modifier = Modifier.size(170.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            SpacerMini()
                        }

                        ImagePicker(onImageSelected = { editImageUrl(it) })
                    }
                }

                item {
                    CustomTextField(
                        value = uiStates.name,
                        onValueChange = { editName(it) },
                        label = R.string.my_routines_create_name,
                        modifier = Modifier.fillMaxWidth()
                            .border(1.dp, GrayMedium, RoundedCornerShape(8.dp)),
                        maxLength = 50,
                        lengthVisibility = true,
                        containerColor = GrayLight
                    )
                }

                item {
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
                            text = getMuscularGroupName(uiStates.mGroupId),
                        )
                    }

                    SpacerSmall()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .background(GrayLight, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        val text = if (uiStates.isFav) R.string.exercise_detail_fav_on
                        else R.string.exercise_detail_fav_off

                        MediumText(
                            text = text,
                            fontFamily = montserrat_bold
                        )
                        Spacer(Modifier.weight(1f))
                        Switch(
                            uiStates.isFav, { editFav(it) },
                            thumbContent = {
                                val icon = if (uiStates.isFav) R.drawable.ic_fav_on else R.drawable.ic_fav_off
                                Icon(
                                    painterResource(icon), null,
                                    tint = if (uiStates.isFav) YellowHard else GrayHard
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
                }

                item {
                    CustomTextField(
                        value = uiStates.description,
                        onValueChange = { editDescription(it) },
                        label = R.string.exercise_detail_description,
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                            .border(1.dp, GrayMedium, RoundedCornerShape(8.dp)),
                        singleLine = false,
                        imeAction = ImeAction.Next,
                        maxLength = 450,
                        maxLines = 10,
                        lengthVisibility = true,
                        containerColor = GrayLight
                    )
                    SpacerSmall()

                    CustomTextField(
                        value = uiStates.involvedMuscles,
                        onValueChange = { editInvolvedMuscles(it) },
                        label = R.string.exercise_detail_involved_muscles,
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                            .border(1.dp, GrayMedium, RoundedCornerShape(8.dp)),
                        singleLine = false,
                        imeAction = ImeAction.None,
                        maxLength = 450,
                        maxLines = 10,
                        lengthVisibility = true,
                        containerColor = GrayLight
                    )
                }
                item {
                    Column {
                        if (uiStates.emptyNameError) {
                            SmallText(
                                text = R.string.exercise_create_error,
                                fontFamily = montserrat_bold,
                                color = RedHard
                            )
                            SpacerSmall()
                        }
                        OutlinedButtonCustomIcon(
                            onClick = { editExercise() },
                            text = R.string.exercise_edit,
                            icon = R.drawable.ic_add,
                            modifier = Modifier.fillMaxWidth(),
                            color = Black
                        )

                        SpacerSmall()

                        TextButton(
                            onClick = { showDeleteExerciseDialog(true) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            SmallText(
                                text = R.string.exercise_delete,
                                textDecoration = TextDecoration.Underline,
                                color = RedMedium,
                                fontFamily = montserrat_bold
                            )
                        }
                        SpacerLarge()
                    }
                }
            }
        }
    }
    if (uiStates.showDeleteExercise) {
        DeleteExerciseDialog(
            exerciseName = uiStates.name,
            cancel = { showDeleteExerciseDialog(false) },
            delete = { deleteExercise() }
        )
    }

    if (uiStates.shouldGoBack) goBack()
    if (uiStates.sww) ErrorDialog { goBack() }
}