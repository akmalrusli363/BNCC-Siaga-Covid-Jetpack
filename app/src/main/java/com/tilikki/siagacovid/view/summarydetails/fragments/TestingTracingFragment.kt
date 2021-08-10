package com.tilikki.siagacovid.view.summarydetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.FragmentCaseDetailTestingTracingBinding
import com.tilikki.siagacovid.model.CovidTestingData
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.utils.chart.AxisDateFormatter
import com.tilikki.siagacovid.utils.chart.TestingDataMarkerView
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel
import java.util.*

class TestingTracingFragment : BaseCaseDetailFragment() {

    private lateinit var vaccineTestingViewModel: VaccineTestingViewModel
    private lateinit var binding: FragmentCaseDetailTestingTracingBinding

    private lateinit var pcrTestString: String
    private lateinit var antigenTestString: String
    private lateinit var testSampleString: String
    private lateinit var peopleTestedString: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)
        vaccineTestingViewModel =
            ViewModelProvider(this).get(VaccineTestingViewModel::class.java)

        binding = FragmentCaseDetailTestingTracingBinding.inflate(inflater, container, false)
        pcrTestString = getString(R.string.pcr_tcm_test)
        antigenTestString = getString(R.string.antigen_test)
        testSampleString = getString(R.string.test_samples)
        peopleTestedString = getString(R.string.people_tested)
        if (!vaccineTestingViewModel.isFetched) {
            vaccineTestingViewModel.fetchData()
        }
        startObserve()
        return binding.root
    }

    override fun setupViewModelObserver() {
        rootViewModel.testTracing.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.setStatisticPairs(
                    it.antigen.samples, tvAntigenSample, tvDailyAntigenSample
                )
                ViewUtility.setStatisticPairs(
                    it.antigen.peopleTested, tvPeopleAntigenTested, tvDailyPeopleAntigenTested
                )
                ViewUtility.setStatisticPairs(it.pcr.samples, tvPCRSample, tvDailyPCRSample)
                ViewUtility.setStatisticPairs(
                    it.pcr.peopleTested, tvPeoplePCRTested, tvDailyPeoplePCRTested
                )
                ViewUtility.setStatisticPairs(it.overall.samples, tvTestSample, tvDailyTestSample)
                ViewUtility.setStatisticPairs(
                    it.overall.peopleTested, tvPeopleTested, tvDailyPeopleTested
                )
            }
        })
        vaccineTestingViewModel.tracingHistory.observe(viewLifecycleOwner, {
            val temporary = it.drop(1)
            initializeTestSamplesSummaryChart(temporary)
            initializePeopleTestSummaryChart(temporary)
        })
    }

    override fun toggleFetchState(isFetching: Boolean) {
        binding.apply {
            toggleViewFetchState(
                isFetching, listOf(tvAntigenSample, tvDailyAntigenSample), pbAntigenSample
            )
            toggleViewFetchState(
                isFetching, listOf(tvPeopleAntigenTested, tvDailyPeopleAntigenTested),
                pbPeopleAntigenTested
            )
            toggleViewFetchState(
                isFetching, listOf(tvPCRSample, tvDailyPCRSample), pbPCRSample
            )
            toggleViewFetchState(
                isFetching, listOf(tvPeoplePCRTested, tvDailyPeoplePCRTested), pbPeoplePCRTested
            )
            toggleViewFetchState(
                isFetching, listOf(tvTestSample, tvDailyTestSample), pbTestSample
            )
            toggleViewFetchState(
                isFetching, listOf(tvPeopleTested, tvDailyPeopleTested), pbPeopleTested
            )
        }
    }

    private fun initializeTestSamplesSummaryChart(vaccinationHistory: List<CovidTestingData>) {
        val testingPeriod = mutableListOf<Date>()
        val pcrAntigenSamples = mutableListOf<BarEntry>()
        val totalSamples = mutableListOf<Entry>()

        vaccinationHistory.forEachIndexed { index, data ->
            testingPeriod.add(data.updated)
            val covidTestSampleSet = floatArrayOf(
                data.pcr.samples.added.toFloat(), data.antigen.samples.added.toFloat()
            )
            pcrAntigenSamples.add(BarEntry(index.toFloat(), covidTestSampleSet))
            totalSamples.add(Entry(index.toFloat(), data.overall.samples.added.toFloat()))
        }
        val covidTestName: Array<String> = arrayOf(pcrTestString, antigenTestString)
        val covidTestColor: Array<Int> = arrayOf(R.color.pcr, R.color.antigen)
        val totalSampleDataSet =
            buildLineDataSet(totalSamples, "Total samples", R.color.indigo2)
        val pcrAntigenDataSet = buildStackedBarDataSet(
            pcrAntigenSamples, covidTestName, covidTestColor
        )

        val dateFormatter = AxisDateFormatter(testingPeriod.toTypedArray())
        val lineData = LineData(listOf(totalSampleDataSet))
        val barData = BarData(pcrAntigenDataSet)

        binding.apply {
            setupTestingCombinedChart(
                lineChartTestSamples, buildCombinedChartData(lineData, barData),
                dateFormatter, testSampleString
            )
        }
    }

    private fun initializePeopleTestSummaryChart(vaccinationHistory: List<CovidTestingData>) {
        val testingPeriod = mutableListOf<Date>()
        val pcrAntigenTests = mutableListOf<BarEntry>()
        val totalTests = mutableListOf<Entry>()

        vaccinationHistory.forEachIndexed { index, data ->
            testingPeriod.add(data.updated)
            val covidTestSampleSet = floatArrayOf(
                data.pcr.peopleTested.added.toFloat(), data.antigen.peopleTested.added.toFloat()
            )
            pcrAntigenTests.add(BarEntry(index.toFloat(), covidTestSampleSet))
            totalTests.add(Entry(index.toFloat(), data.overall.peopleTested.added.toFloat()))
        }
        val covidTestName: Array<String> = arrayOf(pcrTestString, antigenTestString)
        val covidTestColor: Array<Int> = arrayOf(R.color.pcr, R.color.antigen)
        val totalPersonTestedDataSet =
            buildLineDataSet(totalTests, "Total people tests", R.color.indigo2)
        val pcrAntigenDataSet =  buildStackedBarDataSet(
            pcrAntigenTests, covidTestName, covidTestColor
        )

        val dateFormatter = AxisDateFormatter(testingPeriod.toTypedArray())
        val lineData = LineData(listOf(totalPersonTestedDataSet))
        val barData = BarData(pcrAntigenDataSet)

        binding.apply {
            setupTestingCombinedChart(
                lineChartPeopleTests, buildCombinedChartData(lineData, barData),
                dateFormatter, peopleTestedString
            )
        }
    }

    private fun buildCombinedChartData(
        lineData: LineData, barData: BarData
    ): CombinedData {
        return CombinedData().apply {
            setData(lineData)
            setData(barData)
        }
    }

    private fun buildLineDataSet(
        dataEntry: List<Entry>, label: String, @ColorRes colorRes: Int
    ): LineDataSet {
        return LineDataSet(dataEntry, label).apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = resources.getColor(colorRes, null)
            setDrawFilled(true)
            setDrawCircles(false)
        }
    }

    private fun buildStackedBarDataSet(
        dataEntrySet: List<BarEntry>, stackLabel: Array<String>, @ColorRes colorRes: Array<Int>
    ): BarDataSet {
        return BarDataSet(dataEntrySet, "").apply {
            stackLabels = stackLabel
            colors = colorRes.map {
                resources.getColor(it, null)
            }
        }
    }

    private fun setupTestingCombinedChart(
        combinedChart: CombinedChart, combinedData: CombinedData,
        dateFormatter: AxisDateFormatter, title: String
    ) {
        combinedChart.apply {
            setTouchEnabled(true)
            isScaleXEnabled = true
            isDragEnabled = true
            data = combinedData
            setMaxVisibleValueCount(10)
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
            setOnChartValueSelectedListener(onCombinedChartItemSelected(this, dateFormatter, title))
            animateX(100)
        }
    }

    private fun onCombinedChartItemSelected(
        combinedChart: CombinedChart, dateFormatter: AxisDateFormatter, title: String
    ): OnChartValueSelectedListener {

        return object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val marker: IMarker =
                    TestingDataMarkerView(context, combinedChart, dateFormatter, title)
                combinedChart.marker = marker
            }

            override fun onNothingSelected() {
                combinedChart.marker = null
            }
        }
    }

    companion object {
        private const val COVID_TESTING_DATA = "COVID_TESTING_DATA"
    }
}
