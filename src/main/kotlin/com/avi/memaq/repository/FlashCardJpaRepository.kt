package com.avi.memaq.repository

import com.avi.memaq.entity.flashcard.FlashCardEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

sealed interface FlashCardJpaRepository : CrudRepository<FlashCardEntity, UUID> {
  fun findFirstByDeckIdOrderByScoreDesc(deckId: UUID): Optional<FlashCardEntity>
  fun findAllByDeckId(deckId: UUID): List<FlashCardEntity>
}