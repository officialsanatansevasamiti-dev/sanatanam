package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DevotionalRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SHRI SANATANAM", appName)
  }

  @Test
  fun `verify devotional repository contents`() {
    val repo = DevotionalRepository()
    val panchang = repo.getTodayPanchang()
    assertNotNull(panchang)
    assertTrue(panchang.vikramSamvat.contains("२०८३"))

    val items = repo.getAllDevotionalItems()
    assertTrue(items.any { it.id == "hanuman_chalisa" })
    assertTrue(items.any { it.id == "ganesh_aarti" })

    val samiti = repo.getSanatanSevaSamitiInfo()
    assertEquals("sanatansevasamiti.shrisanatanam.com", samiti.displayWebsite)
    assertEquals("DG Developers", samiti.developerName)
    assertEquals("Deep Group", samiti.companyName)
  }

  @Test
  fun `verify app initial flow stages`() {
    assertEquals(com.example.viewmodel.AppStage.SPLASH.name, "SPLASH")
    assertEquals(com.example.viewmodel.AppStage.WELCOME.name, "WELCOME")
    assertEquals(com.example.viewmodel.AppStage.MAIN_APP.name, "MAIN_APP")
  }
}
