package myplayground.example.jakpost

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import myplayground.example.jakpost.ui.theme.JakPostTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JakPostAppKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    fun setUp() {
        composeTestRule.setContent {
            JakPostTheme {
                JakPostApp()
            }
        }
    }

    @Test
    fun press_bottom_navigation_setting_change_page_correctly() {
        composeTestRule.onNodeWithText("Setting").performClick()

        composeTestRule.onAllNodesWithTag("setting_page").onFirst().assertExists()
    }

    @Test
    fun press_bottom_navigation_favourite_change_page_correctly() {
        composeTestRule.onNodeWithText("Favourite").performClick()

        composeTestRule.onAllNodesWithTag("favourite_skeleton_view").onFirst().assertExists()

        // wait for 3s and check for real data shown (because fake repository always delay data for 1.5s)
        Thread.sleep(3000)

        // no data
        composeTestRule.onAllNodesWithTag("no_data").onFirst().assertExists()
    }

    @Test
    fun press_navigation_drawer_about_change_page_and_return_to_home_correctly() {
        composeTestRule.onAllNodesWithTag("hamburger_icon").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("navigation_item_about").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("about_page").onFirst().assertExists()


        composeTestRule.onAllNodesWithTag("hamburger_icon").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("navigation_item_home").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("home_page").onFirst().assertExists()
    }

    @Test
    fun wait_for_home_content_loaded_and_change_categories() {
        composeTestRule.onAllNodesWithTag("home_page").onFirst().assertExists()

        composeTestRule.onAllNodesWithTag("home_skeleton_view").onFirst().assertExists()

        // sleep, wait for data to load
        Thread.sleep(3000)

        composeTestRule.onAllNodesWithTag("home_news_block").onFirst().assertExists()

        composeTestRule.onAllNodesWithTag("tab_bar_SOCIETY").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("home_skeleton_view").onFirst().assertExists()

        // sleep, wait for data to load
        Thread.sleep(3000)

        composeTestRule.onAllNodesWithTag("home_news_block").onFirst().assertExists()
    }

    @Test
    fun wait_for_home_content_click_detail_and_wait_for_detail_content_loaded_and_press_back() {
        composeTestRule.onAllNodesWithTag("home_page").onFirst().assertExists()

        composeTestRule.onAllNodesWithTag("home_skeleton_view").onFirst().assertExists()

        // sleep, wait for data to load
        Thread.sleep(3000)

        composeTestRule.onAllNodesWithTag("home_news_block").onFirst().assertExists()

        composeTestRule.onAllNodesWithTag("home_news_block").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("news_detail_block_skeleton_view").onFirst()
            .performClick()

        // sleep, wait for data to load
        Thread.sleep(3000)

        composeTestRule.onAllNodesWithTag("news_detail_block").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("on_back_click").onFirst().performClick()

        composeTestRule.onAllNodesWithTag("home_page").onFirst().assertExists()
    }
}