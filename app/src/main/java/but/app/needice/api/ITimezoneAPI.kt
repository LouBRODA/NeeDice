package but.app.needice.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITimezoneAPI {
        @GET("api/Time/current/zone")
        fun getTimezone(@Query("timeZone") zone: String?): Call<TimezoneResponse?>?

}