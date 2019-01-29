package com.fibelatti.pinboard.features.posts.domain.usecase

import com.fibelatti.core.functional.Result
import com.fibelatti.core.functional.UseCaseWithParams
import com.fibelatti.core.functional.map
import com.fibelatti.pinboard.features.posts.domain.PostsRepository
import javax.inject.Inject

class AddPost @Inject constructor(
    private val postsRepository: PostsRepository,
    private val validateUrl: ValidateUrl
) : UseCaseWithParams<Unit, AddPost.Params>() {

    override suspend fun run(params: Params): Result<Unit> =
        validateUrl(params.url)
            .map {
                postsRepository.add(
                    url = it,
                    description = params.description,
                    extended = params.extended,
                    tags = params.tags
                )
            }

    data class Params(
        val url: String,
        val description: String,
        val extended: String? = null,
        val tags: List<String>? = null
    )
}
