package com.silly.spidersina.dao

import com.silly.spidersina.entity.News
import org.springframework.data.jpa.repository.JpaRepository

interface NewsDao : JpaRepository<News, Long> {
    fun findByNewsId(newsId: String): News?
}
