package com.silly.spidersina.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class News(
        var newsId: String = "",// 新闻id
        var title: String = "",// 标题
        var imgurl: String = "",// 图片
        var cdateTime: Long = 0L,// 创建时间
        var source: String = "",// 出版社
        var summary: String = "",// 摘要
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null)