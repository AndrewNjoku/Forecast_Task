package com.example.ForecastApp.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.ForecastApp.DI.Dagger_Composer.ComposerComponent
import com.example.ForecastApp.DI.Dagger_Composer.ComposerModule
import com.example.ForecastApp.DI.Dagger_Composer.DaggerComposerComponent
import com.example.ForecastApp.Fragments.MainScreenFragment
import com.example.ForecastApp.Fragments.OnLocationSelectedListener
import com.example.ForecastApp.Fragments.SearchResultsFragment
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.ForecastApp.R
import com.example.ForecastApp.adapter.SearchResultsAdapter
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.mvp.MainScreenFragment.MainActivityContract
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), OnLocationSelectedListener, MainActivityContract.View {
    override fun showDetailFragment(dayPos: Int) {

    }

    lateinit var activityComponent: ComposerComponent


    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    override fun onLocationSelected(location: String) {
        Log.e("Activity", "inside location selected listener")
       showSearchResultsFragment(location)
    }

    override fun onLocationSelected(locId: Long) {
       // presenter.attachSearchResultsFrag(locId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setContentView(R.layout.activity_home)
        //TODO use dagger 2 to pass view to presenter

        presenter.initiateNetworkFragment()
        showMainPageFragment()

    }
    override fun onBackPressed() {
      showMainPageFragment()
    }

    override fun injectDependencies() {
        activityComponent= DaggerComposerComponent.builder()
                .composerModule(ComposerModule(this))
                .build()
              activityComponent.inject(this)
    }


    override fun showError(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMainPageFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame, MainScreenFragment() ,"main")
                .commit()
    }

    override fun showSearchResultsFragment(location: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame, SearchResultsFragment().newInstance(location) ,"results")
                .commit()

    }


   override fun showDetailsFragment(location: String){
       supportFragmentManager.beginTransaction()
               .replace(R.id.frame, WeatherDetailFragment().newInstance(location) ,"detail")
               .commit()


    }

}


