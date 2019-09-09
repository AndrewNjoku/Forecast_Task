package com.example.ForecastApp

import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.model.ApplicationModel
import com.example.ForecastApp.mvp.BaseContract
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.Mock


class SearchResultsTest {


    //we will use mockWebServer to mock the HTTP calls and responses
    lateinit var server: MockWebServer

    @Mock
    lateinit var myDatabase: ForecastDatabase

    @Mock
    lateinit var myView: BaseContract.View



    lateinit var myModelInteractor : ApplicationModel



    @Before
    fun setup(){

        //init mocks for the model interactor which will be the server and database
        server = MockWebServer()
        //we will set responses of the mock server in our test cases


        //for the database we will create an in memory database which will only persist as long as we need it to in memory for the t




    }



    //1
    @Test
    fun showResultsIsCalledWithData() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun showNoResultsIsCalledWithNoData() {


    }
}