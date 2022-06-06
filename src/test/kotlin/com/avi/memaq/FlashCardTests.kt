package com.avi.memaq

import com.avi.memaq.dto.Deck
import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.dto.flashcard.FlashCardContent
import com.avi.memaq.service.DeckService
import com.avi.memaq.service.FlashCardService
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class FlashCardTests(
  @Autowired val mockMvc: MockMvc,
  @Autowired val flashCardService: FlashCardService,
  @Autowired val deckService: DeckService,
) {

  @Test
  fun `when add flash card endpoint with specified deck id is called the flashcard is added to deck`() {
    val deckEntity = Deck(name = "deck_name")
    val deckId = deckService.save(deckEntity)!!

    val questionText = "high_q"
    val answerText = "high_a"
    val addFlashCardRequest = post("/flash-card/$deckId")
      .content(asJsonString(FlashCard(
        question = FlashCardContent(text = questionText),
        answer = FlashCardContent(text = answerText),
      )))
      .contentType(MediaType.APPLICATION_JSON)

    mockMvc
      .perform(addFlashCardRequest)
      .andExpect(status().isCreated)

    val flashCardList = flashCardService.findFlashCardsByDeckId(deckId)

    Assertions.assertThat(flashCardList).hasSize(1)
    Assertions.assertThat(flashCardList[0].answer.text).isEqualTo(answerText)
    Assertions.assertThat(flashCardList[0].question.text).isEqualTo(questionText)
  }

  private companion object {
    fun asJsonString(obj: Any): String = ObjectMapper().writeValueAsString(obj)
  }
}