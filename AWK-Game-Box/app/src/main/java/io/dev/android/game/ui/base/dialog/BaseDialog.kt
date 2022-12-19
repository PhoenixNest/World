package io.dev.android.game.ui.base.dialog

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

open class BaseDialog : DialogFragment() {

    companion object {
        private const val TAG = "BaseDialog"
    }

    private var onDismiss: () -> Unit = {}

    /* ======================== Lifecycle ======================== */

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.isDestroyed || manager.isStateSaved || (activity != null && activity?.isFinishing == true)) {
            return
        }
        super.show(manager, tag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke()
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    /* ======================== Logical ======================== */

    fun setOnDismiss(onDismiss: () -> Unit = {}): BaseDialog {
        this.onDismiss = onDismiss
        return this
    }

    fun showAllowStateLose(activity: FragmentActivity) {
        val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(this, "${javaClass::class.java.name}_${hashCode()}")
        transaction.commitAllowingStateLoss()
    }

    fun showAllowStateLose(fragmentManager: FragmentManager) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(this, "${javaClass::class.java.name}_${hashCode()}")
        transaction.commitAllowingStateLoss()
    }

    fun showAllowStateLose(fragmentManager: FragmentManager, TAG: String?) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(this, TAG)
        transaction.commitAllowingStateLoss()
    }

}