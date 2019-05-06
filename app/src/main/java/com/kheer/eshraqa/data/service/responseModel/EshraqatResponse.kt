package com.kheer.eshraqa.data.service.responseModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EshraqatResponse(
        @SerializedName("data")
        var `data`: List<Eshraqa> = listOf(),
        @SerializedName("found")
        var found: Boolean = false
) : Serializable {
    data class Eshraqa(
            @SerializedName("body")
            var body: String = "",
            @SerializedName("created_at")
            var createdAt: String = "",
            @SerializedName("date")
            var date: String = "",
            @SerializedName("hijri_date")
            var hijriDate: String = "",
            @SerializedName("id")
            var id: String = "",
            @SerializedName("title")
            var title: String = "",
            @SerializedName("updated_at")
            var updatedAt: String = "",
            @SerializedName("zekr_body")
            var zekrBody: String = ""
    ) : Serializable {
        var isFavourite: Boolean = false
    }
}