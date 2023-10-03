package com.diptanil.surveyassignment.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diptanil.surveyassignment.data.model.QuestionAnswer
import com.diptanil.surveyassignment.databinding.QuestionItemBinding
import java.util.Locale


class QuestionAnswersAdapter(questionAnswers: List<QuestionAnswer>) :
    RecyclerView.Adapter<QuestionAnswersAdapter.QuestionAnswerViewHolder>() {

    private val questionAnswers = mutableListOf<QuestionAnswer>()

    init {
        this.questionAnswers.addAll(questionAnswers)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionAnswerViewHolder {
        return QuestionAnswerViewHolder(
            QuestionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionAnswerViewHolder, position: Int) {
        holder.setQuestionAnswer(questionAnswers[position])
    }

    class QuestionAnswerViewHolder(private val questionAnswerItemBinding: QuestionItemBinding) :
        RecyclerView.ViewHolder(questionAnswerItemBinding.root) {
        fun setQuestionAnswer(questionAnswer: QuestionAnswer) {
            questionAnswerItemBinding.question.text = questionAnswer.question
            questionAnswerItemBinding.answer.text = questionAnswer.answer.lowercase(Locale.getDefault())

        }
    }

    class QuestionAnswersDiffUtil(
        private val oldList: List<QuestionAnswer>,
        private val newList: List<QuestionAnswer>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                    oldList[oldItemPosition].uuid == newList[newItemPosition].uuid
        }

    }

    fun setQuestionAnswer(questionAnswer: List<QuestionAnswer>) {
        val diffCallback = QuestionAnswersDiffUtil(this.questionAnswers, questionAnswers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.questionAnswers.clear()
        this.questionAnswers.addAll(questionAnswer)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return questionAnswers.size
    }
}