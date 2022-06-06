package com.avi.memaq.dto.extension

import com.avi.memaq.dto.Deck
import com.avi.memaq.entity.DeckEntity

fun Deck.toEntity(): DeckEntity = DeckEntity(
  id = id,
  name = name
)