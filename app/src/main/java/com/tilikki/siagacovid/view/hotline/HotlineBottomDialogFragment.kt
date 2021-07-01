package com.tilikki.siagacovid.view.hotline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.BottomDialogFragmentHotlineBinding
import com.tilikki.siagacovid.utils.AppEventLogging

class HotlineBottomDialogFragment : BottomSheetDialogFragment() {
    private val viewModel = HotlineViewModel()
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
        startObserve()

        binding.ivReturnIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerAdapter() {
        binding.rvHotline.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = hotlineAdapter
        }
        viewModel.fetchData()
    }

    private fun startObserve() {
        viewModel.hotlineList.observe(this) {
            hotlineAdapter.updateData(it)
        }
        viewModel.successResponse.observe(this) {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                binding.apply {
                    pbFetchHotline.visibility = View.GONE
                    rvHotline.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showError(tag: String, e: Exception) {
        this@HotlineBottomDialogFragment.activity?.runOnUiThread {
            activity?.let { AppEventLogging.logExceptionOnToast(tag, it, e) }
        }
    }
}
