package com.avi.memaq.controller

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.service.FlashCardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/flash-card")
class FlashCardController(val flashCardService: FlashCardService) {
  @PostMapping
  fun addFlashCardToDeck(@RequestBody flashCard: FlashCard): ResponseEntity<FlashCard> {
    flashCardService.save(flashCard)
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri())
      .body(flashCard)
  }

  @GetMapping
  fun getWholeDeck() = flashCardService.getAllCards()

  @GetMapping("/top")
  fun getTopScoreCardFromDeck(): FlashCard {
    return flashCardService.getTopCard()
  }
}