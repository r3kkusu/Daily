package com.daily.app.common.mapper

import com.daily.app.data.remote.dto.SourceDto
import com.daily.app.domain.model.Source

fun SourceDto.toSource() : Source {
    return Source(
        favicon = favicon,
        url = url,
        title = title
    )
}