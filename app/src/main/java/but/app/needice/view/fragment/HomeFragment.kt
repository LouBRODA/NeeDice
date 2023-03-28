package but.app.needice.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import but.app.needice.R
import but.app.needice.data.Stub
import but.app.needice.view.PlayFragment

class HomeFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_screen, container, false)

        var data = Stub().load()

        view.findViewById<Button>(R.id.play_button).setOnClickListener{
            navController.navigate(R.id.playFragment)
        }

        view.findViewById<Button>(R.id.settings_button).setOnClickListener{
            navController.navigate(R.id.settingsFragment)
        }
        return view
    }
}