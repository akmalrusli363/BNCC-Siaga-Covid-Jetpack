package com.tilikki.siagacovid.utils.chart

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.tilikki.siagacovid.R

class SingleDataMarkerView : MarkerView {

    constructor(context: Context?) : super(context, R.layout.marker_view_single)
    constructor(
        context: Context?, lineChart: LineChart, xAxis: AxisDateFormatter, title: String
    ) : this(context) {
        this.lineChart = lineChart
        this.xAxis = xAxis
        this.title = title
    }
    constructor(
        context: Context?, lineChart: LineChart, xAxis: AxisDateFormatter,
        title: String, fieldColor: Int
    ) : this(context, lineChart, xAxis, title) {
        this.fieldColor.background = ColorDrawable(fieldColor)
    }

    private lateinit var lineChart: LineChart
    private lateinit var xAxis: AxisDateFormatter
    private lateinit var title: String

    private val titleText: TextView = findViewById(R.id.tvTitle)
    private val dateText: TextView = findViewById(R.id.tvDate)
    private val field: TextView = findViewById(R.id.tvField)
    private val fieldColor: View = findViewById(R.id.vField)

    private var mOffset: MPPointF? = null
    private var xOffsetMultiplier = 2

    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            mOffset = MPPointF((-(width / xOffsetMultiplier)).toFloat(), (-height).toFloat())
        }
        return mOffset!!
    }

    override fun refreshContent(e: Entry, highlight: Highlight) {
        val fieldValue = lineChart.data.getDataSetByIndex(0)
            .getEntryForXValue(e.x, Float.NaN, DataSet.Rounding.CLOSEST)

        titleText.text = title
        dateText.text = xAxis.getFormattedValue(e.x)
        field.text = String.format("%,.0f", fieldValue.y)

        xOffsetMultiplier = when {
            e.x < lineChart.xChartMax * 0.2 -> 3
            e.x > lineChart.xChartMax * 0.8 -> 1
            else -> 2
        }

        super.refreshContent(e, highlight)
    }

}
