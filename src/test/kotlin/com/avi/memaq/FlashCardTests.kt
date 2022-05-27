package com.avi.memaq

import com.avi.memaq.controller.FlashCardController
import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.dto.flashcard.FlashCardView
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class FlashCardTests(
  @Autowired val mockMvc: MockMvc,
  @Autowired val flashCardController: FlashCardController
) {

  @BeforeEach
  fun beforeEach(){
    flashCardController.deck.clear()
  }

  @Test
  fun `when no cards are in deck when add card endpoint is called twice the deck contains two cards`() {
    val addFlashCardRequest = post("/flash-card")
      .content(asJsonString(FlashCard()))
      .contentType(MediaType.APPLICATION_JSON)

    mockMvc
      .perform(addFlashCardRequest)
      .andExpect(status().isCreated)

    mockMvc
      .perform(addFlashCardRequest)
      .andExpect(status().isCreated)

    mockMvc
      .perform(get("/flash-card"))
      .andExpect(status().is2xxSuccessful)
      .andExpect(jsonPath<Collection<Any>>("$", hasSize(2)))
  }

  @Test
  fun `test requests`() {
    val frontSideText = "FrontSide1"
    val backSideText = "BackSide1"
    val frontSideText2 = "FrontSide2"
    val backSideText2 = "BackSide2"
    val highestScore = 7.0
    val flashCardToSave = FlashCard(
      frontSide = FlashCardView(frontSideText),
      backSide = FlashCardView(backSideText),
      score = 5.0,
    )
    val flashCardToSave2 = FlashCard(
      frontSide = FlashCardView(frontSideText2),
      backSide = FlashCardView(backSideText2),
      score = highestScore,
    )
    val addFlashCardRequest = post("/flash-card")
      .content(asJsonString(flashCardToSave))
      .contentType(MediaType.APPLICATION_JSON)
    val addFlashCardRequest2 = post("/flash-card")
      .content(asJsonString(flashCardToSave2))
      .contentType(MediaType.APPLICATION_JSON)

    mockMvc
      .perform(addFlashCardRequest)
      .andExpect(status().isCreated)

    mockMvc
      .perform(addFlashCardRequest2)
      .andExpect(status().isCreated)

    mockMvc
      .perform(get("/flash-card/top"))
      .andExpect(status().is2xxSuccessful)
      .andExpect(jsonPath<Any>("$.frontSide.text", `is`(frontSideText2)))
      .andExpect(jsonPath<Any>("$.backSide.text", `is`(backSideText2)))
      .andExpect(jsonPath<Any>("$.score", `is`(highestScore)))
  }

  private companion object {
    fun asJsonString(obj: Any): String = ObjectMapper().writeValueAsString(obj)
  }

}
