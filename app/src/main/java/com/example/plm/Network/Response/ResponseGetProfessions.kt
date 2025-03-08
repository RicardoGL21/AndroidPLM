package com.example.plm.Network.Response

data class ResponseGetProfessions(val getProfessionsResult:List<ProfessionResult>?)
data class ProfessionResult(val Active:Boolean?, val EnglishName:String?, val ParentId:String?, val ProfessionId:Int?, val ProfessionName:String?, val ReqProfessionalLicense:Boolean?)