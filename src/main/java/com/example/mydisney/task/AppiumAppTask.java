package com.example.mydisney.task;

import com.example.mydisney.entity.Playtime;
import com.example.mydisney.mapper.PlaytimeMapper;
import com.example.mydisney.service.IPlaytimeService;
import com.example.mydisney.tool.DateUtil;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppiumAppTask {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPlaytimeService playtimeService;

    @Autowired
    private PlaytimeMapper playtimeMapper;

    @Autowired
    private DateUtil dateUtil;


    @Scheduled(cron = "0 15 0 * * ?")
    public void setupPlayTime() {
        logger.info(LocalDateTime.now() + "开始获取排队时间信息");

        // 使用属性选择器选择具有特定 resource-id 属性的标签
        String resourceId = "com.disney.shanghaidisneyland_goo:id/txt_title";
        String resourceTime = "com.disney.shanghaidisneyland_goo:id/txt_wait_time";

        AppiumDriver driver = null;
        try {
            BaseOptions options = new BaseOptions()
                    .setPlatformName("Android")
                    .setPlatformVersion("12")
                    .amend("deviceName", "127.0.0.1:16384")
                    .amend("autoAcceptAlerts", "true")
                    .amend("appPackage", "com.disney.shanghaidisneyland_goo")
                    .amend("appActivity", "com.disney.wdpro.park.activities.SplashActivity");
            driver = new AppiumDriver(
                    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                    new URL("http://127.0.0.1:4723/wd/hub"), options
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            WebElement element = driver.findElement(AppiumBy.id("com.disney.shanghaidisneyland_goo:id/right_button"));
            element.click();
            WebElement element1 = driver.findElement(AppiumBy.id("android:id/button1"));
            element1.click();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            WebElement element2 = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"com.disney.shanghaidisneyland_goo:id/tabImageView\"])[3]"));
            element2.click();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                WebElement element3 = driver.findElement(AppiumBy.id("android:id/button1"));
                element3.click();
                WebElement element4 = driver.findElement(AppiumBy.id("com.disney.shanghaidisneyland_goo:id/exitTitle"));
                element4.click();
                WebElement element5 = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"com.disney.shanghaidisneyland_goo:id/tabImageView\"])[3]"));
                element5.click();
                WebElement element6 = driver.findElement(AppiumBy.id("com.disney.shanghaidisneyland_goo:id/scrollBoxView"));
                element6.click();
                WebElement element7 = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"com.disney.shanghaidisneyland_goo:id/facilityTypeImage\"])[4]"));
                element7.click();
            } catch (Exception e) {
                Thread.sleep(1000);
                this.setupPlayTime();
            }

            Map<String, String> resultMap = new HashMap<>();
            for (int i = 0; i < 8; i++) {
                // 此处处理页面
                getResultMap(resourceId, resourceTime, driver, resultMap);

                swipeDown(driver);
            }
            System.out.println(resultMap);
            String currentDate = dateUtil.getCurrentDate(null);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("date", currentDate);
            List<Playtime> playtimes = playtimeMapper.selectByMap(paramMap);
            if (playtimes.size() > 0) {
                playtimeMapper.deleteByMap(paramMap);
            }
            for (String key : resultMap.keySet()) {
                Playtime playtime = new Playtime();
                playtime.setName(key);
                playtime.setWaitTime(resultMap.get(key));
                playtime.setUpdateTime(LocalDateTime.now());
                playtime.setDate(currentDate);
                playtimeMapper.insert(playtime);
            }
        } catch (MalformedURLException | InterruptedException e) {
            this.setupPlayTime();
        } finally {
            driver.quit();
        }
    }

    private static Map<String, String> getResultMap(String resourceId, String resourceTime, AppiumDriver driver, Map<String, String> resultMap) {
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements paragraphs = doc.select("[resource-id=" + resourceId + "]");
        for (Element paragraph : paragraphs) {
            getContentBetweenElements(paragraph, resourceTime, resultMap);
        }
        return resultMap;
    }

    private static void getContentBetweenElements(Element startElement, String resourceTime, Map<String, String> reusltMap) {
        String title = startElement.attr("text");
        if (reusltMap.get(title) == null && startElement.nextElementSibling() != null) {
            Element sibling = startElement.nextElementSibling().parent();
            reusltMap.put(title, null);
            // 遍历兄弟元素，直到下一个目标元素（endElement）为止
            while (sibling != null && sibling.parent() != null) {
                if (sibling.parent().select("[resource-id=" + resourceTime + "]").size() > 0) {
                    reusltMap.put(startElement.attr("text"), sibling.parent().select("[resource-id=" + resourceTime + "]").attr("text"));
                }
                sibling = sibling.nextElementSibling();
            }
        }
    }

    public static void swipeDown(AppiumDriver driver) throws MalformedURLException {

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point start = new Point(526, 1768);
        Point end = new Point(552, 720);
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

}
