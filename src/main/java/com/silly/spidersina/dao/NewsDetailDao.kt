package com.silly.spidersina.dao

import com.silly.spidersina.entity.NewsDetail
import org.springframework.data.jpa.repository.JpaRepository

interface NewsDetailDao : JpaRepository<NewsDetail, Long> {
}
