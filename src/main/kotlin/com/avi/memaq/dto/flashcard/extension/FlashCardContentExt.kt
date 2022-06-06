package com.avi.memaq.dto.flashcard.extension

import com.avi.memaq.dto.flashcard.FlashCardContent
import com.avi.memaq.entity.flashcard.FlashCardContentEntity

fun FlashCardContent.toEntity(): FlashCardContentEntity = FlashCardContentEntity(
  text = text,
)