package com.tilikki.siagacovid.view.summarydetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.FragmentCaseDetailVaccineBinding
import com.tilikki.siagacovid.model.VaccinationOverviewData
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.utils.chart.AxisDateFormatter
import com.tilikki.siagacovid.utils.chart.VaccinationDataMarkerView
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel
import java.util.*

class VaccineFragment : BaseCaseDetailFragment() {

    private lateinit var vaccineTestingViewModel: VaccineTestingViewModel
    private lateinit var binding: FragmentCaseDetailVaccineBinding

    private lateinit var firstDoseString: String
    private lateinit var secondDoseString: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)
        vaccineTestingViewModel =
            ViewModelProvider(this).get(VaccineTestingViewModel::class.java)

        binding = FragmentCaseDetailVaccineBinding.inflate(inflater, container, false)
        mapStringResources()
        if (!vaccineTestingViewModel.isFetched) {
            vaccineTestingViewModel.fetchData()
        }
        startObserve()
        return binding.root
    }

    override fun setupViewModelObserver() {
        rootViewModel.vaccinationOverview.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.setStatisticPairs(it.firstDose, tvFirstDose, tvDailyFirstDose)
                ViewUtility.setStatisticPairs(it.secondDose, tvSecondDose, tvDailySecondDose)
            }
        })
        vaccineTestingViewModel.vaccinationHistory.observe(viewLifecycleOwner, {
            initializeVaccinationSummaryChart(it)
        })
    }

    override fun toggleFetchState(isFetching: Boolean) {
        binding.apply {
            toggleViewFetchState(
                isFetching, listOf(tvFirstDose, tvDailyFirstDose), pbFirstDose
            )
            toggleViewFetchState(
                isFetching, listOf(tvSecondDose, tvDailySecondDose), pbSecondDose
            )
        }
    }

    private fun initializeVaccinationSummaryChart(vaccinationHistory: List<VaccinationOverviewData>) {
        val vaccinationPeriod = mutableListOf<Date>()
        val firstDoseDataEntry = mutableListOf<Entry>()
        val secondDoseDataEntry = mutableListOf<Entry>()
        val dailyFirstDoseDataEntry = mutableListOf<Entry>()
        val dailySecondDoseDataEntry = mutableListOf<Entry>()

        vaccinationHistory.forEachIndexed { index, data ->
            vaccinationPeriod.add(data.updated)
            firstDoseDataEntry.add(Entry(index.toFloat(), data.firstDose.total.toFloat()))
            secondDoseDataEntry.add(Entry(index.toFloat(), data.secondDose.total.toFloat()))
            dailyFirstDoseDataEntry.add(Entry(index.toFloat(), data.firstDose.added.toFloat()))
            dailySecondDoseDataEntry.add(Entry(index.toFloat(), data.secondDose.added.toFloat()))
        }
        val firstDoseDataSet =
            buildLineDataSet(firstDoseDataEntry, "Total $firstDoseString", R.color.firstDose)
        val secondDoseDataSet =
            buildLineDataSet(secondDoseDataEntry, "Total $secondDoseString", R.color.secondDose)
        val dailyFirstDoseDataSet =
            buildLineDataSet(dailyFirstDoseDataEntry, "Daily $firstDoseString", R.color.firstDose)
        val dailySecondDoseDataSet =
            buildLineDataSet(dailySecondDoseDataEntry, "Daily $secondDoseString", R.color.secondDose)

        val dateFormatter = AxisDateFormatter(vaccinationPeriod.toTypedArray())
        val overallLineData = LineData(listOf(firstDoseDataSet, secondDoseDataSet))
        val dailyLineData = LineData(listOf(dailyFirstDoseDataSet, dailySecondDoseDataSet))

        binding.apply {
            setupVaccinationLineChart(lineChartVaccination, overallLineData, dateFormatter)
            setupVaccinationLineChart(lineChartDailyVaccination, dailyLineData, dateFormatter)
        }
    }

    private fun buildLineDataSet(
        dataEntry: MutableList<Entry>, label: String, @ColorRes colorRes: Int
    ): LineDataSet {
        return LineDataSet(dataEntry, label).apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = resources.getColor(colorRes, null)
            fillColor = resources.getColor(colorRes, null)
            setDrawFilled(true)
            setDrawCircles(false)
        }
    }

    private fun setupVaccinationLineChart(
        lineChart: LineChart, lineData: LineData, dateFormatter: AxisDateFormatter
    ) {
        lineChart.apply {
            setTouchEnabled(true)
            isScaleXEnabled = true
            isDragEnabled = true
            data = lineData
            setMaxVisibleValueCount(15)
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = dateFormatter
                setDrawAxisLine(false)
                setDrawGridLines(true)
                description.text = "Date"
                granularity = 1f
                labelCount = 4
            }
            axisLeft.valueFormatter = LargeValueFormatter()
            axisRight.valueFormatter = LargeValueFormatter()
            legend.apply {
                isEnabled = true
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
            setOnChartValueSelectedListener(onLineChartItemSelected(this, dateFormatter))
            animateX(100)
        }
    }

    private fun onLineChartItemSelected(lineChart: LineChart, dateFormatter: AxisDateFormatter)
            : OnChartValueSelectedListener {

        return object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val marker: IMarker = VaccinationDataMarkerView(context, lineChart, dateFormatter)
                lineChart.marker = marker
            }

            override fun onNothingSelected() {
                lineChart.marker = null
            }
        }
    }

    private fun mapStringResources() {
        firstDoseString = getString(R.string.first_dose)
        secondDoseString = getString(R.string.second_dose)
    }

    companion object {
        private const val VACCINATION_DATA = "VACCINATION_DATA"
    }
}
