package com.fibelatti.pinboard.core.android.base

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.fibelatti.core.extension.inTransaction
import com.fibelatti.pinboard.BuildConfig
import com.fibelatti.pinboard.R
import com.fibelatti.pinboard.StackTraceFragment
import com.fibelatti.pinboard.core.extension.toast
import java.io.PrintWriter
import java.io.StringWriter

abstract class BaseFragment : Fragment {

    protected val viewModelFactory
        get() = (activity as BaseActivity).viewModelFactory

    constructor() : super()

    @ContentView
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    open fun handleError(error: Throwable) {
        activity?.toast(getString(R.string.generic_msg_error))
        if (BuildConfig.DEBUG) error.printStackTrace()

        try {
            val message = error.message.orEmpty()
            val stringWriter = StringWriter()
            val printerWriter = PrintWriter(stringWriter)
            error.printStackTrace(printerWriter)

            inTransaction {
                setCustomAnimations(R.anim.slide_up, -1, -1, R.anim.slide_down)
                add(
                    R.id.fragmentHost,
                    StackTraceFragment.newInstance(message, stringWriter.toString()),
                    StackTraceFragment.TAG
                )
                addToBackStack(StackTraceFragment.TAG)
            }
        } catch (ignored: Exception) {
        }
    }
}
