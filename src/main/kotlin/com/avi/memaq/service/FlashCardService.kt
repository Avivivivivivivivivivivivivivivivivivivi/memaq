package com.avi.memaq.service

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.dto.flashcard.extension.toEntity
import com.avi.memaq.entity.flashcard.FlashCardEntity
import com.avi.memaq.entity.flashcard.extension.toDto
import com.avi.memaq.repository.FlashCardJpaRepository
import org.springframework.stereotype.Service

@Service
class FlashCardService(val flashCardJpaRepository: FlashCardJpaRepository) {
  fun save(newFlashCard: FlashCard): FlashCard {
    return flashCardJpaRepository.save(newFlashCard.toEntity()).toDto()
  }

  fun getAllCards(): List<FlashCard> {
    return flashCardJpaRepository.findAllByDeckId(1).map(FlashCardEntity::toDto)
  }

  fun getTopCard(): FlashCard {
    return flashCardJpaRepository.findFirstByDeckIdOrderByScoreDesc(1).toDto()
  }
}