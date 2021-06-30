package com.tilikki.siagacovid.view.vaccine

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.siagacovid.databinding.ItemVaccinationGroupBinding
import com.tilikki.siagacovid.model.VaccinationDetail

class VaccinationGroupAdapter :
    ListAdapter<VaccinationDetail, VaccinationGroupAdapter.VaccinationGroupViewHolder>(DiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinationGroupViewHolder {
        return VaccinationGroupViewHolder(
            ItemVaccinationGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VaccinationGroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffUtils : DiffUtil.ItemCallback<VaccinationDetail>() {
        override fun areItemsTheSame(
            oldItem: VaccinationDetail,
            newItem: VaccinationDetail
        ): Boolean {
            return oldItem.group == newItem.group
        }

        override fun areContentsTheSame(
            oldItem: VaccinationDetail,
            newItem: VaccinationDetail
        ): Boolean {
            return oldItem == newItem
        }
    }

    class VaccinationGroupViewHolder(private val binding: ItemVaccinationGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vaccinationOverview: VaccinationDetail) {
            binding.run {
                vaccinationOverview.let {
                    setAccentColor(it.group.color)
                    tvVaccinationGroup.setText(it.group.groupName)
                    tvGroupTarget.text = it.target.toString()
                    tvFirstDose.text = it.firstDose.toString()
                    tvSecondDose.text = it.secondDose.toString()
                    tvFirstDosePercentage.text = "(${it.firstDosePercentage})"
                    tvSecondDosePercentage.text = "(${it.secondDosePercentage})"
                    pbVaccinationProgress.apply {
                        max = it.target
                        progress = it.firstDose
                        secondaryProgress = it.secondDose
                    }
                }
            }
        }

        private fun setAccentColor(@ColorRes colorRes: Int) {
            binding.run {
                val color = ContextCompat.getColor(root.context, colorRes)
                llVaccinationTarget.backgroundTintList = ColorStateList.valueOf(color)
                llFirstDose.backgroundTintList = ColorStateList.valueOf(color)
                llSecondDose.backgroundTintList = ColorStateList.valueOf(color)
                pbVaccinationProgress.progressTintList = ColorStateList.valueOf(color)
            }
        }
    }
}
