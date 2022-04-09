package com.example.truelogicappchallenge.presentation.ui

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.filters.LargeTest
import com.example.truelogicappchallenge.di.UseCaseModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(UseCaseModule::class)
@HiltAndroidTest
@LargeTest
@RunWith(MockitoJUnitRunner::class)
class End2EndMainActivityTest {

    private lateinit var context: Context
    private lateinit var scenario: ActivityScenario<MainActivity>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


}