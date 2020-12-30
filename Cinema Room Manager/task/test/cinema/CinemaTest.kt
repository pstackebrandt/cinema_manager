package cinema

import org.junit.Assert
import org.junit.Test

@Suppress("unused")
class CinemaTest {
    class GetPercentageOfBookedSeatsMessageTest{
        @Test
        fun `should show value with decimals`() {
            val actual = Cinema.getPercentageOfBookedSeatsMessage(Room(2,2))
            Assert.assertEquals("Percentage: 0.00%", actual)
        }
    }
}