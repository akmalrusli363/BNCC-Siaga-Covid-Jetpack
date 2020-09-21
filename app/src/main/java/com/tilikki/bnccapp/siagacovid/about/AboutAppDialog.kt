package com.tilikki.bnccapp.siagacovid.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.dialog_about_app.view.*

class AboutAppDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_about_app, container, false)
        rootView.ivCloseDialog.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    private fun setupDialogSize() {
        dialog?.window?.setBackgroundDrawable(this.context?.let { ContextCompat.getDrawable(it, R.drawable.shape_round_dialog_box) })
    }

    override fun onStart() {
        super.onStart()
        setupDialogSize()
    }

}