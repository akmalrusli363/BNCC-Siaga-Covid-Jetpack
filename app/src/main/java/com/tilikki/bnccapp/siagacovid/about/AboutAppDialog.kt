package com.tilikki.bnccapp.siagacovid.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.DialogAboutAppBinding

class AboutAppDialog : DialogFragment() {
    private lateinit var binding: DialogAboutAppBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAboutAppBinding.inflate(inflater)
        binding.ivCloseDialog.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun setupDialogSize() {
        dialog?.window?.setBackgroundDrawable(this.context?.let { ContextCompat.getDrawable(it, R.drawable.shape_round_dialog_box) })
    }

    override fun onStart() {
        super.onStart()
        setupDialogSize()
    }

}