package com.taskk.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.taskk.demo.R
import com.taskk.demo.interfaces.Constants
import com.taskk.demo.interfaces.OnItemCLick
import com.taskk.demo.model.TopRatedModel
import com.squareup.picasso.Picasso
import java.lang.Exception

class TopRatedMoviesAdapter(
    val onItemCLick: OnItemCLick,
    private val topRatedMoovies: ArrayList<TopRatedModel.Result>,
    private val contex: Context
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TopRatedMoviesAdapter.MyViewHolder>(),
    Constants {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return topRatedMoovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        try {
            holder.loader.visibility = View.VISIBLE
            val imgUrl = baseImageURL + topRatedMoovies.get(position).posterPath
            Picasso.with(contex)
                .load(imgUrl)
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(holder.imgBanner,object : com.squareup.picasso.Callback{
                    override fun onSuccess() {
                        holder.loader.visibility = View.GONE
                    }

                    override fun onError() {
                        holder.loader.visibility = View.GONE
                    }
                })

            holder.title.text = topRatedMoovies.get(position).title
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            onItemCLick.onItemClick(position,topMoviee)

        })
    }


    inner class MyViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        val imgBanner = view.findViewById<ImageView>(R.id.imgBanner)
        val loader = view.findViewById<RelativeLayout>(R.id.loader)
        val  title = view.findViewById<TextView>(R.id.tvTitle)
    }
}