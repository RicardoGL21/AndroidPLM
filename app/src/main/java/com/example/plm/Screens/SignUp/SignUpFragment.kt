package com.example.plm.Screens.SignUp

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.plm.MainActivity
import com.example.plm.Network.Request.RequestCreateUser
import com.example.plm.Network.Response.CountriesResult
import com.example.plm.Network.Response.ProfessionResult
import com.example.plm.Network.Response.ResponseGetProfessions
import com.example.plm.Network.Response.StateByCountry
import com.example.plm.R
import com.example.plm.Screens.Utils.showErrorDialog
import com.example.plm.SharedPreferencesManager
import com.example.plm.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private var viewModel = SignUpViewModel()

    var listProfessions:List<ProfessionResult> = emptyList()
    var listCountries:List<CountriesResult> = emptyList()
    var listStates:List<StateByCountry> = emptyList()

    var countrySelected:CountriesResult? = null
    var professionSelected:ProfessionResult? = null
    var stateSelected:StateByCountry? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        binding.etName.filters = arrayOf(allowOnly(true))
        binding.etSurname1.filters = arrayOf(allowOnly(true))
        binding.etSurname2.filters = arrayOf(allowOnly(true))
        binding.etCodeProfession.filters = arrayOf(allowOnly(false), InputFilter.LengthFilter(10))

        binding.btnSignUp.setOnClickListener {
            validateFields()
        }

        val adapterProfession = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, listProfessions.map { it.ProfessionName })
        adapterProfession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerProfession.adapter = adapterProfession

        binding.cvProfession.setOnClickListener {
            binding.spinnerProfession.performClick()
        }

        binding.spinnerProfession.setSelection(0,false)

        binding.spinnerProfession.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.etProfession.setText(listProfessions[position].ProfessionName)
                professionSelected = listProfessions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val adapterStates = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, listStates.map { it.StateName })
        adapterStates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerState.adapter = adapterStates

        binding.cvState.setOnClickListener {
            binding.spinnerState.performClick()
        }

        binding.spinnerState.setSelection(0,false)

        binding.spinnerState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.etState.setText(listStates[position].StateName)
                stateSelected = listStates[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val adapterCountries = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, listCountries.map { it.CountryName })
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = adapterCountries

        binding.cvCountry.setOnClickListener {
            binding.spinnerCountry.performClick()
        }

        binding.spinnerCountry.setSelection(0,false)

        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.etCountry.setText(listCountries[position].CountryName)
                countrySelected = listCountries[position]
                binding.etState.setText("")
                if ((listCountries[position].CountryId ?: 0) >= 0) {
                    viewModel.getStates(idCountry = listCountries[position].CountryId.toString()) {
                        if (it.isSuccess) {
                            listStates = it.getOrNull() ?: emptyList()
                            if (listStates.isEmpty()) {
                                binding.etState.setText(listCountries[position].CountryName)
                            }
                            adapterStates.clear()
                            adapterStates.addAll(listStates.map { it.StateName })
                            adapterStates.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.getCountries {
            if (it.isSuccess) {
                listCountries = it.getOrNull() ?: emptyList()
                adapterCountries.clear()
                adapterCountries.addAll(listCountries.map { it.CountryName })
                adapterCountries.notifyDataSetChanged()
            } else {

            }
        }

        viewModel.getProfessions {
            if (it.isSuccess) {
                listProfessions = it.getOrNull() ?: emptyList()
                adapterProfession.clear()
                adapterProfession.addAll(listProfessions.map { it.ProfessionName })
                adapterProfession.notifyDataSetChanged()
            } else {

            }
        }

        return binding.root
    }

    fun validateFields() {
        val name = binding.etName.text.toString()
        val surname1 = binding.etSurname1.text.toString()
        val surname2 = binding.etSurname2.text.toString()
        val email = binding.etEmail.text.toString()
        val profession = binding.etProfession.text.toString()
        val codeProfession = binding.etCodeProfession.text.toString()
        val country = binding.etCountry.text.toString()
        val state = binding.etState.text.toString()
        val phone = binding.etPhone.text.toString()

        if (name.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (surname1.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (surname2.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (!validateEmailAndNotEmpty(email)) {
            showErrorDialog(this.requireContext())
            return
        } else if (profession.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (codeProfession.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (country.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (state.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (phone.isEmpty()) {
            showErrorDialog(this.requireContext())
            return
        } else if (profession == "SELECCIONAR") {
            showErrorDialog(this.requireContext())
            return
        } else if (country == "SELECCIONAR") {
            showErrorDialog(this.requireContext())
            return
        } else if (state == "SELECCIONAR") {
            showErrorDialog(this.requireContext())
            return
        } else {
            createUser(email,name,surname1,phone,codeProfession,surname2)
        }

    }

    fun createUser(email: String, name:String, surname1:String, phone:String, codeProfession:String, surname2:String) {
        (activity as MainActivity).showLoading()
        val request = RequestCreateUser(codePrefix = "TESTPLM", country = countrySelected?.ID ?: "", email = email, firstName = name, lastName = surname1, phone = phone, profession = professionSelected?.ProfessionId ?: 0, professionLicense = codeProfession, slastName = surname2, source = 27, state = stateSelected?.ShortName ?: (countrySelected?.CountryName ?: ""), targetOutput = 5)
        viewModel.createAccount(request) {
            (activity as MainActivity).hideLoading()
            if (it.isSuccess) {
                val codeUser = it.getOrNull()
                if (codeUser != null) {
                    SharedPreferencesManager(requireContext()).saveUser(codeUser)
                    (activity as MainActivity).goToLoginScreen()
                } else {
                    showErrorDialog(this.requireContext())
                }
            } else {
                showErrorDialog(this.requireContext())
            }
            
        }
    }

    fun validateEmailAndNotEmpty(email:String):Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun allowOnly(type:Boolean): InputFilter {
        return object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                val regex = if(type) {
                    Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$")
                } else {
                    Regex("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+$")
                }
                return if (source?.matches(regex) == true) source else ""
            }
        }
    }


}