package but.app.needice.view.fragment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
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
import java.util.*

class SettingsFragment : Fragment(), FlagPagerAdapter.OnClickListener {


    //---ON CREATE VIEW---//

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_screen, container, false)

        val systemNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (systemNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        setupViewPager(view)
        setupButtons(view)
        setupTimezoneAPI(view)
        setupDarkModeSwitch(view)

        return view
    }


    //---SETUP VIEWPAGER---//

    private fun setupViewPager(view: View) {
        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val flagIds: List<Int> =
            listOf(R.drawable.flag_england, R.drawable.flag_france, R.drawable.flag_italy)
        val flagPagerAdapter = FlagPagerAdapter(requireContext(), flagIds)
        viewPager.adapter = flagPagerAdapter

        flagPagerAdapter.setOnClickListener(this)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                val btnPrev: Button = view.findViewById(R.id.arrow_left)
                val btnNext: Button = view.findViewById(R.id.arrow_right)

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

        val btnPrev: Button = view.findViewById(R.id.arrow_left)
        val btnNext: Button = view.findViewById(R.id.arrow_right)

        // initial button state
        btnPrev.visibility = View.INVISIBLE
        if (flagPagerAdapter.count > 1) {
            btnNext.visibility = View.VISIBLE
        } else {
            btnNext.visibility = View.INVISIBLE
        }
    }


    //---SETUP BUTTONS---//

    private fun setupButtons(view: View) {
        val btnPrev: Button = view.findViewById(R.id.arrow_left)
        val btnNext: Button = view.findViewById(R.id.arrow_right)

        btnPrev.setOnClickListener {
            val viewPager: ViewPager = view.findViewById(R.id.viewPager)
            viewPager.currentItem -= 1
        }

        btnNext.setOnClickListener {
            val viewPager: ViewPager = view.findViewById(R.id.viewPager)
            viewPager.currentItem += 1
        }
    }


    //---SETUP API---//

    private fun setupTimezoneAPI(view: View) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://timeapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val timezone = when (resources.configuration.locale.language) {
            "en" -> "Europe/London"
            "fr" -> "Europe/Paris"
            "it" -> "Europe/Rome"
            else -> "Europe/London"
        }
        val apiService = retrofit.create(ITimezoneAPI::class.java)
        val call = apiService.getTimezone(timezone)
        call?.enqueue(object : Callback<TimezoneResponse?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<TimezoneResponse?>,
                response: Response<TimezoneResponse?>
            ) {
                val timezoneResponse = response.body()
                if (timezoneResponse != null) {
                    val time : TextView = view.findViewById(R.id.time)
                    val date = timezoneResponse.date
                    val timeO = timezoneResponse.time
                    time.text = "$date $timeO"
                }
            }
            override fun onFailure(call: Call<TimezoneResponse?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    //---SETUP DARK MODE---//

    private fun setupDarkModeSwitch(view: View) {
        val darkModeSwitch: SwitchCompat = view.findViewById(R.id.dark_mode_select)

        // Get the current night mode state from preferences and set the switch accordingly
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        darkModeSwitch.isChecked = nightMode == AppCompatDelegate.MODE_NIGHT_YES

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


    //---ON FLAG CLICK---//

    override fun onItemClick(position: Int) {
        val language = when (position) {
            0 -> "en"
            1 -> "fr"
            2 -> "it"
            else -> "en"
        }
        changeLanguage(language)
    }


    //---CHANGE LANGUAGE---//

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
