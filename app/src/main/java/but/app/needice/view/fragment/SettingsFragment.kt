package but.app.needice.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import but.app.needice.R
import but.app.needice.adaptor.FlagPagerAdapter
import but.app.needice.data.Stub
import java.util.*


class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_screen, container, false)

        var data = Stub().load()

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val flagIds: List<Int> =
            Arrays.asList(R.drawable.flag_england, R.drawable.flag_france, R.drawable.flag_spain)
        val flagPagerAdapter = FlagPagerAdapter(requireContext(), flagIds)
        viewPager.adapter = flagPagerAdapter

        val btnPrev: Button = view.findViewById(R.id.arrow_left)
        val btnNext: Button = view.findViewById(R.id.arrow_right)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

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

            override fun onPageScrollStateChanged(state: Int) {
            }
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

        return view
    }
}
