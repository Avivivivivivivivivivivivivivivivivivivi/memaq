package com.avi.memaq.repository

import com.avi.memaq.entity.DeckEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

sealed interface DeckJpaRepository : JpaRepository<DeckEntity, UUID>