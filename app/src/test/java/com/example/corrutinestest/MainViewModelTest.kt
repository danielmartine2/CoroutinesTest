package com.example.corrutinestest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Rule
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

@ExperimentalCoroutinesApi
class MainViewModelTest {
    
    @get:Rule
    // esta tarea lo que hace ejecutar los live data en el hilo del test en vez de intentarlo en el hilo principal
    //como el hilo principal no existe en los test daria una exception.
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: MainViewModel

    @Before
    fun setUp(){
        vm = MainViewModel(coroutinesTestRule.testDispatcher)
    }

    //esta forma de escribir los nombres en los test solo hacerlo en test unitarios
    @Test
    fun `success if user and pass are not empty`() {

        //creamos un observer com mockito
        val observer = mock<Observer<Boolean>>()

        //Este builder corrutinas lo que nos permite, es blokear el hilo principal hasta que las corrutinas acaben.
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //para observar el resultado de live data
            //lo que hace es que en ves de observar durante el ciclo de vida, va observar durante todo el tiempo que exista el live data
            //este live data solo vive durante el tiempo de vida del test
            vm.loginResult.observeForever(observer)

            vm.onSubmitClicked("user", "pass")

            verify(observer).onChanged(true)
        }
    }

    @Test
    fun `error if user is empty`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        //creamos un observer com mockito
        val observer = mock<Observer<Boolean>>()

        //para observar el resultado de live data
        //lo que hace es que en ves de observar durante el ciclo de vida, va observar durante todo el tiempo que exista el live data
        //este live data solo vive durante el tiempo de vida del test
        vm.loginResult.observeForever(observer)

        vm.onSubmitClicked("", "pass")

        verify(observer).onChanged(false)
    }

    @Test
    fun `error if pass is empty`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val observer = mock<Observer<Boolean>>()

        vm.loginResult.observeForever(observer)

        vm.onSubmitClicked("user", "")

        verify(observer).onChanged(false)
    }

    @Test
    fun `error if user and pass is empty`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val observer = mock<Observer<Boolean>>()

        vm.loginResult.observeForever(observer)

        vm.onSubmitClicked("", "")

        verify(observer).onChanged(false)
    }
}