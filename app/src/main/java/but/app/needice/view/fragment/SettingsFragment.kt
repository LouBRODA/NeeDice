package but.app.needice.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import but.app.needice.R
import but.app.needice.adaptor.FlagPagerAdapter
import but.app.needice.api.ITimezoneAPI
import but.app.needice.api.TimezoneResponse
import but.app.needice.data.Stub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : Fragment(), FlagPagerAdapter.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_screen, container, false)

        var data = Stub().load()

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val flagIds: List<Int> =
            listOf(R.drawable.flag_england, R.drawable.flag_france, R.drawable.flag_italy)
        val flagPagerAdapter = FlagPagerAdapter(requireContext(), flagIds)
        viewPager.adapter = flagPagerAdapter

        flagPagerAdapter.setOnClickListener(this)

        val btnPrev: Button = view.findViewById(R.id.arrow_left)
        val btnNext: Button = view.findViewById(R.id.arrow_right)

        val time : TextView = view.findViewById(R.id.time)
        val selectedLanguage : TextView = view.findViewById(R.id.selected_language)


        val systemNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (systemNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        val darkModeSelect: SwitchCompat = view.findViewById(R.id.dark_mode_select)
        val isDarkModeOn = AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES
        darkModeSelect.isChecked = isDarkModeOn

        val retrofit = Retrofit.Builder()
            .baseUrl("https://worldtimeapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        // Appel à l'API

        val timezone = when (resources.configuration.locale.language) {
            "en" -> "Europe/London"
            "fr" -> "Europe/Paris"
            "it" -> "Europe/Rome"
            else -> "Europe/London"
        }
        val apiService = retrofit.create(ITimezoneAPI::class.java)
        val call = apiService.getTimezone(timezone)
        call.enqueue(object : Callback<TimezoneResponse> {
            override fun onResponse(call: Call<TimezoneResponse>, response: Response<TimezoneResponse>) {
                val timezoneResponse = response.body()
                if (timezoneResponse != null) {
                    val datetime = timezoneResponse.datetime
                    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat("dd-MM-yyyy : HH:mm:ss", Locale.getDefault())
                    val inputDate = inputDateFormat.parse(datetime)
                    val outputDate = outputDateFormat.format(inputDate!!)
                    time.text = outputDate
                }
            }
            override fun onFailure(call: Call<TimezoneResponse>, t: Throwable) {
                // Traitement des erreurs
            }
        })

        // Changement de page de drapeau
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

        // Set Dark Mode
        darkModeSelect.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        val currentLanguage = resources.configuration.locale.language
        val newText = getString(R.string.selected_language, currentLanguage)
        selectedLanguage.text = newText

        return view
    }

    // Switch language onClick
    override fun onItemClick(position: Int) {
        val language = when (position) {
            0 -> "en"
            1 -> "fr"
            2 -> "it"
            else -> "en"
        }
        changeLanguage(language)
    }

    // Definition nouveau langage
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
