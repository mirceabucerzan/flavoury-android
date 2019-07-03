package flavoury.libraries.core

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import flavoury.libraries.core.AlertDialogFragment.Companion.newInstance

/**
 * [DialogFragment] which displays an [AlertDialog]. See [newInstance] for dialog customization.
 */
class AlertDialogFragment : DialogFragment() {

    /**
     * Interface definition for a callback that is invoked when a
     * button (positive / negative) is clicked.
     */
    interface AlertClickListener {
        fun onClick(positive: Boolean)
    }

    companion object {
        private const val TITLE_ID_KEY = "TITLE_ID_KEY"
        private const val MESSAGE_ID_KEY = "MESSAGE_ID_KEY"
        private const val POSITIVE_BTN_ID_KEY = "POSITIVE_BTN_ID_KEY"
        private const val NEGATIVE_BTN_ID_KEY = "NEGATIVE_BTN_ID_KEY"
        private const val THEME_ID_KEY = "THEME_ID_KEY"

        /**
         * @param titleId Int res id for the [AlertDialog]'s title, or -1 if no title is needed
         * @param messageId Int res id for the [AlertDialog]'s message, or -1 if no title is message
         * @param positiveBtnId Int res id for the [AlertDialog]'s positive button, or -1
         * @param negativeBtnId Int res id for the [AlertDialog]'s negative button, or -1
         * @param themeId Int res id for the [AlertDialog]'s theme, or -1 to use the default app theme
         */
        fun newInstance(
            @StringRes titleId: Int = -1,
            @StringRes messageId: Int = -1,
            @StringRes positiveBtnId: Int = -1,
            @StringRes negativeBtnId: Int = -1,
            @StyleRes themeId: Int = -1
        ): DialogFragment {
            return AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(TITLE_ID_KEY, titleId)
                    putInt(MESSAGE_ID_KEY, messageId)
                    putInt(POSITIVE_BTN_ID_KEY, positiveBtnId)
                    putInt(NEGATIVE_BTN_ID_KEY, negativeBtnId)
                    putInt(THEME_ID_KEY, themeId)
                }
            }
        }
    }

    private var listener: AlertClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleId: Int = arguments?.getInt(TITLE_ID_KEY) ?: -1
        val messageId: Int = arguments?.getInt(MESSAGE_ID_KEY) ?: -1
        val positiveBtnId: Int = arguments?.getInt(POSITIVE_BTN_ID_KEY) ?: -1
        val negativeBtnId: Int = arguments?.getInt(NEGATIVE_BTN_ID_KEY) ?: -1
        val themeId: Int = arguments?.getInt(THEME_ID_KEY) ?: -1

        val builder = if (themeId != -1) {
            AlertDialog.Builder(requireContext(), themeId)
        } else {
            AlertDialog.Builder(requireContext())
        }
        builder.apply {
            if (titleId != -1) setTitle(titleId)
            if (messageId != -1) setMessage(messageId)
            if (positiveBtnId != -1) setPositiveButton(positiveBtnId) { _, _ -> listener?.onClick(true) }
            if (negativeBtnId != -1) setNegativeButton(negativeBtnId) { _, _ -> listener?.onClick(false) }
        }
        return builder.create()
    }

}