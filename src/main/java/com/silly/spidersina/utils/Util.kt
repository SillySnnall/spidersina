package com.silly.spidersina.utils

import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*


/**
 * 新闻详情页面数据处理
 */
fun newsDetailAnalysis(url: String): String {
    val doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36").get()
    doc.getElementsByClass("art_box").attr("style", "background: #fff")
    doc.getElementsByClass("hd_s1").remove()
    doc.getElementsByAttributeValue("sax-type", "sax_5").remove()
    doc.getElementsByClass("z_c2").remove()
    doc.getElementsByClass("z_c3").remove()
    doc.getElementsByClass("j_wbrecommend_wrap").remove()
    doc.getElementsByClass("hd_nav").remove()
    doc.getElementsByClass("ft_cm").remove()
    doc.getElementsByAttributeValue("sax-type", "proxy").remove()
    doc.getElementsByClass("j_alert_box").remove()
    doc.getElementsByClass("look_info").remove()
    doc.getElementsByClass("j_float_bg").remove()
    doc.getElementsByClass("j_float_wbro").remove()
    doc.getElementsByClass("j_relevent_video").remove()
    doc.getElementsByAttributeValue("sax-type", "lmt").remove()
    doc.getElementsByAttributeValue("sax-type", "sax_hw").remove()
    doc.getElementsByAttributeValue("sax-type", "phtoto_proxy").remove()
    doc.getElementsByAttributeValue("sax-type", "sax_7").remove()
    doc.getElementsByAttributeValue("sax-type", "sax_jdt").remove()
    doc.getElementsByAttributeValue("property", "og:url").remove()
    doc.getElementsByAttributeValue("property", "article:author").remove()
    doc.getElementsByAttributeValue("name", "apple-mobile-web-app-title").remove()
    doc.getElementsByAttributeValue("rel", "apple-touch-icon-precomposed").remove()
    doc.getElementsByClass("art_tit_h1").attr("style", "margin-bottom: 20px")
    doc.title(doc.title().toString().replace("新浪", ""))
    doc.getElementById("wx_pic").remove()
    val script = doc.select("script")
    for (scriptItem in script) {
        val scriptItemS = scriptItem.toString()
        if (scriptItemS.contains("whn2.star-media.cn")
                || scriptItemS.contains("parseInt")
                || scriptItemS.contains("loadJs")
                || scriptItemS.contains("articleMaskLayer")
                || scriptItemS.contains("sudaMapConfig")
                || scriptItemS.contains("quset_mobile.min.js")
        ) scriptItem.remove()
    }

    val img = doc.select("img")
    for (imgItem in img) {
        if (imgItem.toString().contains("src=\"data:image")) imgItem.attr("src", imgItem.attr("data-src"))
    }
    return doc.toString()
}


/**
 * 判断时间是不是今天
 */
fun isToDay(date: String): Boolean {
    // 去除 HH:mm:ss
    val split = date.split(" ")
    if (split.isEmpty()) return false
    //当前时间
    val now = Date()
    val sf = SimpleDateFormat("yyyy-MM-dd")
    //获取今天的日期
    val nowDay = sf.format(now)
    //对比的时间
    val day = sf.format(sf.parse(split[0]))
    return day == nowDay
}

/**
 * log
 */
fun log(message: Any?) {
    println("Silly----Log---- $message")
}

/**
 * yyyy-MM-dd HH:mm:ss 转时间戳
 */
fun time2timeCurrent(time: String): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = simpleDateFormat.parse(time)
    val ts = date.time
    return ts.toString()
}
