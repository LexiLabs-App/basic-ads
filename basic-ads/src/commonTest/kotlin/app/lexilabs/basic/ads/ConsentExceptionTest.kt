package app.lexilabs.basic.ads

import kotlin.test.Test
import kotlin.test.assertEquals

class ConsentExceptionTest {

    @Test
    @OptIn(DependsOnGoogleUserMessagingPlatform::class)
    fun testConsentExceptionMessage() {
        val exception = ConsentException("Configuration error")
        assertEquals("UserMessagingPlatform failed to obtain, load, or show ConsentInformation: Configuration error", exception.message)
    }
}
