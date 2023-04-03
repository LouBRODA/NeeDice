package but.app.needice.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter


class FlagPagerAdapter(context: Context, flagIds: List<Int>) :
    PagerAdapter() {

    private val mContext: Context
    private val mFlagIds: List<Int>

    init {
        mContext = context
        mFlagIds = flagIds
    }

    override fun getCount(): Int {
        return mFlagIds.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(mContext)
        imageView.setImageResource(mFlagIds[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(32, 0, 32, 0)
        imageView.layoutParams = layoutParams

        container.addView(imageView)

        imageView.setOnClickListener { _ ->
            onClickListener?.onItemClick(position)
        }

        return imageView
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
