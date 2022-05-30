package com.avi.memaq.entity.flashcard

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "flash_card_content")
class FlashCardContentEntity(
  @Id
  @GeneratedValue
  val id: UUID? = null,
  @Column
  val text: String?,
)