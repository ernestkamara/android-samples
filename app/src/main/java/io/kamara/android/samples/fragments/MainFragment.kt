package io.kamara.android.samples.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kamara.android.samples.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
          btnOnboarding.setOnClickListener {
              //findNavController().navigate(R.id.onboardingFragment)
              OnboardingFragment().show(childFragmentManager, "foo")
          }
    }
}
