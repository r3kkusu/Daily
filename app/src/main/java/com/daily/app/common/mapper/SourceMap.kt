package com.daily.app.common.mapper

import com.daily.app.data.local.entities.SourceEnt
import com.daily.app.data.remote.dto.SourceDto
import com.daily.app.domain.model.Source

fun SourceDto.toSource() : Source {
    return Source(
        favicon = favicon,
        url = url,
        title = title
    )
}

fun SourceEnt.toSource() : Source {
    return Source(
        favicon = favicon,
        url = url,
        title = name
    )
}

fun SourceDto.toSourceEnt() : SourceEnt {
    return SourceEnt(
        source_id = 0,
        favicon = favicon,
        url = url,
        name = title
    )
}