package com.fibelatti.pinboard.core.android.base

import android.os.Bundle
import android.view.View
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.fibelatti.core.extension.toast
import com.fibelatti.pinboard.BuildConfig
import com.fibelatti.pinboard.R
import com.fibelatti.pinboard.core.di.ViewModelProvider
import com.fibelatti.pinboard.core.extension.doOnApplyWindowInsets
import com.fibelatti.pinboard.core.extension.getViewToApplyInsets

abstract class BaseFragment @ContentView constructor(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    protected val viewModelProvider: ViewModelProvider
        get() = (activity as BaseActivity).viewModelProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewToApplyInsets(view).doOnApplyWindowInsets { viewToApply, insets, initialPadding, _ ->
            ViewCompat.setPaddingRelative(
                viewToApply,
                initialPadding.start,
                initialPadding.top,
                initialPadding.end,
                initialPadding.bottom + insets.systemWindowInsetBottom
            )
        }
    }

    open fun handleError(error: Throwable) {
        activity?.toast(getString(R.string.generic_msg_error))
        if (BuildConfig.DEBUG) {
            error.printStackTrace()
        }
    }
}
