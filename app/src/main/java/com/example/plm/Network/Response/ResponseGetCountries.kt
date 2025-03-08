package com.example.plm.Network.Response

data class ResponseGetCountries(val getCountriesResult:List<CountriesResult>?)

data class CountriesResult(val Active:Boolean?, val CountryCode:String?, val CountryId:Int?, val CountryLada:String?, val CountryName:String?, val ID:String?)
