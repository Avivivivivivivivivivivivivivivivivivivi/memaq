package com.avi.memaq.entity.extension

import com.avi.memaq.dto.Deck
import com.avi.memaq.entity.DeckEntity

fun DeckEntity.toDto(): Deck = Deck(
  id = id,
  name = name!!
)
