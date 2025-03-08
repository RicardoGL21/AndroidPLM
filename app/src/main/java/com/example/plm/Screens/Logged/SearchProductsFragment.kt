package com.example.plm.Screens.Logged

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plm.MainActivity
import com.example.plm.Network.Response.DrugsResult
import com.example.plm.R
import com.example.plm.Screens.Utils.showErrorDialog
import com.example.plm.SharedPreferencesManager
import com.example.plm.databinding.FragmentSearchProductsBinding

class SearchProductsFragment : Fragment() {

    private lateinit var binding: FragmentSearchProductsBinding

    var viewModel = SearchProductsViewModel()

    lateinit var adapter:DrugsResultAdapter
    var drugsList: List<DrugsResult> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_products, container, false)

        binding.btnSearch.setOnClickListener {
            searchProduct()
        }

        binding.rvDrugs.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = DrugsResultAdapter(drugsList)
        binding.rvDrugs.adapter = adapter

        return binding.root
    }

    fun searchProduct() {
        val product = binding.etProduct.text.toString()

        if (product.isEmpty()) {
            showErrorDialog(this.requireContext(), "Hubo algún error al buscar el producto\npor favor intenta de nuevo")
            return
        } else {

            val userCode = SharedPreferencesManager(this.requireContext()).getUser()

            if (userCode != null) {
                (activity as MainActivity).showLoading()
                viewModel.getProducts(userCode, product) {
                    if (it.isSuccess) {
                        drugsList = it.getOrNull() ?: emptyList()
                        adapter.updateData(drugsList)
                    } else {
                        showErrorDialog(this.requireContext(), "Hubo algún error al buscar el producto\npor favor intenta de nuevo")
                    }
                    (activity as MainActivity).hideLoading()
                }
                /*viewModel.getProductsMocky(userCode, product) {
                    if (it.isSuccess) {
                        drugsList = it.getOrNull() ?: emptyList()
                        adapter.updateData(drugsList)
                    }
                    (activity as MainActivity).hideLoading()
                }*/
            }
        }

    }

}

class DrugsResultAdapter(
    private var drugsList: List<DrugsResult>
) : RecyclerView.Adapter<DrugsResultAdapter.DrugsViewHolder>() {

    class DrugsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBrand: TextView = view.findViewById(R.id.tvBrand)
        val tvCategory: TextView = view.findViewById(R.id.tvPharmaform)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drug, parent, false)
        return DrugsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrugsViewHolder, position: Int) {
        val drug = drugsList[position]
        holder.tvBrand.text = drug.Brand ?: ""
        holder.tvCategory.text = drug.PharmaForm ?: ""
    }

    override fun getItemCount(): Int = drugsList.size

    fun updateData(newList: List<DrugsResult>) {
        drugsList = newList
        notifyDataSetChanged()
    }
}