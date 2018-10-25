package com.silly.spidersina.timer

import com.google.gson.Gson
import com.silly.spidersina.dao.NewsDao
import com.silly.spidersina.dao.NewsDetailDao
import com.silly.spidersina.entity.News
import com.silly.spidersina.entity.NewsDetail
import com.silly.spidersina.entity.SinaNews
import com.silly.spidersina.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpiderTimer {

    @Autowired
    lateinit var newsDao: NewsDao
    @Autowired
    lateinit var newsDetailDao: NewsDetailDao

    private var isTask = true// 只执行一条任务
    private val interval = arrayOf(5L, 11L, 8L, 14L, 20L, 16L, 3L)
    private val sleepTime = arrayOf(3L, 2L, 5L, 7L)
    private val random = Random()

    private var executeTime = 0L
    @Scheduled(fixedRate = 1000)
    fun scheduled1() {
        val currentTime = System.currentTimeMillis() / 1000
        if (executeTime == 0L) executeTime = currentTime
        if (currentTime > executeTime) executeTime = currentTime + interval[random.nextInt(7)] * 60// 防止当前时间大于开始时间
        log("当前时间:$currentTime ---- 下一次执行任务时间:$executeTime")
        if (executeTime != currentTime) return
        if (isTask) task()
        executeTime = currentTime + interval[random.nextInt(7)] * 60
    }

    /**
     * 执行的任务
     */
    private fun task() {
        isTask = false
        log("任务开始")
        for (i in 1..3) {
            val data = UrlReqUtil.get("https://interface.sina.cn/wap_api/layout_col.d.json?showcid=38712&col=38713&level=1%2C2&show_num=30&page=$i")
            val fromJson = Gson().fromJson(data, SinaNews::class.java)
            val list = fromJson.result.data.list
            for ((index, item) in list.withIndex()) {
                try {
                    log("第 $i 页，进度:${index + 1}/ ${list.size}，新闻ID:${item.news_id}")
                    if (!isToDay(item.cdateTime)) break
                    if (newsDao.findByNewsId(item.news_id) != null) continue
                    Thread.sleep(sleepTime[random.nextInt(4)] * 1000)
                    val contents = newsDetailAnalysis(item.URL)
                    newsDao.save(News(item.news_id, item.title,
                            item.allPics.pics[0].imgurl,
                            time2timeCurrent(item.cdateTime), item.source, item.summary))
                    newsDetailDao.save(NewsDetail(item.news_id, contents))
                } catch (e: Exception) {
                    continue
                }
            }
            log("第 $i 页完成")
        }
        Thread.sleep(10000)
        log("任务完成")
        isTask = true
    }

}
