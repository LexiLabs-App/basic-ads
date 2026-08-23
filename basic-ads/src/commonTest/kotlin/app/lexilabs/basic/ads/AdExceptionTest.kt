package app.lexilabs.basic.ads

import kotlin.test.Test
import kotlin.test.assertEquals

class AdExceptionTest {

    @Test
    fun testAdExceptionMessage() {
        val exception = AdException("Network error")
        assertEquals("AdMob failed to obtain, load, or show an ad: Network error", exception.message)
    }

    @Test
    fun testAdExceptionNullMessage() {
        val exception = AdException(null)
        assertEquals("AdMob failed to obtain, load, or show an ad: null", exception.message)
    }
}
