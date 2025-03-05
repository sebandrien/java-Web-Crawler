# Multi-threaded Web Crawler in Java  

This is a **multi-threaded web crawler** built in Java using **JSoup** for HTML parsing. It efficiently crawls web pages, extracts links, and supports additional features such as **delayed crawling, enhanced validation, and URL storage**.  

## Features  
- **Multi-threading**: Uses a thread pool to speed up crawling.  
- **Depth control**: Limits recursion depth to prevent infinite loops.  
- **URL validation**: Uses regex to ensure valid URLs.  
- **Page title fetching**: Retrieves and logs webpage titles.  
- **Delayed crawling**: Adds an optional delay between requests.  
- **File storage**: Saves crawled URLs to a text file.  

## Technologies Used  
- **Java** (Concurrency & Collections Framework)  
- **JSoup** (HTML parsing & web scraping)  

## Usage  
1. **Clone the repository**:  
   ```sh
   git clone https://github.com/your-username/web-crawler-java.git
   cd web-crawler-java
2. **Compile and run the program**:
  javac WebCrawler.java  
  java WebCrawler

3. **Modify the starting URL in main() to your desired website:**
   String startUrl = "https://example.com";
