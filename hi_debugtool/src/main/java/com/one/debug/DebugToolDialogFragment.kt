package com.one.debug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.one.hi_debugtool.R
import com.one.library.util.HiDisplayUtil

/**
 * @author  diaokaibin@gmail.com on 2021/8/1.
 */
class DebugToolDialogFragment : DialogFragment() {

    private val debugTools = arrayOf(DebugTools::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content) ?: container
        val view = inflater.inflate(R.layout.hi_debug_tool, parent, false)

        dialog?.window?.setLayout(
            (HiDisplayUtil.getDisplayHeightInPx(view.context) * 0.7f).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_hi_debug_tool)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration =
            DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.shape_hi_debug_divider
            )!!
        )

        val size = debugTools.size
        for (index in 0..size - 1) {

        }
    }
}