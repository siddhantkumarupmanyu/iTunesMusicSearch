package sku.challenge.itunesmusicsearch.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import sku.challenge.itunesmusicsearch.test_utils.launchFragmentInHiltContainer


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class GalleryFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        // Populate @Inject fields in test class
        hiltRule.inject()

        launchFragmentInHiltContainer<GalleryFragment> {
            dataBindingIdlingResourceRule.monitorFragment(this)
        }
    }

}