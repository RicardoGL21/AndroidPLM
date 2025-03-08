package com.example.plm.Screens.Utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.plm.databinding.DialogErrorBinding
import org.w3c.dom.Text

fun showErrorDialog(context: Context, text: String = "Hubo algún error al registrarte\npor favor intenta de nuevo") {
    val dialog = Dialog(context)

    val binding = DialogErrorBinding.inflate(LayoutInflater.from(context))
    dialog.setContentView(binding.root)

    binding.tvErrorMessage.setText(text)

    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    binding.btnClose.setOnClickListener { dialog.dismiss() }

    dialog.show()
}