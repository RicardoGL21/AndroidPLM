package com.example.plm.Network.Response


data class ResponseProducts(
    val getDrugsResult: List<DrugsResult>?
)

data class DrugsResult(
    val BaseUrl: String?,
    val Brand: String?,
    val CategoryName: String?,
    val CategotyId: Int?,
    val CountryCodes: String?,
    val DivisionId: Int?,
    val DivisionName: String?,
    val DivisionShortName: String?,
    val EnglishPharmaForm: String?,
    val PharmaForm: String?,
    val PharmaFormId: Int?,
    val ProductId: Int?,
    val ProductShot: String?,
    val ProductTypeId: Int?,
    val ProductTypeName: String?
)
