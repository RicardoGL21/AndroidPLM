package com.example.plm.Screens.Logged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plm.Network.Request.RequestCreateUser
import com.example.plm.Network.Response.CountriesResult
import com.example.plm.Network.Response.DrugsResult
import com.example.plm.Network.Response.ProfessionResult
import com.example.plm.Network.Response.StateByCountry
import com.example.plm.Network.RetrofitClient
import com.example.plm.Screens.SignUp.ErrorService
import kotlinx.coroutines.launch

class SearchProductsViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    fun getProducts(codeUser:String, drug:String, onResult: (Result<List<DrugsResult>>) -> Unit) {
        viewModelScope.launch {
            try {

                val response = apiService.getProducts(codeUser, countryId = "11", editionId = "211", drug = drug)
                response.getDrugsResult?.let {
                    onResult(Result.success(it))
                } ?: onResult(Result.failure(ErrorService("Error al obtener los datos")))
            } catch (e: Exception) {
                onResult(Result.failure(ErrorService(e.message ?: "Error desconocido")))
            }
        }
    }

    fun getProductsMocky(codeUser:String, drug:String, onResult: (Result<List<DrugsResult>>) -> Unit) {
        viewModelScope.launch {
            try {

                val response = apiService.getProductsMocky()
                response.getDrugsResult?.let {
                    onResult(Result.success(it))
                } ?: onResult(Result.failure(ErrorService("Error al obtener los datos")))
            } catch (e: Exception) {
                onResult(Result.failure(ErrorService(e.message ?: "Error desconocido")))
            }
        }
    }

}