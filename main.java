import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
    private static final int MAX_THREADS = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    private static final Set<String> visitedLinks = new HashSet<>();
    private static final Pattern urlPattern = Pattern.compile("https?://(\\w+\.)*(\\w+)");
    private static final int MAX_DEPTH = 3;

    public static void main(String[] args) {
        String startUrl = "https://example.com";
        crawl(startUrl, 0);
        executor.shutdown();
    }

    public static void crawl(String url, int depth) {
        if (!visitedLinks.contains(url) && depth < MAX_DEPTH) {
            visitedLinks.add(url);
            executor.execute(() -> {
                try {
                    System.out.println("Crawling (depth " + depth + "): " + url);
                    Document doc = Jsoup.connect(url).get();
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        String nextUrl = link.absUrl("href");
                        Matcher matcher = urlPattern.matcher(nextUrl);
                        if (matcher.find() && !visitedLinks.contains(nextUrl)) {
                            crawl(nextUrl, depth + 1);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Failed to fetch: " + url);
                }
            });
        }
    }
    
    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
    
    public static boolean isValidURL(String url) {
        Matcher matcher = urlPattern.matcher(url);
        return matcher.find();
    }
    
    public static void saveResultsToFile(Set<String> results) {
        try (java.io.FileWriter writer = new java.io.FileWriter("crawled_urls.txt")) {
            for (String url : results) {
                writer.write(url + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public static String fetchTitle(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.title();
        } catch (IOException e) {
            return "[Failed to retrieve title]";
        }
    }
    
    public static void enhancedCrawl(String url, int depth) {
        if (!visitedLinks.contains(url) && depth < MAX_DEPTH) {
            visitedLinks.add(url);
            executor.execute(() -> {
                try {
                    log("Crawling (depth " + depth + ") " + url);
                    Document doc = Jsoup.connect(url).get();
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        String nextUrl = link.absUrl("href");
                        if (isValidURL(nextUrl) && !visitedLinks.contains(nextUrl)) {
                            enhancedCrawl(nextUrl, depth + 1);
                        }
                    }
                } catch (IOException e) {
                    log("Failed to fetch: " + url);
                }
            });
        }
    }
    
    public static void crawlWithDelay(String url, int depth, int delay) {
        if (!visitedLinks.contains(url) && depth < MAX_DEPTH) {
            visitedLinks.add(url);
            executor.execute(() -> {
                try {
                    Thread.sleep(delay);
                    log("Crawling with delay (depth " + depth + ") " + url);
                    Document doc = Jsoup.connect(url).get();
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        String nextUrl = link.absUrl("href");
                        if (isValidURL(nextUrl) && !visitedLinks.contains(nextUrl)) {
                            crawlWithDelay(nextUrl, depth + 1, delay);
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    log("Failed to fetch: " + url);
                }
            });
        }
    }
}
