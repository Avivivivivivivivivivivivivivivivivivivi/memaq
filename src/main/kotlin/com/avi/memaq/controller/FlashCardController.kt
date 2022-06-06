package com.avi.memaq.controller

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.service.FlashCardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/flash-card")
class FlashCardController(val flashCardService: FlashCardService) {
  @PostMapping("/{deckId}")
  fun addFlashCardToDeck(@RequestBody flashCard: FlashCard, @PathVariable deckId: UUID): ResponseEntity<Unit> {
    flashCardService.save(flashCard, deckId)
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath()
//      .path("/${flashCardService.save(flashCard, deckId)}")
      .build().toUri()).build()
  }
}