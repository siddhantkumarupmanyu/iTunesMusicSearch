package sku.challenge.itunesmusicsearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import sku.challenge.itunesmusicsearch.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {


    private var _binding: FragmentGalleryBinding? = null

    private val binding: FragmentGalleryBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_gallery,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.searchView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.close,
                    0
                )
            } else {
                if (binding.searchView.length() == 0) {
                    binding.searchView.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                }
            }
        }

        binding.searchView.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP)
                &&
                (event.rawX >= (binding.searchView.right - binding.searchView.totalPaddingRight))
            ) {
                binding.searchView.setText("")
                true
            } else {
                false
            }
        }

        binding.searchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.searchView.clearFocus()
                val imm: InputMethodManager? =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

                imm?.hideSoftInputFromWindow(view.windowToken, 0)
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}