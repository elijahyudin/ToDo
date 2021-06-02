package com.coursework.androidtodometer.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.AboutFragmentBinding
import com.coursework.androidtodometer.util.MaterialDialog
import com.coursework.androidtodometer.util.MaterialDialog.Companion.message
import com.coursework.androidtodometer.util.MaterialDialog.Companion.positiveButton
import com.coursework.androidtodometer.util.MaterialDialog.Companion.title

class AboutFragment : Fragment() {
    private var _binding: AboutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AboutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.packageName?.let {
            val packageInfo = context?.packageManager?.getPackageInfo(it, 0)
            val versionName = packageInfo?.versionName
            binding.versionNumberTextView.text = versionName
            if (versionName?.contains(getString(R.string.beta)) == true) {
                binding.releaseChannelTextView.visibility = View.VISIBLE
                binding.releaseChannelTextView.text = getString(R.string.beta)
            }
            if (versionName?.contains(getString(R.string.alpha)) == true) {
                binding.releaseChannelTextView.visibility = View.VISIBLE
                binding.releaseChannelTextView.text = getString(R.string.alpha)
            }
        }
        binding.githubCard.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getString(R.string.github_url))
            startActivity(intent)
        }
        binding.openSourceLicensesCard.setOnClickListener {
            val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
            intent.putExtra("title", getString(R.string.open_source_licenses))
            startActivity(intent)
        }
        binding.privacyPolicyCard.setOnClickListener {
            val htmlBody = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(getString(R.string.privacy_policy_body), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(getString(R.string.privacy_policy_body))
            }
            MaterialDialog.createDialog(requireContext()) {
                title(R.string.privacy_policy)
                message(htmlBody)
                positiveButton(R.string.ok)
            }.show()
        }
    }
}
