package com.avi.memaq.entity.flashcard

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "flash_card")
class FlashCardEntity(
  @Id
  @GeneratedValue
  val id: UUID? = null,
  @Column(nullable = false)
  var score: Double = 0.0,
  @OneToOne(cascade = [CascadeType.ALL], optional = false)
  @JoinColumn
  val question: FlashCardContentEntity,
  @OneToOne(cascade = [CascadeType.ALL], optional = false)
  @JoinColumn
  val answer: FlashCardContentEntity,
  @Column(nullable = false)
  val deckId: Long = 1,
)