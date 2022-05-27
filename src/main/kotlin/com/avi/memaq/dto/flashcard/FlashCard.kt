package com.avi.memaq.dto.flashcard

class FlashCard(
  val frontSide: FlashCardView = FlashCardView(),
  val backSide: FlashCardView = FlashCardView(),
  val score: Double = 10.0
)