package com.tilikki.siagacovid.utils.chart

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.tilikki.siagacovid.R

class TestingDataMarkerView : MarkerView {

    constructor(context: Context?) : super(context, R.layout.marker_view_testing_tracing)
    constructor(
        context: Context?, combinedChart: CombinedChart, xAxis: AxisDateFormatter, title: String
    ) : this(context) {
        this.combinedChart = combinedChart
        this.xAxis = xAxis
        this.title = title
    }

    private lateinit var combinedChart: CombinedChart
    private lateinit var xAxis: AxisDateFormatter
    private lateinit var title: String

    private val titleText: TextView = findViewById(R.id.tvTitle)
    private val dateText: TextView = findViewById(R.id.tvDate)
    private val pcr: TextView = findViewById(R.id.tvPCRTest)
    private val antigen: TextView = findViewById(R.id.tvAntigen)
    private val total: TextView = findViewById(R.id.tvTotal)

    private var mOffset: MPPointF? = null
    private var xOffsetMultiplier = 2

    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            mOffset = MPPointF((-(width / xOffsetMultiplier)).toFloat(), (-height).toFloat())
        }
        return mOffset!!
    }

    override fun refreshContent(e: Entry, highlight: Highlight) {
        val pcrValue = combinedChart.barData.getDataSetByIndex(0)
            .getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST).yVals[0]
        val antigenValue = combinedChart.barData.getDataSetByIndex(0)
            .getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST).yVals[1]
        val totalValue = combinedChart.lineData.getDataSetByIndex(0)
            .getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST)

        titleText.text = title
        dateText.text = xAxis.getFormattedValue(e.x)
        pcr.text = String.format("%,.0f", pcrValue)
        antigen.text = String.format("%,.0f", antigenValue)
        total.text = String.format("%,.0f", totalValue.y)

        xOffsetMultiplier = when {
            e.x < combinedChart.xChartMax * 0.2 -> 3
            e.x > combinedChart.xChartMax * 0.8 -> 1
            else -> 2
        }

        super.refreshContent(e, highlight)
    }

}
