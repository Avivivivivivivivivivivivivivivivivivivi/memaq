package com.avi.memaq

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.dto.flashcard.FlashCardContent
import com.avi.memaq.repository.FlashCardJpaRepository
import com.fasterxml.jackson.databind.ObjectMapper
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
  @Autowired val flashCardJpaRepository: FlashCardJpaRepository,
) {

  @BeforeEach
  fun beforeEach() {
    flashCardJpaRepository.deleteAll()
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
      .andExpect(jsonPath("$.length()").value(2))
  }

  @Test
  fun `when two cards are in deck when top card is selected the card with highest score is returned`() {
    val frontSideText = "FrontSide1"
    val backSideText = "BackSide1"
    val frontSideText2 = "FrontSide2"
    val backSideText2 = "BackSide2"
    val highestScore = 7.0
    val flashCardToSave = FlashCard(
      question = FlashCardContent(frontSideText),
      answer = FlashCardContent(backSideText),
      score = 5.0,
    )
    val flashCardToSave2 = FlashCard(
      question = FlashCardContent(frontSideText2),
      answer = FlashCardContent(backSideText2),
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
      .andExpect(jsonPath("$.question.text").value(frontSideText2))
      .andExpect(jsonPath("$.answer.text").value(backSideText2))
      .andExpect(jsonPath("$.score").value(highestScore))
  }

  private companion object {
    fun asJsonString(obj: Any): String = ObjectMapper().writeValueAsString(obj)
  }
}
