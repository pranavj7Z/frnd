package com.pranavjayaraj.frnd.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pranavjayaraj.frnd.data.Audio


class AudioAdapter : ListAdapter<Audio, AudioAdapter.AudioHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Audio>() {
            override fun areContentsTheSame(oldItem: Audio, newItem: Audio): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun areItemsTheSame(oldItem: Audio, newItem: Audio): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.pranavjayaraj.frnd.R.layout.audio_item, parent, false)
        return AudioHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        // This is the link of the audio
        val audioLink: Audio = getItem(position)

        // IMPORTANT: setting up audio player here and pass audiolink to the audio player


    }

    fun getAudioAt(position: Int): Audio? {
        return getItem(position)
    }

    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(audio: Audio)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
