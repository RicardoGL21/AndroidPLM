package com.example.plm.Screens.Logged

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.plm.MainActivity
import com.example.plm.R
import com.example.plm.databinding.FragmentMainUserBinding
import com.google.android.material.navigation.NavigationView

class MainUserFragment : Fragment() {

    private lateinit var binding: FragmentMainUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_user, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.xmark)
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_search -> {
                    childFragmentManager.beginTransaction().replace(binding.frmContainer.id, SearchProductsFragment()).commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.item_developer -> {
                    childFragmentManager.beginTransaction().replace(binding.frmContainer.id, DeveloperFragment()).commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        childFragmentManager.beginTransaction().replace(binding.frmContainer.id, SearchProductsFragment()).commit()

        return binding.root
    }
}
