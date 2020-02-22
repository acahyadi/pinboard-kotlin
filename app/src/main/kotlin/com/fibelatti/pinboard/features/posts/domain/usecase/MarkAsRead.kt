package com.fibelatti.pinboard.features.posts.domain.usecase

import com.fibelatti.core.functional.Result
import com.fibelatti.core.functional.UseCaseWithParams
import com.fibelatti.core.functional.mapCatching
import com.fibelatti.pinboard.features.posts.domain.PostsRepository
import com.fibelatti.pinboard.features.posts.domain.model.Post
import javax.inject.Inject

class MarkAsRead @Inject constructor(
    private val postsRepository: PostsRepository
) : UseCaseWithParams<Post, Post>() {

    override suspend fun run(params: Post): Result<Post> =
        postsRepository.add(
            url = params.url,
            title = params.title,
            description = params.description,
            private = params.private,
            readLater = false,
            tags = params.tags
        ).mapCatching { params.copy(readLater = false) }
}
