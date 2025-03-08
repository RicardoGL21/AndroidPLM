package com.example.plm.Network.Response

data class ResponseGetStates(val getStateByCountryResult:List<StateByCountry>?)

data class StateByCountry(val Active:Boolean?, val CountryId:Int?, val ShortName:String?, val StateId:Int?, val StateName:String?)