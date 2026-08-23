package app.lexilabs.basic.ads

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AdStateTest {

    @Test
    fun testAdStateValues() {
        val states = AdState.entries.toTypedArray()
        assertNotNull(states)
        assertEquals(7, states.size)
        assertEquals(AdState.NONE, AdState.valueOf("NONE"))
        assertEquals(AdState.LOADING, AdState.valueOf("LOADING"))
        assertEquals(AdState.READY, AdState.valueOf("READY"))
        assertEquals(AdState.SHOWING, AdState.valueOf("SHOWING"))
        assertEquals(AdState.SHOWN, AdState.valueOf("SHOWN"))
        assertEquals(AdState.DISMISSED, AdState.valueOf("DISMISSED"))
        assertEquals(AdState.FAILING, AdState.valueOf("FAILING"))
    }
}
