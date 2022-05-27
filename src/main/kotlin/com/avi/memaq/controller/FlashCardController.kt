package com.avi.memaq.controller

import com.avi.memaq.dto.flashcard.FlashCard
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/flash-card")
class FlashCardController {

  val deck = mutableListOf<FlashCard>()

  @PostMapping
  fun addFlashCardToDeck(@RequestBody flashCard: FlashCard): ResponseEntity<FlashCard> {
    deck.add(flashCard)
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri())
      .body(flashCard)
  }

  @GetMapping
  fun getWholeDeck() = deck

  @GetMapping("/top")
  fun getTopScoreCardFromDeck(): FlashCard {
    deck.sortWith(compareByDescending { it.score })
    return deck[0]
  }

}