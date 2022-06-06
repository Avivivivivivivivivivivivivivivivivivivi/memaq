package com.avi.memaq.service

import com.avi.memaq.dto.Deck
import com.avi.memaq.dto.extension.toEntity
import com.avi.memaq.entity.extension.toDto
import com.avi.memaq.repository.DeckJpaRepository
import com.avi.memaq.repository.FlashCardJpaRepository
import com.avi.memaq.service.exception.DeckNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeckService(val deckJpaRepository: DeckJpaRepository, val flashCardJpaRepository: FlashCardJpaRepository) {
  fun findById(deckId: UUID): Deck = deckJpaRepository.findById(deckId)
    .orElseThrow { DeckNotFoundException("Deck with id = $deckId could not be found") }
    .toDto()

  fun save(deck: Deck) = deckJpaRepository.save(deck.toEntity()).id
}
