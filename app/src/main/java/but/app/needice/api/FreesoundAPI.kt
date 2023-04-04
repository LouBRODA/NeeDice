package but.app.needice.api

import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Call

interface FreesoundAPI {

    @GET
    fun getSounds(@Url url: String): Call<FreesoundResponse>

}