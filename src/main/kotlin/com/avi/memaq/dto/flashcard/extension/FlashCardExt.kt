package com.avi.memaq.dto.flashcard.extension

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.entity.flashcard.FlashCardEntity

fun FlashCard.toEntity() = FlashCardEntity(
  question = question.toEntity(),
  answer = answer.toEntity(),
  score = score,
)