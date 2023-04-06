package but.app.needice.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ITimezoneAPI {
        @GET("api/timezone/{timezone}")
        fun getTimezone(@Path("timezone") timezone: String): Call<TimezoneResponse>
}