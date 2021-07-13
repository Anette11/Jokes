package com.example.jokes.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.jokes.R
import com.example.jokes.databinding.FragmentWebViewBinding
import com.example.jokes.ui.JokeFactory
import com.example.jokes.ui.JokeViewModel
import com.example.jokes.util.Constants.URL_API_DOCUMENTATION

class FragmentWebView : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var jokeViewModel: JokeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebViewBinding
            .inflate(inflater, container, false)
        val view = binding.root
        setViewModel()
        changeActionBarName()
        changeProgressBarVisibility()
        letWebViewGoBackOnBackPressedButton()
        setWebView()
        restoreWebViewState(savedInstanceState)
        return view
    }

    private fun restoreWebViewState(savedInstanceState: Bundle?) {
        when {
            savedInstanceState != null -> {
                _binding?.webView?.restoreState(savedInstanceState)
            }
            else -> {
                webViewLoadUrl()
            }
        }
    }

    private fun webViewLoadUrl() {
        _binding?.webView?.loadUrl(URL_API_DOCUMENTATION)
    }

    private fun letWebViewGoBackOnBackPressedButton() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (_binding?.webView?.canGoBack() == true) {
                        _binding?.webView?.goBack()
                    } else if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

    private fun setViewModel() {
        jokeViewModel = ViewModelProvider(requireActivity(), JokeFactory())
            .get(JokeViewModel::class.java)
    }

    private fun changeActionBarName() {
        jokeViewModel.actionBarName.postValue(getString(R.string.api_info))
    }

    private fun setWebView() {
        _binding?.webView?.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    jokeViewModel.progressBarWebViewVisibility.postValue(1)
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    jokeViewModel.progressBarWebViewVisibility.postValue(0)
                }
            }
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.builtInZoomControls = true
            settings.setSupportZoom(true)
        }
    }

    private fun changeProgressBarVisibility() {
        jokeViewModel.progressBarWebViewVisibility.observe(viewLifecycleOwner, {
            if (it != null) when (it) {
                1 -> {
                    _binding?.progressBar?.visibility = View.VISIBLE
                }
                else -> {
                    _binding?.progressBar?.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _binding?.webView?.saveState(outState)
    }
}