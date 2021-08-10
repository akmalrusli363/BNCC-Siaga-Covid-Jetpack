package com.tilikki.siagacovid.utils.chart

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class AxisDateFormatter(private val values: Array<Date>) : ValueFormatter() {
    private val simpleDateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)

    override fun getFormattedValue(value: Float): String {
        return if (value >= 0 && values.size > value.toInt()) {
            simpleDateFormatter.format(values[value.toInt()])
        } else {
            ""
        }
    }
}
