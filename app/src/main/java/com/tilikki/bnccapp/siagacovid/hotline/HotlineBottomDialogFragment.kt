package com.tilikki.bnccapp.siagacovid.hotline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.BottomDialogFragmentHotlineBinding
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging

class HotlineBottomDialogFragment : BottomSheetDialogFragment(), PVContract.View<HotlineData> {
    private val presenter = HotlinePresenter(HotlineModel(), this)
    private lateinit var binding: BottomDialogFragmentHotlineBinding

    private val mockHotlineList = mutableListOf(
        HotlineData("@drawable/ic_wait", "Loading...", "???")
    )

    private val hotlineAdapter = HotlineAdapter(mockHotlineList)

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val dialogFragment = HotlineBottomDialogFragment()
            dialogFragment.setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            dialogFragment.show(fragmentManager, dialogFragment.tag)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomDialogFragmentHotlineBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerAdapter()

        binding.ivReturnIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerAdapter() {
        binding.rvHotline.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = hotlineAdapter
        }
        presenter.fetchData()
    }

    override fun updateData(listData: List<HotlineData>) {
        this@HotlineBottomDialogFragment.activity?.runOnUiThread {
            hotlineAdapter.updateData(listData)
            binding.apply {
                pbFetchHotline.visibility = View.GONE
                rvHotline.visibility = View.VISIBLE
            }
        }
    }

    override fun showError(tag: String, e: Exception) {
        this@HotlineBottomDialogFragment.activity?.runOnUiThread {
            activity?.let { AppEventLogging.logExceptionOnToast(tag, it, e) }
        }
    }
}
