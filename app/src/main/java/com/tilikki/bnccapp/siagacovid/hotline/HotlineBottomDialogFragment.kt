package com.tilikki.bnccapp.siagacovid.hotline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.bottom_dialog_fragment_hotline.*
import kotlinx.android.synthetic.main.bottom_dialog_fragment_hotline.ivReturnIcon

class HotlineBottomDialogFragment : BottomSheetDialogFragment(), PVContract.View<HotlineData> {
    private val presenter = HotlinePresenter(HotlineModel(), this)

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
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_fragment_hotline, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerAdapter()

        ivReturnIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerAdapter() {
        rvHotline.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvHotline.adapter = hotlineAdapter
        presenter.fetchData()
    }

    override fun updateData(listData: List<HotlineData>) {
        this@HotlineBottomDialogFragment.activity?.runOnUiThread {
            hotlineAdapter.updateData(listData)
        }
    }

    override fun showError(tag: String, e: Exception) {
        this@HotlineBottomDialogFragment.activity?.runOnUiThread {
            activity?.let { AppEventLogging.logExceptionOnToast(tag, it, e) }
        }
    }
}
