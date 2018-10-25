package com.silly.spidersina

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.silly.spidersina.dao.NewsDao
import com.silly.spidersina.dao.NewsDetailDao
import com.silly.spidersina.entity.News
import com.silly.spidersina.entity.NewsDetail
import com.silly.spidersina.entity.SinaNews
import com.silly.spidersina.entity.X
import com.silly.spidersina.utils.*
import org.dom4j.io.SAXReader
//import org.jsoup.Jsoup
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class SpidersinaApplicationTests {

    private var logger = LoggerFactory.getLogger(SpidersinaApplicationTests::class.java)

    @Test
    fun contextLoads() {
        val data = UrlReqUtil.get("https://interface.sina.cn/wap_api/layout_col.d.json?showcid=38712&col=38713&level=1%2C2&show_num=30&page=1")
        val fromJson = Gson().fromJson(data, SinaNews::class.java)
        println(fromJson)
    }

    @Autowired
    lateinit var newsDao: NewsDao
    @Autowired
    lateinit var newsDetailDao: NewsDetailDao

    @Test
    fun dbTest() {
//        newsDao.save(News("hmuuiyw4730483-comos-sports-cms", "\"WiFi探针\"涉嫌违法 这样的\"毒瘤式发明\"当及时喊停",
//                "https://k.sinaimg.cn/n/tech/transform/667/w400h267/20181023/sAa4-hmuuiyw4728600.jpg/w120h90l50t1ec4.jpg",
//                "2018-10-23 17:17:06", "新京报", "作为一种营销手段，“WiFi探针”技术，可谓涉嫌违反多项法律法规。"))
//        newsDetailDao.save(NewsDetail("hmuuiyw4730483-comos-sports-cms", "作为一种营销手段，“WiFi探针”技术，可谓涉嫌违反多项法律法规。"))
        val findByNewsId = newsDao.findByNewsId("hmuuiyw4730483-comos-sport")
//        val findByNewsId = newsDao.findAll()
        println(findByNewsId)
    }

    @Test
    fun newsDetailTest() {
//        val data = UrlReqUtil.get("https://tech.sina.cn/i/gn/2018-10-23/detail-ihmuuiyw4730483.d.html?&cid=38712")
//        println(data)
        logger.info("的塑料袋捡垃圾的啦啥")
        logger.error("多萨达")
        logger.debug("啊飒飒是法国风格")// 无法打出
        logger.warn("656同意让他让他")
    }

    @Test
    fun jsoupTest() {
//        val doc = Jsoup.connect("http://localhost:63344/demo1/xxxx.html?_ijt=b1kis60l52pp1kf715ddcbhhes").get()
//        println(doc.select("table").attr("style","width: 100%"))
//        println(doc)

    }


    @Test
    fun jsonDo() {
        for (i in 1..3) {
            val data = UrlReqUtil.get("https://interface.sina.cn/wap_api/layout_col.d.json?showcid=38712&col=38713&level=1%2C2&show_num=30&page=$i")
            val fromJson = Gson().fromJson(data, SinaNews::class.java)
            val list = fromJson.result.data.list
            for ((index, item) in list.withIndex()) {
                log("新闻ID:${item.news_id}")
                log("第 $i 页，进度:${index + 1}/ ${list.size}")
                if (!isToDay(item.cdateTime)) break
                if (newsDao.findByNewsId(item.news_id) != null) continue
                Thread.sleep(2000)
                val contents = newsDetailAnalysis(item.URL)
                newsDao.save(News(item.news_id, item.title,
                        item.allPics.pics[0].imgurl,
                        item.cdateTime, item.source, item.summary))
                newsDetailDao.save(NewsDetail(item.news_id, contents))
            }
            log("第 $i 页完成")
        }
    }


    @Test
    fun time2time() {
        val findAll = newsDao.findAll()
        for ((index, item) in findAll.withIndex()) {
            item.cdateTime = time2timeCurrent(item.cdateTime)
            log(index)
        }
        newsDao.saveAll(findAll)
    }
}
