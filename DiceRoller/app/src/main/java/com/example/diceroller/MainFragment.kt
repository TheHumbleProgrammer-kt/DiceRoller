package com.example.diceroller

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.diceroller.databinding.FragmentMainBinding

const val KEY_RESULT_TEXT = "result_text_key"
const val KEY_RESULTS = "results_key"

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var result: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        result = Result()
        fun getSavedInstanceState() {
            binding.resultText.text = savedInstanceState?.getString(KEY_RESULT_TEXT, "") ?: ""
            result.intString = savedInstanceState?.getString(KEY_RESULTS, "") ?: ""
        }

        getSavedInstanceState()

        binding.rollButton.setOnClickListener { rollDice() }
        binding.cuButton.setOnClickListener { countUp() }
        binding.ssButton.setOnClickListener { shareSequence() }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
        if (getShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.shareSequence).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareSequence -> shareSequence()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, result.intString)
        return shareIntent
    }

    private fun shareSequence() {
        startActivity(getShareIntent())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RESULT_TEXT, binding.resultText.text.toString())
        outState.putString(KEY_RESULTS, result.intString)

    }

    private fun rollDice() {

        val randomInt = (1..6).random()

        result.intString += randomInt.toString()

        binding.resultText.text = randomInt.toString()
        Toast.makeText(requireContext(), result.intString, Toast.LENGTH_SHORT).show()

    }

    private fun countUp() {
        binding.apply {
            when (resultText.text.toString()) {
                "" -> resultText.text = "1"
                "6" -> return
                else -> {
                    if (resultText.text.toString() < "6") {
                        val resText = (resultText.text.toString().toInt() + 1).toString()
                        resultText.text = resText
                        result.updateResult(resText)
                    } else {
                        return
                    }
                }
            }
        }
        Toast.makeText(requireContext(), result.intString, Toast.LENGTH_SHORT).show()

    }


}