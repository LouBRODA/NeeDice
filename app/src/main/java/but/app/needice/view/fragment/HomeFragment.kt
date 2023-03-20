package but.app.needice.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentContainerView
import but.app.needice.R
import but.app.needice.data.Stub

class HomeFragment : Fragment() {

    private lateinit var playButton : Button
    private lateinit var settingsButton : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FragmentContainerView>(R.id.home_screen)

        var data = Stub().load()

        playButton = view.findViewById<Button>(R.id.play_button)
        settingsButton = view.findViewById<Button>(R.id.settings_button)
    }
}