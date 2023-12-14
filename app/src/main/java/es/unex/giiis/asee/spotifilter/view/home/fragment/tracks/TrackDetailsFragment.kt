package es.unex.giiis.asee.spotifilter.view.home.fragment.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.databinding.FragmentTrackDetailsBinding

class TrackDetailsFragment : Fragment() {

    private var _binding: FragmentTrackDetailsBinding? = null
    private val args: TrackDetailsFragmentArgs by navArgs()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val track = args.track
        context?.let {
            Glide.with(it).load(track.image)
                .placeholder(R.drawable.placeholder)
                .into(binding.trackDetailsImageView1)
        }
        binding.trackDetailsTextView1.text = track.name
        binding.trackDetailsTextView2.text = track.artists
        binding.trackDetailsTextView3.text = track.album
        binding.trackDetailsTextView4.text = "${track.minutes} min. ${track.seconds} sec."
        binding.trackDetailsTextView5.text = track.releaseDate
        binding.trackDetailsTextView6.text = "${track.popularity}%"
    }

}