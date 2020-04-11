package com.taskk.demo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taskk.demo.R
import com.taskk.demo.adapter.TopRatedMoviesAdapter
import com.taskk.demo.adapter.UpcomingMoviesAdapter
import com.taskk.demo.interfaces.Constants
import com.taskk.demo.interfaces.OnItemCLick
import com.taskk.demo.model.TopRatedModel
import com.taskk.demo.model.UpcomingMoviesModel
import com.taskk.demo.util.Commons
import com.taskk.demo.viewModel.TopRatedViewModel
import com.taskk.demo.viewModel.UpcomingMoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.util.*

class MainActivity : AppCompatActivity(), OnItemCLick,Constants {


    private var adapter: TopRatedMoviesAdapter? = null
    private val topRated = ArrayList<TopRatedModel.Result>()
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var topRatedViewModel: TopRatedViewModel
    private lateinit var upcomingViewModel: UpcomingMoviesViewModel

    private var upcomingAdapter: UpcomingMoviesAdapter ? = null
    private val upcomingMovies = ArrayList<UpcomingMoviesModel.Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEmptyAdapter()
        getTopMovies()
        getUpcomingMovies()

        Commons.setToolbarWithBackAndTitle(
            this,
            "Movies",
            false,
            0
        )

    }

    fun setEmptyAdapter() {
        layoutManager = LinearLayoutManager(this)
        layoutManager!!.orientation = RecyclerView.HORIZONTAL
        rvTopMovies.layoutManager = layoutManager

        adapter = TopRatedMoviesAdapter(this, topRated, this)
        rvTopMovies.adapter = adapter

        val upcomingLayoutManager  = LinearLayoutManager(this)
        upcomingLayoutManager.orientation = RecyclerView.HORIZONTAL
        rvUpcomingMovies.layoutManager = upcomingLayoutManager

        upcomingAdapter = UpcomingMoviesAdapter(this,upcomingMovies,this)
        rvUpcomingMovies.adapter = upcomingAdapter
    }

    fun getTopMovies() {

        topRatedViewModel = ViewModelProvider(this).get(TopRatedViewModel::class.java)

        topRatedViewModel.getUpcomingMovies().observe(this,
            Observer<List<TopRatedModel.Result>> { results ->
                topRated.addAll(results)
                adapter?.notifyDataSetChanged()
            })
    }

    fun getUpcomingMovies(){

        upcomingViewModel = ViewModelProvider(this).get(UpcomingMoviesViewModel :: class.java)

        upcomingViewModel.getUpcomingMovies().observe(this,
            Observer<List<UpcomingMoviesModel.Result>>{results ->
                upcomingMovies.addAll(results)
                upcomingAdapter?.notifyDataSetChanged()
            })

    }

    override fun onItemClick(position: Int, type: Int) {

        val intent = Intent(this, MovieDetail::class.java)

        if (type == upcomingMoviee){
            intent.putExtra("detail",upcomingMovies[position] as Serializable)
        }
        else if (type == topMoviee){
            intent.putExtra("detail",topRated[position] as Serializable)
        }

        intent.putExtra("type",type )
        startActivity(intent)
    }
}
