package com.silly.spidersina.entity


data class SinaNews(
    var result: Result
)

data class Result(
    var status: Status,
    var data: Data
)

data class Status(
    var code: Int,
    var msg: String
)

data class Data(
    var total: Int,
    var count: Int,
    var list: List<X>
)

data class X(
    var _id: String,
    var title: String,
    var stitle: String,
    var URL: String,
    var basetype: String,
    var news_id: String,
    var pc_url: String,
    var mediaTypes: String,
    var cTime: String,
    var cdateTime: String,
    var type: String,
    var allPics: AllPics,
    var source: String,
    var summary: String,
    var commentid: String,
    var comment: String
)

data class AllPics(
    var total: Int,
    var pics: List<Pic>
)

data class Pic(
    var note: String,
    var imgurl: String
)