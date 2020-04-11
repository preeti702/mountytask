package com.taskk.demo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taskk.demo.R
import com.taskk.demo.interfaces.Constants
import com.taskk.demo.model.TopRatedModel
import com.taskk.demo.model.UpcomingMoviesModel
import com.taskk.demo.util.Commons
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetail : AppCompatActivity(),Constants {



    lateinit var topRatedModel : TopRatedModel.Result
    lateinit var upcomingMoviesModel: UpcomingMoviesModel.Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        getData()
    }

    fun getData(){

        val  type = intent.getIntExtra("type",0)
        if (type == topMoviee){
            Commons.setToolbarWithBackAndTitle(this,"Top rated movies",true,R.drawable.ic_arrow_back_white_24dp)
            topRatedModel = intent.getSerializableExtra("detail") as TopRatedModel.Result
            setTopMovies()
        }
        else if (type == upcomingMoviee){
            Commons.setToolbarWithBackAndTitle(this,"Upcoming movies",true,R.drawable.ic_arrow_back_white_24dp)
            upcomingMoviesModel = intent.getSerializableExtra("detail") as UpcomingMoviesModel.Result

            setUpcomingData()
        }

    }

    fun setUpcomingData(){

        val imgUrl = baseImageURL + upcomingMoviesModel.posterPath

        Picasso.with(this)
            .load(imgUrl)
            .placeholder(R.drawable.ic_popcorn)
            .error(R.drawable.ic_popcorn)
            .into(imgBanner)

        tvTitle.text = upcomingMoviesModel.title
        tvLng.text = upcomingMoviesModel.originalLanguage
        tvRating.text = upcomingMoviesModel.voteAverage
        tvOverview.text = upcomingMoviesModel.overview
    }

    fun setTopMovies(){

        val imgUrl = baseImageURL + topRatedModel.posterPath

        Picasso.with(this)
            .load(imgUrl)
            .placeholder(R.drawable.ic_popcorn)
            .error(R.drawable.ic_popcorn)
            .into(imgBanner)

        tvTitle.text = topRatedModel.title
        tvLng.text = topRatedModel.originalLanguage
        tvRating.text = topRatedModel.voteCount.toString()
        tvOverview.text = topRatedModel.overview
    }
}
