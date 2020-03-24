package com.tbright.ktbaseproject.demo

import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.tbright.ktbaselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.xml.sax.XMLReader
import java.net.URL


class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main
//    val ss = "<h2><strong>集团介绍：<></h2><p>&nbsp;&nbsp;&nbsp;" +
//            "安徽友如云电子商务有限公司成立于2018年5月,注册资金为5000万元,坐落于大湖名城、" +
//            "创新高地——安徽合肥,公司经营以“保护非遗传统，传承非遗文化”为主题的综合性文化类" +
//            "购物网站——如云商城，公司后期规划在全国范围建设多家大型综合非物质文化遗产宣传体验店，" +
//            "以线上线下相结合的模式，致力打造一个集非物质文化遗产宣传与保护、非物质文化遗产类产品" +
//            "普及与发展于一体的商业化生态闭环。</p><p><br></p>" +
//            "<img src=\"https://www.baidu.com/img/bd_logo1.png\" /><br>" +
//            "<img src=\"https://www.baidu.com/img/bd_logo1.png\" />"

    val ss = "<p>哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒哒少时诵诗书所所所所所所所所诵诗书所所所所所所所所诵诗书所所所所所所所所诵诗书所所所所所所所所诗书所所所所所所所所诗书所所所所所所所所诗书所所所所所所所所诗书所所所所所所所所哒哒哒哒哒哒哒哒哒大叔大婶哒哒哒哒 <span data-cke-copybin-start=\"1\">\u200B</span><span tabindex=\"-1\" data-cke-widget-wrapper=\"1\" data-cke-filter=\"off\" class=\"cke_widget_wrapper cke_widget_inline\" data-cke-display-name=\"图像\" data-cke-widget-id=\"0\" style=\"line-height: 0;\" contenteditable=\"false\"><img alt=\"\" src=\"http://203.156.217.226:22000/upload/exam/202002/28184412z9rn.png\" data-cke-widget-keep-attr=\"0\" data-widget=\"image\" class=\"cke_widget_element\" data-cke-widget-data=\"%7B%22hasCaption%22%3Afalse%2C%22src%22%3A%22http%3A%2F%2F203.156.217.226%3A22000%2Fupload%2Fexam%2F202002%2F28184412z9rn.png%22%2C%22alt%22%3A%22%22%2C%22width%22%3A%22720%22%2C%22height%22%3A%22170%22%2C%22lock%22%3Atrue%2C%22align%22%3A%22none%22%2C%22classes%22%3Anull%7D\" data-cke-saved-src=\"http://203.156.217.226:22000/upload/exam/202002/28184412z9rn.png\" width=\"720\" height=\"170\"><span class=\"cke_reset cke_widget_drag_handler_container\" style=\"background: rgba(220, 220, 220, 0.5) url(\"http://203.156.217.226:22003/resource/js/ckeditorCopy/plugins/widget/images/handle.png\") repeat scroll 0% 0%; top: -15px; left: 0px; display: block;\"><img class=\"cke_reset cke_widget_drag_handler\" data-cke-widget-drag-handler=\"1\" src=\"data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==\" title=\"点击并拖拽以移动\" draggable=\"true\" width=\"15\" height=\"15\"></span><span class=\"cke_image_resizer\" title=\"点击并拖拽以改变尺寸\">\u200B</span></span><span data-cke-copybin-end=\"1\">\u200B</span><br></p><p>书所所所所所所所所诗书所所所所所书所所所所所所所所诗书所所所所所书所所所所所所所所诗书所所所所所书所所所所所所所所诗书所所所所所书所所所所所所所所诗书所所所所所书所所所所所所所所诗书所所所所所</p><div data-cke-hidden-sel=\"1\" data-cke-temp=\"1\" style=\"position:fixed;top:0;left:-1000px\"><br></div>"

    //https://www.jianshu.com/p/6939676963ce
    override fun initView(savedInstanceState: Bundle?) {
        hellowrold.text = HtmlCompat.fromHtml(ss, HtmlCompat.FROM_HTML_MODE_LEGACY, object : Html.ImageGetter {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getDrawable(source: String?): Drawable {
                Log.e("RG", "source---?>>>" + source)
                if(source?.contains("http") == true){

                    var ss = runBlocking (Dispatchers.IO){
                        var ulr = URL(source)
                        var drawable = Drawable.createFromStream(ulr.openStream(),"")
                        drawable.setBounds(0,0,drawable.intrinsicWidth,drawable.intrinsicHeight)
                        drawable
                    }
                    return ss
                }
               return getDrawable(R.drawable.ic_launcher_background)!!
            }
        },object :Html.TagHandler{
            override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {

            }
        })
    }

    override fun initData() {
    }

}
