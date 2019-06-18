package com.fibelatti.pinboard.features.share

import android.content.Intent
import android.os.Bundle
import com.fibelatti.core.archcomponents.extension.observeEvent
import com.fibelatti.pinboard.R
import com.fibelatti.pinboard.core.android.base.BaseActivity
import com.fibelatti.pinboard.core.extension.toast
import kotlinx.android.synthetic.main.activity_share.*

class ShareReceiverActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val shareReceiverViewModel = viewModelFactory.get<ShareReceiverViewModel>(this)

        setupViewModels(shareReceiverViewModel)
        intent?.checkForExtraText(shareReceiverViewModel::saveUrl)
    }

    private fun setupViewModels(
        shareReceiverViewModel: ShareReceiverViewModel
    ) {
        with(shareReceiverViewModel) {
            observeEvent(saved) { message ->
                imageViewFeedback.setImageResource(R.drawable.ic_url_saved)
                toast(message)
                finish()
            }
            observeEvent(failed) { message ->
                imageViewFeedback.setImageResource(R.drawable.ic_url_saved_error)
                toast(message)
                finish()
            }
        }
    }

    private fun Intent.checkForExtraText(onExtraTextFound: (String) -> Unit) {
        takeIf { it.action == Intent.ACTION_SEND && it.type == "text/plain" }
            ?.getStringExtra(Intent.EXTRA_TEXT)
            ?.let(onExtraTextFound)
    }
}
