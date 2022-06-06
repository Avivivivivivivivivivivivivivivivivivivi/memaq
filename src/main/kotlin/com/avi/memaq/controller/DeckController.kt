package com.avi.memaq.controller

import com.avi.memaq.dto.Deck
import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.service.DeckService
import com.avi.memaq.service.FlashCardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/deck")
class DeckController(val deckService: DeckService, val flashCardService: FlashCardService) {

  @GetMapping("/{deckId}")
  fun getWholeDeck(@PathVariable deckId: UUID) = deckService.findById(deckId)

  @GetMapping("/{deckId}/top")
  fun getTopScoreCardFromDeck(@PathVariable deckId: UUID): FlashCard {
    return flashCardService.getTopCard(deckId)
  }

  @PostMapping
  fun createDeck(@RequestBody deck: Deck): ResponseEntity<Unit> {
    return ResponseEntity
      .created(
        ServletUriComponentsBuilder.fromCurrentRequest().path("/${deckService.save(deck)}")
          .build()
          .toUri()
      )
      .build()
  }
}