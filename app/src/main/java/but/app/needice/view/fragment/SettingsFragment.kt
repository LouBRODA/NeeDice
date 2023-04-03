package but.app.needice.view.fragment

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import but.app.needice.R
import but.app.needice.adaptor.FlagPagerAdapter
import but.app.needice.data.Stub
import java.util.*

class SettingsFragment : Fragment(), FlagPagerAdapter.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_screen, container, false)

        var data = Stub().load()

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val flagIds: List<Int> =
            listOf(R.drawable.flag_england, R.drawable.flag_france, R.drawable.flag_spain)
        val flagPagerAdapter = FlagPagerAdapter(requireContext(), flagIds)
        viewPager.adapter = flagPagerAdapter

        flagPagerAdapter.setOnClickListener(this)

        val btnPrev: Button = view.findViewById(R.id.arrow_left)
        val btnNext: Button = view.findViewById(R.id.arrow_right)

        val darkModeSelect: SwitchCompat = view.findViewById(R.id.dark_mode_select)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    btnPrev.visibility = View.INVISIBLE
                } else {
                    btnPrev.visibility = View.VISIBLE
                }

                if (position == flagPagerAdapter.count - 1) {
                    btnNext.visibility = View.INVISIBLE
                } else {
                    btnNext.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        btnPrev.setOnClickListener {
            viewPager.currentItem -= 1
        }

        btnNext.setOnClickListener {
            viewPager.currentItem += 1
        }

        // initial button state
        btnPrev.visibility = View.INVISIBLE
        if (flagPagerAdapter.count > 1) {
            btnNext.visibility = View.VISIBLE
        } else {
            btnNext.visibility = View.INVISIBLE
        }

        darkModeSelect.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        return view
    }

    override fun onItemClick(position: Int) {
        val language = when (position) {
            0 -> "en"
            1 -> "fr"
            2 -> "es"
            else -> "en" // fallback to English if index is out of range
        }
        changeLanguage(language)
    }

    private fun changeLanguage(language: String) {
        val locale = Locale(language)
        Log.d("SettingsFragment", "Selected language: $language")
        Log.d("SettingsFragment", "Locale: $locale")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)
        requireActivity().recreate()
    }

}
