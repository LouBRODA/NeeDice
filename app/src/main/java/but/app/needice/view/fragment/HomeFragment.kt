package but.app.needice.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import but.app.needice.R
import but.app.needice.data.Stub

class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val orientation = resources.configuration.orientation
        val view = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.home_screen, container, false)
        } else {
            inflater.inflate(R.layout.home_screen, container, false)
        }

        viewContainer = container ?: view as ViewGroup
        var data = Stub().load()

        view.findViewById<Button>(R.id.play_button).setOnClickListener{
            navController.navigate(R.id.playFragment)
        }

        view.findViewById<Button>(R.id.settings_button).setOnClickListener{
            navController.navigate(R.id.settingsFragment)
        }
        return view
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val orientation = newConfig.orientation
        val view = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutInflater.inflate(R.layout.home_screen, viewContainer, false)
        } else {
            layoutInflater.inflate(R.layout.home_screen, viewContainer, false)
        }
        viewContainer.removeAllViews()
        viewContainer.addView(view)
    }
}
