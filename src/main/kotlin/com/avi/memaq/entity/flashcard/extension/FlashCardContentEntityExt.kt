package com.avi.memaq.entity.flashcard.extension

import com.avi.memaq.dto.flashcard.FlashCardContent
import com.avi.memaq.entity.flashcard.FlashCardContentEntity

fun FlashCardContentEntity.toDto(): FlashCardContent = FlashCardContent(
  text = text,
)