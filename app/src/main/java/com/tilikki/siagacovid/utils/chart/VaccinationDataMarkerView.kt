package com.tilikki.siagacovid.utils.chart

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.tilikki.siagacovid.R

class VaccinationDataMarkerView : MarkerView {

    constructor(context: Context?) : super(context, R.layout.marker_view_vaccination)
    constructor(
        context: Context?, lineChart: LineChart, xAxis: AxisDateFormatter
    ) : this(context) {
        this.lineChart = lineChart
        this.xAxis = xAxis
    }

    private lateinit var lineChart: LineChart
    private lateinit var xAxis: AxisDateFormatter

    private val dateText: TextView = findViewById(R.id.tvDate)
    private val firstDose: TextView = findViewById(R.id.tvFirstDose)
    private val secondDose: TextView = findViewById(R.id.tvSecondDose)

    private var mOffset: MPPointF? = null
    private var xOffsetMultiplier = 2

    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            mOffset = MPPointF((-(width / xOffsetMultiplier)).toFloat(), (-height).toFloat())
        }
        return mOffset!!
    }

    override fun refreshContent(e: Entry, highlight: Highlight) {
        val firstDoseValue = lineChart.data.getDataSetByIndex(0)
            .getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST)
        val secondDoseValue = lineChart.data.getDataSetByIndex(1)
            .getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST)

        dateText.text = xAxis.getFormattedValue(e.x)
        firstDose.text = String.format("%,.0f", firstDoseValue.y)
        secondDose.text = String.format("%,.0f", secondDoseValue.y)

        xOffsetMultiplier = when {
            e.x < lineChart.xChartMax * 0.2 -> 3
            e.x > lineChart.xChartMax * 0.8 -> 1
            else -> 2
        }

        super.refreshContent(e, highlight)
    }

}
