package com.asrul.covidvaccine.ui.assessment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.data.api.response.AssessmentItem
import com.asrul.covidvaccine.databinding.AssessmentItemBinding

class AssessmentAdapter: RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>() {

    private var onItemSelectCallback: OnItemSelectCallback? = null

    fun setOnItemSelectCallback(onItemSelectCallback: OnItemSelectCallback) {
        this.onItemSelectCallback = onItemSelectCallback
    }

    var lastCheckedRadioGroup: Int? = null

    val listAssessment = ArrayList<AssessmentItem?>()

    fun setAssessment(assessment: List<AssessmentItem?>) {
        this.listAssessment.clear()
        this.listAssessment.addAll(assessment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentViewHolder {
        val binding = AssessmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssessmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssessmentViewHolder, position: Int) {
        val assessment = listAssessment[position]

        holder.bind(assessment, position)
    }

    override fun getItemCount(): Int = listAssessment.size

    inner class AssessmentViewHolder(private val binding: AssessmentItemBinding ): RecyclerView.ViewHolder(binding.root) {
        fun bind(assessment: AssessmentItem?, position: Int) {
            with(binding) {
                tvQuestion.text = assessment?.question
                tvNo.text = "${assessment?.no}/6"

                rgAssessment.tag = position
                if (assessment?.isAnswered == true) {
                    rgAssessment.check(assessment.checkedId)
                } else {
                    rgAssessment.check(-1)
                }

                rgAssessment.setOnCheckedChangeListener { radioGroup, i ->
                    lastCheckedRadioGroup = radioGroup.tag as Int
                    val data = listAssessment[lastCheckedRadioGroup!!]
                    data?.isAnswered = true
                    data?.checkedId = i
                    Log.e("AssessmentAdapter", "Pos: ${lastCheckedRadioGroup} onCheckedChange + ${data?.checkedId}")

                    onItemSelectCallback?.onItemSelected(data, lastCheckedRadioGroup!!)
                }
            }
        }
    }

    interface OnItemSelectCallback {
        fun onItemSelected(assessment: AssessmentItem?, lastChecked: Int)
    }
}