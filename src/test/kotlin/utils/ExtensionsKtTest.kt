package utils

import org.junit.Assert.*
import org.junit.Test

class ExtensionsKtTest {

    @Test
    fun shouldReturnElementByIndexFromSet() {
        val set = mutableSetOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)

        assertEquals(1.0, set.get(0))
        assertEquals(3.0, set.get(2))
        assertEquals(5.0, set.get(4))
        assertEquals(7.0, set.get(6))
        assertNull(set.get(7))
    }
}