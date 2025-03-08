package com.example.plm.Screens.Logged

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.plm.R
import com.example.plm.databinding.FragmentDeveloperBinding

class DeveloperFragment : Fragment() {

    private lateinit var binding: FragmentDeveloperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_developer, container, false)


        return binding.root
    }

}