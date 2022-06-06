package com.avi.memaq.entity.flashcard.extension

import com.avi.memaq.dto.flashcard.FlashCard
import com.avi.memaq.entity.flashcard.FlashCardEntity

fun FlashCardEntity.toDto(): FlashCard = FlashCard(
  question = question.toDto(),
  answer = answer.toDto(),
  score = score,
)