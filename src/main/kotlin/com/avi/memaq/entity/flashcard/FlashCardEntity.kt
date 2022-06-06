package com.avi.memaq.entity.flashcard

import com.avi.memaq.entity.DeckEntity
import java.util.*
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
  @ManyToOne(fetch = FetchType.LAZY)
  var deck: DeckEntity? = null,
)