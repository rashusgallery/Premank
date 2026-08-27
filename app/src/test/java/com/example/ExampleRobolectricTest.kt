package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.CalculationMode
import com.example.model.CompatibilityTier
import com.example.model.NumerologyEngine
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
    assertEquals("PremAnk", appName)
  }

  @Test
  fun `test numerology reduction and scoring`() {
    val numReduced = NumerologyEngine.reduceNum(37)
    assertEquals(1, numReduced)

    val nameNum = NumerologyEngine.nameNumber("Rahul")
    assertTrue(nameNum in 1..9)

    val dobNum = NumerologyEngine.dobNumber("1998-05-14")
    assertTrue(dobNum in 1..9)

    val result = NumerologyEngine.calculateScore(
      mode = CalculationMode.LOVE,
      nameA = "Rahul",
      dobA = "1998-05-14",
      nameB = "Priya",
      dobB = "1999-08-22"
    )

    assertTrue(result.score in 42..99)
    assertNotNull(result.tier)
    assertTrue(result.wishText.contains("Rahul"))
    assertTrue(result.wishText.contains("Priya"))
  }
}

