package but.app.needice.api

import com.google.gson.annotations.SerializedName

data class FreesoundResult(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("previews")
    val previews: FreesoundPreviews?
)