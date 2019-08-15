package com.fibelatti.pinboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fibelatti.core.android.BundleDelegate
import kotlinx.android.synthetic.main.fragments_stacktrace.*

private var Bundle.message by BundleDelegate.String("MESSAGE")
private var Bundle.stacktrace by BundleDelegate.String("STACKTRACE")

class StackTraceFragment : Fragment() {

    companion object {
        @JvmStatic
        val TAG: String = "StackTraceFragment"

        fun newInstance(message: String, stacktrace: String): StackTraceFragment {
            return StackTraceFragment().apply {
                arguments = Bundle().apply {
                    this.message = message
                    this.stacktrace = stacktrace
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragments_stacktrace, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewMessage.text = arguments?.message
        textViewStacktrace.text = arguments?.stacktrace
    }
}
