package com.example.meetmewhere

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetmewhere.databinding.EventItemBinding

class EventAdapter (private var eventList: List<Events>): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(private var binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root){
       fun bind(event: Events){
           binding.tvEventTitle.text = event.title
           binding.tvEventDesc.text = event.description
           binding.tvEventDateTime.text = event.date
           binding.tvEventLocation.text = event.location
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
      holder.bind(eventList[position])
    }

    override fun getItemCount(): Int  =  eventList.size


    fun updateEvents(newEvents: List<Events>) {
            eventList = newEvents
            notifyDataSetChanged()
    }


}