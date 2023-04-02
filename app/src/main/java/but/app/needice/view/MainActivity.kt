package but.app.needice.view


import android.content.Context
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import but.app.needice.R
import but.app.needice.language.OnLanguageChangeListener
import but.app.needice.view.fragment.SettingsFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_host_fragment)
    }
}

