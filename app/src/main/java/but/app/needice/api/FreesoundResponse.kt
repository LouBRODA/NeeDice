package but.app.needice.api

import com.google.gson.annotations.SerializedName

data class FreesoundResponse(
    @SerializedName("results")
    val results: List<FreesoundResult>?
)