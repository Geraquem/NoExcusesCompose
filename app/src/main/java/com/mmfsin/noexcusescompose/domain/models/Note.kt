package com.mmfsin.noexcusescompose.domain.models

data class Note(
    var id: String,
    var title: String,
    var description: String,
    var date: String,
    var pinned: Boolean
)

fun getExampleNotes() = listOf(
    Note(
        id = "1",
        title = "Nota 1",
        description = "Description 1",
        date = "23 Agosto 2025",
        pinned = false
    ),
    Note(
        id = "2",
        title = "NotaNotaNota Nota NotaNotaNotaNotaNota NotaNota NotaNotaNotaNota Nota 2",
        description = "Description Description Description  lsk lñaksdñlkflñ kdsf ksdlñk flñdk fls dfñlksdñlkf ñlsdk fñlsdjg lñkjf glñdlfk gjlñdj fglj fñljg dñlfj gñldjgñjd2",
        date = "23 Agosto 2025",
        pinned = false
    ),
    Note(
        id = "3",
        title = "Nota 3",
        description = "Description 3",
        date = "23 Agosto 2025",
        pinned = true
    ),
)
