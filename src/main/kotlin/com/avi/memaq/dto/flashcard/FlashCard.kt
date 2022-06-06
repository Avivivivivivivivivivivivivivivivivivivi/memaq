package com.avi.memaq.dto.flashcard

class FlashCard(
  val question: FlashCardContent = FlashCardContent(),
  val answer: FlashCardContent = FlashCardContent(),
  val score: Double = 0.0
)