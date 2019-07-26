package com.dwirandyh.wallpaperapp.view.about

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.databinding.FragmentAboutBinding
import com.dwirandyh.wallpaperapp.utils.app.AppUtils

class AboutFragment : Fragment() {


    private lateinit var viewModel: AboutViewModel
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)

        getVersion()
    }

    private fun getVersion() {
        context?.let {
            binding.version = AppUtils.getVersionName(it.applicationContext)
        }
    }

}
