package but.app.needice.api

import com.google.gson.annotations.SerializedName

data class FreesoundPreviews(
    @SerializedName("preview-hq-mp3")
    val preview_hq_mp3: String?
)