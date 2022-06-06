package com.avi.memaq.service

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.dto.flashcard.extension.toEntity
import com.avi.memaq.entity.DeckEntity
import com.avi.memaq.entity.flashcard.FlashCardEntity
import com.avi.memaq.entity.flashcard.extension.toDto
import com.avi.memaq.repository.FlashCardJpaRepository
import com.avi.memaq.service.exception.DeckNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class FlashCardService(val flashCardJpaRepository: FlashCardJpaRepository) {
  fun save(newFlashCard: FlashCard, deckId: UUID) {
    // fake entity to prevent selecting the parent
    val deckEntity = DeckEntity(id = deckId)
    val flashCardEntity = newFlashCard.toEntity()
    flashCardEntity.deck = deckEntity
    flashCardJpaRepository.save(flashCardEntity)
  }

  fun getTopCard(deckId: UUID): FlashCard {
    return flashCardJpaRepository.findFirstByDeckIdOrderByScoreDesc(deckId)
      .orElseThrow { DeckNotFoundException("Deck with id = $deckId could not be found") }.toDto()
  }

  fun findFlashCardsByDeckId(deckId: UUID) = flashCardJpaRepository.findAllByDeckId(deckId)
}