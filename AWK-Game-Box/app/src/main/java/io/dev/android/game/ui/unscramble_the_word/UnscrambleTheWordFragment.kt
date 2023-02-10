package io.dev.android.game.ui.unscramble_the_word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class UnscrambleTheWordFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(): UnscrambleTheWordFragment {
            return UnscrambleTheWordFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold() { padding ->
                    Surface(modifier = Modifier.padding(padding)) {
                        UnscrambleTheWordPage()
                    }
                }
            }
        }
    }

    @Composable
    private fun UnscrambleTheWordPage() {
        //
    }

}