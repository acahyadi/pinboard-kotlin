package com.fibelatti.pinboard.features.posts.data.model

import com.fibelatti.core.test.extension.shouldBe
import com.fibelatti.pinboard.MockDataProvider.mockTagString1
import com.fibelatti.pinboard.MockDataProvider.mockTagString2
import com.fibelatti.pinboard.features.posts.domain.model.SuggestedTags
import com.fibelatti.pinboard.features.tags.domain.model.Tag
import org.junit.jupiter.api.Test

internal class SuggestedTagDtoMapperTest {

    private val mapper = SuggestedTagDtoMapper()

    @Test
    fun `WHEN map is called THEN SuggestedTags is returned`() {

        mapper.map(SuggestedTagsDto(
            popular = listOf(mockTagString1),
            recommended = listOf(mockTagString2)
        )) shouldBe SuggestedTags(
            popular = listOf(Tag(mockTagString1)),
            recommended = listOf(Tag(mockTagString2))
        )
    }
}
