package sandokminer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class TGJUMiner {
    private WebDriver webDriver;
    //    private static final String goldUrl = "https://www.tgju.org/profile/geram18/history";
//    private static final String goldUrl = "https://www.tgju.org/profile/price_dollar_rl/history";
    private  ChromeOptions options;


    public  ArrayList<ArrayList<String>> getData(int startPage, int endPage, String url) {
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\PC\\Desktop\\java\\sandok-miner\\src\\main\\resources\\chromeDriver\\chromedriver.exe");
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().minimize();
        webDriver.get(url);
        int counter = startPage;
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        WebElement r = webDriver.findElement(By.id("DataTables_Table_0_paginate"));
        List<WebElement> elements = r.findElements(By.tagName("a"));
        for (WebElement w : elements) {
            if (w.getText().equals("" + counter)) {
                w.click();
                System.out.println("page: " + counter + " mining");
                break;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            while (counter <= endPage) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                WebElement table = webDriver.findElement(By.id("DataTables_Table_0"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                WebElement rows = table.findElement(By.id("table-list"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<WebElement> trs = rows.findElements(By.tagName("tr"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (WebElement e : trs) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    List<WebElement> tds = e.findElements(By.tagName("td"));
                    for (WebElement w : tds) {
                        try {
                            WebElement span = w.findElement(By.tagName("span"));
                            if (span.getAttribute("class").equals("high")) {
                                if (w.getText().equals("-")) {
                                    arrayList.add("null");
                                } else
                                    arrayList.add("+" + w.getText());
                            } else if (span.getAttribute("class").equals("low")) {
                                if (w.getText().equals("-")) {
                                    arrayList.add("null");
                                } else
                                    arrayList.add("-" + w.getText());
                            }
                        } catch (Exception error) {
                            try {
                                if (w.getText().equals("-")) {
                                    arrayList.add("null");
                                } else
                                    arrayList.add(w.getText());
                            } catch (Exception er) {
                                continue;
                            }

                        }

                    }

                    if(arrayList.size()==7){
                        int i = arrayList.indexOf("null");
                        arrayList.add(i,"null");
                    }
                    list.add(arrayList);
                }
                r = webDriver.findElement(By.id("DataTables_Table_0_paginate"));
                elements = r.findElements(By.tagName("a"));
                counter++;
                for (WebElement w : elements) {
                    if (w.getText().equals("" + counter)) {
                        w.click();
                        System.out.println("page: " + counter + " mining");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return list;
        }
        return list;
    }


}
