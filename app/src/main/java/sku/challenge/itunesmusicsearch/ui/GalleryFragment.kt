package sku.challenge.itunesmusicsearch.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import sku.challenge.itunesmusicsearch.R
import sku.challenge.itunesmusicsearch.databinding.FragmentGalleryBinding
import sku.challenge.itunesmusicsearch.vo.Track


@AndroidEntryPoint
class GalleryFragment : Fragment() {


    private var _binding: FragmentGalleryBinding? = null

    private val binding: FragmentGalleryBinding
        get() = _binding!!

    private val viewModel: GalleryViewModel by viewModels()

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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.gridView.adapter = GalleryAdapter()

        // (binding.gridView.adapter as GalleryAdapter).tracks = fakeData()

        // todo: uncomment
        // lifecycleScope.launch {
        //     repeatOnLifecycle(Lifecycle.State.STARTED) {
        //         viewModel.tracks.collect { result ->
        //             when (result) {
        //                 is GalleryViewModel.TracksResult.Loading -> showProgressIndicator()
        //                 is GalleryViewModel.TracksResult.Success -> updateTracks(result)
        //             }
        //         }
        //     }
        // }

        // todo clean this code
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

    private fun updateTracks(result: GalleryViewModel.TracksResult.Success) {
        hideProgressIndicator()
        (binding.gridView.adapter as GalleryAdapter).tracks = result.tracks
    }

    private fun hideProgressIndicator() {
        binding.progressIndicator.visibility = View.GONE
    }

    private fun showProgressIndicator() {
        binding.progressIndicator.visibility = View.VISIBLE
    }

    private fun fakeData(): List<Track> {
        return listOf(
            Track(1, "overdrive", "krsna", ""),
            Track(2, "overdrive", "krsna2", ""),
            Track(3, "overdrive", "krsna3", ""),
            Track(4, "overdrive", "krsna4", ""),
            Track(5, "overdrive", "krsna5", ""),
            Track(6, "overdrive", "krsna6", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
            Track(7, "overdrive", "krsna7", ""),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}