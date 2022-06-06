package com.avi.memaq.entity

import com.avi.memaq.entity.flashcard.FlashCardEntity
import java.util.*
import javax.persistence.*

@Table(name = "deck")
@Entity
class DeckEntity(
  @Id
  @GeneratedValue
  var id: UUID? = null,
  val name: String? = null,
  @OneToMany(
    mappedBy = "deck",
    cascade = [CascadeType.ALL],
    orphanRemoval = true,
  )
  var flashCardList: MutableList<FlashCardEntity>? = mutableListOf(),
)