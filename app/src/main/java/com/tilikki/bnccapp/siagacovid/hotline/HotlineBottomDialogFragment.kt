package com.tilikki.bnccapp.siagacovid.hotline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.bottom_dialog_fragment_hotline.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class HotlineBottomDialogFragment : BottomSheetDialogFragment() {
    private val okHttpClient = OkHttpClient()

    companion object {
        const val hotlineApiURL = "https://bncc-corona-versus.firebaseio.com/v1/hotlines.json"

        fun show(fragmentManager: FragmentManager) {
            val dialogFragment = HotlineBottomDialogFragment()
            dialogFragment.setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            dialogFragment.show(fragmentManager, dialogFragment.tag)
        }
    }

    private val mockHotlineList = mutableListOf(
        HotlineData("@drawable/ic_wait", "Loading...", "???")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_fragment_hotline, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvHotline.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvHotline.adapter = hotlineAdapter

        fetchData(hotlineAdapter)

        ivReturnIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun fetchData(hotlineAdapter: HotlineAdapter) {
        val request: Request = Request.Builder().url(hotlineApiURL).build()

        okHttpClient.newCall(request).enqueue(getCallback(hotlineAdapter))
    }

    private fun getCallback(hotlineAdapter: HotlineAdapter): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.let { AppEventLogging.logExceptionOnToast(it, e) }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val hotlineListFromNetwork = mutableListOf<HotlineData>()

                    for (i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            HotlineData(
                                imgIcon = jsonArray.getJSONObject(i).getString("img_icon"),
                                name = jsonArray.getJSONObject(i).getString("name"),
                                phone = jsonArray.getJSONObject(i).getString("phone")
                            )
                        )
                    }

                    activity?.runOnUiThread {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }
                } catch (e: Exception) {
                    activity?.let { AppEventLogging.logExceptionOnToast(it, e) }
                }
            }

        }
    }
}
