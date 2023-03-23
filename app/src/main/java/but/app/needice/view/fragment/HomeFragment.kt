package but.app.needice.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import but.app.needice.R
import but.app.needice.data.Stub

class HomeFragment : Fragment() {

    private lateinit var playButton : Button
    private lateinit var settingsButton : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FragmentContainerView>(R.id.homeFragment)

        var data = Stub().load()

        playButton = view.findViewById<Button>(R.id.play_button)
        settingsButton = view.findViewById<Button>(R.id.settings_button)

        playButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_playFragment)
        }

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

    }
}