package com.example.mydisney.task;

import com.example.mydisney.mapper.CarParkFlowMapper;
import com.example.mydisney.entity.CarParkFlowEntity;
import com.example.mydisney.enumDisney.parkEnum;
import com.example.mydisney.tool.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class CarParkFlow {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${chrome.driver}")
    private String CHROMEDRIVER;
    @Value("${chrome.path}")
    private String CHROMEPATH;
    @Value("${chrome.parhUrl}")
    private String CHROMEURL;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private CarParkFlowMapper carParkFlowMapper;

    @Scheduled(cron = "0 30 0 * * ?")
    public void getParkTime(){
        logger.info(LocalDateTime.now() + "开始获取停车信息");

        // 设置 ChromeDriver 路径
        System.setProperty("webdriver.chrome.driver","F:\\anzhuosdk\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        option.setBinary("F:\\anzhuosdk\\chrome-win64\\chrome-win64\\chrome.exe");

        // 创建 ChromeDriver 实例
        WebDriver driver = new ChromeDriver(option);

        // 打开网页
        driver.get(CHROMEURL);

        // 等待页面加载（你可能需要根据实际情况进行适当的等待）
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement element = driver.findElement(By.id("searchName"));

        element.sendKeys("上海迪士尼度假区");

        driver.findElement(By.id("searchInput")).click();


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document parse = Jsoup.parse(driver.getPageSource());
        driver.close();
        Elements parking_address = parse.getElementsByClass("parking_address");
        Map<String, Integer> map = new HashMap<>();
        Random random = new Random(); // 定义随机类
        for (Element parkingAddress : parking_address) {
            Element parent = parkingAddress.parent();
            String count = parent.getElementsByClass("group-llr-b").get(1).text().split(": ")[1];
            String title = parent.getElementsByClass("l-icon-st").get(0).text();
            if (parkEnum.getIndex(title) != null) {
                switch (count) {
                    case "充足":
                        map.put(title, new Double(parkEnum.getIndex(title) * random.nextInt(30) * 0.01).intValue());
                        break;
                    case "一般":
                        map.put(title, new Double(parkEnum.getIndex(title) * (random.nextInt(30) + 50) * 0.01).intValue());
                        break;
                    case "紧张":
                        map.put(title, new Double(parkEnum.getIndex(title) * (random.nextInt(20) + 80) * 0.01).intValue());
                        break;
                }
            }
        }
        for (String key : map.keySet()) {
            CarParkFlowEntity carParkFlowEntity = new CarParkFlowEntity();
            carParkFlowEntity.setTime(new Date());
            carParkFlowEntity.setName(key);
            carParkFlowEntity.setFlow(String.valueOf(map.get(key)));
            carParkFlowEntity.setDate(dateUtil.getCurrentDate(null));
            int count = carParkFlowMapper.count(carParkFlowEntity);
            if(count == 0){
                carParkFlowMapper.insert(carParkFlowEntity);
            }else {
                carParkFlowMapper.update(carParkFlowEntity);
            }
        }
        logger.info(LocalDateTime.now() + "获取停车信息结束");

    }


}
