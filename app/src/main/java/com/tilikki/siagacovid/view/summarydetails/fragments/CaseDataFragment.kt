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
import com.tilikki.siagacovid.databinding.FragmentCaseDetailCasesBinding
import com.tilikki.siagacovid.model.CaseOverview
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.utils.chart.AxisDateFormatter
import com.tilikki.siagacovid.utils.chart.SingleDataMarkerView
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel
import java.util.*

class CaseDataFragment : BaseCaseDetailFragment() {

    private lateinit var binding: FragmentCaseDetailCasesBinding
    private lateinit var historyViewModel: CaseHistoryViewModel

    private lateinit var activeCaseString: String
    private lateinit var dailyConfirmedCaseString: String
    private lateinit var dailyActiveCaseString: String
    private lateinit var dailyRecoveredCaseString: String
    private lateinit var dailyDeathCaseString: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)
        historyViewModel = ViewModelProvider(requireActivity())
            .get(CaseHistoryViewModel::class.java)

        binding = FragmentCaseDetailCasesBinding.inflate(inflater, container, false)
        mapStringResources()
        if (!historyViewModel.isFetched) {
            historyViewModel.fetchData()
        }
        startObserve()

        return binding.root
    }

    override fun setupViewModelObserver() {
        rootViewModel.caseOverview.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.run {
                    setStatisticPairs(it.confirmedCase, tvTotalCaseCount, tvDailyConfirmedCaseCount)
                    setStatisticPairs(it.activeCase, tvPositiveCount, tvDailyPositiveCount)
                    setStatisticPairs(it.recoveredCase, tvRecoveredCount, tvDailyRecoveredCount)
                    setStatisticPairs(it.deathCase, tvDeathCount, tvDailyDeathCount)
                }
            }
        })
        historyViewModel.caseHistory.observe(viewLifecycleOwner, {
            initializeCaseHistoryChart(it)
        })
    }

    override fun toggleFetchState(isFetching: Boolean) {
        binding.apply {
            toggleViewFetchState(
                isFetching, listOf(tvTotalCaseCount, tvDailyConfirmedCaseCount), pbTotalCase
            )
            toggleViewFetchState(
                isFetching, listOf(tvPositiveCount, tvDailyPositiveCount), pbPositive
            )
            toggleViewFetchState(
                isFetching, listOf(tvRecoveredCount, tvDailyRecoveredCount), pbRecovered
            )
            toggleViewFetchState(isFetching, listOf(tvDeathCount, tvDailyDeathCount), pbDeath)
        }
    }

    private fun initializeCaseHistoryChart(vaccinationHistory: List<CaseOverview>) {
        val caseHistoryPeriod = mutableListOf<Date>()
        val activeCaseDataEntry = mutableListOf<Entry>()
        val dailyActiveCaseDataEntry = mutableListOf<Entry>()
        val dailyConfirmedCaseDataEntry = mutableListOf<Entry>()
        val dailyRecoveredCaseDataEntry = mutableListOf<Entry>()
        val dailyDeathCaseDataEntry = mutableListOf<Entry>()

        vaccinationHistory.forEachIndexed { index, data ->
            caseHistoryPeriod.add(data.lastUpdated)
            activeCaseDataEntry.add(Entry(index.toFloat(), data.activeCase.total.toFloat()))
            dailyActiveCaseDataEntry.add(Entry(index.toFloat(), data.activeCase.added.toFloat()))
            dailyConfirmedCaseDataEntry.add(
                Entry(index.toFloat(), data.confirmedCase.added.toFloat())
            )
            dailyRecoveredCaseDataEntry.add(
                Entry(index.toFloat(), data.recoveredCase.added.toFloat())
            )
            dailyDeathCaseDataEntry.add(Entry(index.toFloat(), data.deathCase.added.toFloat()))
        }
        val activeCaseDataSet =
            buildLineDataSet(activeCaseDataEntry, activeCaseString, R.color.teal)
        val dailyActiveCaseDataSet =
            buildLineDataSet(dailyActiveCaseDataEntry, dailyActiveCaseString, R.color.softIndigo)
        val dailyConfirmedCaseDataSet =
            buildLineDataSet(dailyConfirmedCaseDataEntry, dailyConfirmedCaseString, R.color.indigo)
        val dailyRecoveredCaseDataSet =
            buildLineDataSet(dailyRecoveredCaseDataEntry, dailyRecoveredCaseString, R.color.green)
        val dailyDeathCaseDataSet =
            buildLineDataSet(dailyDeathCaseDataEntry, dailyDeathCaseString, R.color.red)

        val dateFormatter = AxisDateFormatter(caseHistoryPeriod.toTypedArray())

        binding.apply {
            setupSingleLineChart(lineChartActiveCase, activeCaseDataSet, dateFormatter)
            setupSingleLineChart(lineChartDailyActiveCase, dailyActiveCaseDataSet, dateFormatter)
            setupSingleLineChart(
                lineChartDailyConfirmedCase, dailyConfirmedCaseDataSet, dateFormatter
            )
            setupSingleLineChart(
                lineChartDailyRecoveredCase, dailyRecoveredCaseDataSet, dateFormatter
            )
            setupSingleLineChart(lineChartDailyDeathCase, dailyDeathCaseDataSet, dateFormatter)
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

    private fun setupSingleLineChart(
        lineChart: LineChart, lineDataSet: LineDataSet, dateFormatter: AxisDateFormatter,
    ) {
        val lineData = LineData(lineDataSet)
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
            setOnChartValueSelectedListener(
                onLineChartItemSelected(
                    this, dateFormatter, lineDataSet.label, lineDataSet.color
                )
            )
            animateX(100)
        }
    }

    private fun onLineChartItemSelected(
        lineChart: LineChart, dateFormatter: AxisDateFormatter,
        title: String, color: Int
    ): OnChartValueSelectedListener {

        return object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val marker: IMarker =
                    SingleDataMarkerView(context, lineChart, dateFormatter, title, color)
                lineChart.marker = marker
            }

            override fun onNothingSelected() {
                lineChart.marker = null
            }
        }
    }

    private fun mapStringResources() {
        activeCaseString = getString(R.string.active_case)
        dailyConfirmedCaseString = getString(R.string.daily_confirmed_case)
        dailyActiveCaseString = getString(R.string.daily_active_case)
        dailyRecoveredCaseString = getString(R.string.daily_recovered_case)
        dailyDeathCaseString = getString(R.string.daily_death_case)
    }

    companion object {
        private const val COVID_CASE_DATA_DETAIL = "COVID_CASE_DATA_DETAIL"
    }
}
