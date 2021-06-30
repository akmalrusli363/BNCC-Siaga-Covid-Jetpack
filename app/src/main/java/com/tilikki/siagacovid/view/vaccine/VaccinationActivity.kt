package com.tilikki.siagacovid.view.vaccine

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.ActivityVaccinationBinding
import com.tilikki.siagacovid.model.VaccinationOverview
import com.tilikki.siagacovid.utils.AppEventLogging
import com.tilikki.siagacovid.utils.StringParser
import com.tilikki.siagacovid.utils.ViewUtility
import java.util.*

class VaccinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVaccinationBinding
    private lateinit var viewModel: VaccinationViewModel

    private val vaccinationGroupAdapter = VaccinationGroupAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaccinationBinding.inflate(layoutInflater)
        viewModel = VaccinationViewModel()
        setContentView(binding.root)
        setupComponents()
        setupRecyclerAdapter()
        viewModel.fetchData()
        startObserve()
    }

    private fun setupComponents() {
        binding.ivReturnIcon.setOnClickListener {
            finish()
        }
        binding.ivInformation.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setMessage(R.string.vaccination_data_provider_info)
                .setPositiveButton(android.R.string.ok) { dialog, id ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }
        toggleFetchState(true)
    }

    private fun setupRecyclerAdapter() {
        binding.rvVaccinationTargetGroups.let {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = vaccinationGroupAdapter
        }
    }

    private fun startObserve() {
        viewModel.vaccinationData.observe(this) {
            updateData(it)
        }
        viewModel.lastUpdated.observe(this) {
            binding.tvLastUpdated.text =
                String.format(Locale.ROOT, "Updated: %s", StringParser.formatShortDate(it))
        }
        viewModel.successResponse.observe(this) {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                toggleFetchState(false)
            }
        }
    }

    private fun updateData(vaccinationOverview: VaccinationOverview) {
        binding.run {
            vaccinationOverview.let {
                tvVaccinationTarget.text = it.targetVaccination.toString()
                tvFirstDose.text = it.firstDose.toString()
                tvSecondDose.text = it.secondDose.toString()
                tvFirstDosePercentage.text = "(${it.firstDosePercentage})"
                tvSecondDosePercentage.text = "(${it.secondDosePercentage})"
                vaccinationGroupAdapter.submitList(it.vaccinationStages.values.toList())
            }
        }
    }

    private fun showError(tag: String, e: Exception) {
        AppEventLogging.logExceptionOnToast(tag, this, e)
    }

    private fun toggleFetchState(isFetching: Boolean) {
        binding.run {
            toggleFetchState(isFetching, listOf(tvFirstDose, tvFirstDosePercentage), pbFetchFirstDose)
            toggleFetchState(isFetching, listOf(tvSecondDose, tvSecondDosePercentage), pbFetchSecondDose)
            toggleFetchState(isFetching, tvVaccinationTarget, pbFetchVaccinationTarget)
            toggleFetchState(isFetching, rvVaccinationTargetGroups, pbFetchVaccinationDetails)
        }
    }

    private fun toggleFetchState(
        isFetching: Boolean,
        view: View,
        progressBar: ProgressBar
    ) {
        ViewUtility.setVisibility(view, !isFetching)
        ViewUtility.setVisibility(progressBar, isFetching)
    }

    private fun toggleFetchState(
        isFetching: Boolean,
        views: List<View>,
        progressBar: ProgressBar
    ) {
        ViewUtility.setVisibility(progressBar, isFetching)
        views.map { view ->
            ViewUtility.setVisibility(view, !isFetching)
        }
    }
}
