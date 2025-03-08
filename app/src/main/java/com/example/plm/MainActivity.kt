package com.example.plm

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.plm.Screens.Logged.MainUserFragment
import com.example.plm.Screens.SignUp.SignUpFragment
import com.example.plm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    var progressDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val userCode = SharedPreferencesManager(this).getUser()
        if (userCode.isNullOrEmpty()) {
            supportFragmentManager.beginTransaction().replace(binding.frmContainer.id, SignUpFragment()).commit()
        } else {
            goToLoginScreen()
        }

    }

    fun showLoading() {
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.apply {
                setContentView(R.layout.dialog_progess)
                setCancelable(false)
                window?.setBackgroundDrawableResource(android.R.color.transparent)
                window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                show()
            }
        }
    }

    fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    fun goToLoginScreen() {
        supportFragmentManager.beginTransaction().replace(binding.frmContainer.id, MainUserFragment()).commit()
    }

}