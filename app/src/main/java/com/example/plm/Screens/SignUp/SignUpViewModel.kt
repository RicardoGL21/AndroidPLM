package com.example.plm.Screens.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plm.Network.Request.RequestCreateUser
import com.example.plm.Network.Response.CountriesResult
import com.example.plm.Network.Response.ProfessionResult
import com.example.plm.Network.Response.StateByCountry
import com.example.plm.Network.RetrofitClient
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    fun getProfessions(onResult: (Result<List<ProfessionResult>>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getProfessions()
                response.getProfessionsResult?.let {
                    onResult(Result.success(it))
                } ?: onResult(Result.failure(ErrorService("Error al obtener los datos")))
            } catch (e: Exception) {
                onResult(Result.failure(ErrorService(e.message ?: "Error desconocido")))
            }
        }
    }

    fun getCountries(onResult: (Result<List<CountriesResult>>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getCountries()
                response.getCountriesResult?.let {
                    onResult(Result.success(it))
                } ?: onResult(Result.failure(ErrorService("Error al obtener los datos")))
            } catch (e: Exception) {
                onResult(Result.failure(ErrorService(e.message ?: "Error desconocido")))
            }
        }
    }

    fun getStates(idCountry: String, onResult: (Result<List<StateByCountry>>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getStates(idCountry)
                response.getStateByCountryResult?.let {
                    onResult(Result.success(it))
                } ?: onResult(Result.failure(ErrorService("Error al obtener los datos")))
            } catch (e: Exception) {
                onResult(Result.failure(ErrorService(e.message ?: "Error desconocido")))
            }
        }
    }

    fun createAccount(request: RequestCreateUser, onResult: (Result<String>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.createAccount(request)
                response.saveMobileLocationAppClientResult?.let {
                    onResult(Result.success(it))
                } ?: onResult(Result.failure(ErrorService("Error al obtener los datos")))
            } catch (e: Exception) {
                onResult(Result.failure(ErrorService(e.message ?: "Error desconocido")))
            }
        }
    }
}

data class ErrorService(override val message: String) : Exception(message)