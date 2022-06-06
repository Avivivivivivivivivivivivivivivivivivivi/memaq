package com.avi.memaq

import com.avi.memaq.dto.Deck
import com.avi.memaq.entity.DeckEntity
import com.avi.memaq.entity.flashcard.FlashCardContentEntity
import com.avi.memaq.entity.flashcard.FlashCardEntity
import com.avi.memaq.repository.DeckJpaRepository
import com.avi.memaq.service.DeckService
import com.avi.memaq.service.exception.DeckNotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class DeckTests(
  @Autowired val mockMvc: MockMvc,
  @Autowired val deckJpaRepository: DeckJpaRepository,
  @Autowired val deckService: DeckService,
) {

  val deckName = "amazing_deck"

  @Test
  fun `when create deck endpoint is called deck is saved in the database and can be retrieved by its response path`() {
    val createDeckRequest = post("/deck")
      .content(asJsonString(Deck(name = deckName)))
      .contentType(MediaType.APPLICATION_JSON)

    val location = mockMvc.perform(createDeckRequest)
      .andExpect(status().isCreated)
      .andReturn()
      .response
      .getHeaderValue("Location")
      .toString()

    mockMvc.perform(get(location))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.name").value(deckName))
  }

  @Test
  fun `when get top card endpoint is called the card with highest score in deck is returned`() {
    val lowScore = 5.0
    val highScore = 10.0
    val highScoreQuestion = FlashCardContentEntity(text = "high_q")
    val highScoreAnswer = FlashCardContentEntity(text = "high_a")
    var deckEntity = DeckEntity(
      name = deckName
    )
    deckEntity.flashCardList?.addAll(
      listOf(
        FlashCardEntity(score = lowScore,
          question = FlashCardContentEntity(text = "low_q"),
          answer = FlashCardContentEntity(text = "low_a"),
          deck = deckEntity),
        FlashCardEntity(score = lowScore,
          question = FlashCardContentEntity(text = "low_q"),
          answer = FlashCardContentEntity(text = "low_a"),
          deck = deckEntity),
        FlashCardEntity(score = lowScore,
          question = FlashCardContentEntity(text = "low_q"),
          answer = FlashCardContentEntity(text = "low_a"),
          deck = deckEntity),
        FlashCardEntity(score = highScore, question = highScoreQuestion, answer = highScoreAnswer, deck = deckEntity),
        FlashCardEntity(score = lowScore,
          question = FlashCardContentEntity(text = "low_q"),
          answer = FlashCardContentEntity(text = "low_a"),
          deck = deckEntity),
        FlashCardEntity(score = lowScore,
          question = FlashCardContentEntity(text = "low_q"),
          answer = FlashCardContentEntity(text = "low_a"),
          deck = deckEntity),
        FlashCardEntity(score = lowScore,
          question = FlashCardContentEntity(text = "low_q"),
          answer = FlashCardContentEntity(text = "low_a"),
          deck = deckEntity),
      )
    )
    deckEntity = deckJpaRepository.save(deckEntity)

    mockMvc.perform(get("/deck/${deckEntity.id}/top"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.score").value(highScore))
      .andExpect(jsonPath("$.question.text").value(highScoreQuestion.text))
      .andExpect(jsonPath("$.answer.text").value(highScoreAnswer.text))
  }

  @Test
  fun `when deck is not found throw DeckNotFoundException`(){
    deckJpaRepository.deleteAll()
    Assertions.assertThrowsExactly(
      DeckNotFoundException::class.java
    ) {
      deckService.findById(UUID.randomUUID())
    }
  }

  private companion object {
    fun asJsonString(obj: Any): String = ObjectMapper().writeValueAsString(obj)
  }
}