package app.lexilabs.basic.ads

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AdUnitIdTest {

    @Test
    fun testDefaults() {
        assertNotNull(AdUnitId.BANNER_DEFAULT)
        assertNotNull(AdUnitId.INTERSTITIAL_DEFAULT)
        assertNotNull(AdUnitId.REWARDED_DEFAULT)
        assertNotNull(AdUnitId.REWARDED_INTERSTITIAL_DEFAULT)
        assertNotNull(AdUnitId.NATIVE_DEFAULT)
    }

    @Test
    fun testAutoSelect() {
        val selected = AdUnitId.autoSelect(androidAdUnitId = "test_id", iosAdUnitId = "test_id")
        assertEquals("test_id", selected)
    }
}
