# web-crawler  
web-crawler is a web crawler for Java which provides a simple interface for crawling the Web.  
  
## Start application  

    mvn exec:java -Dexec.mainClass="com.softeq.crawler.controller.CrawlerController" "-Dexec.args=url=https://example.com 'tags=tag1,...,tagN' path=D:\... file_name=test.csv top_file_name=top_test.csv height=3 pages_limit=10 top_limit=3" 

 
**Default arguments:**

    path=.../src/main/resources  
    file_name=test.csv
    top_file_name=top_test.csv  
    height=8 
    pages_limit=10000
    top_limit=10
  
  **Description:**
 - url=https://example.com - url, which will be scanned;
 - tags=tag1,...,tagN - detected terms;
 - path=... - path to save a result statistics;
 - file_name=test.csv - name of file with statistics;
 - tip_file_name=top.csv - name of file with sorted statistics;
 - height=8 - link depth;
 - pages_limit=10000 - max visited pages limit;
 - top_limit=10 - top pages by total hits;
 
 **Example:**
Detect url by terms with pages limit = 20, height=8 and top limit=10,    save it to path C:\\:

    mvn exec:java -Dexec.mainClass="com.softeq.crawler.controller.CrawlerController" "-Dexec.args=url=https://en.wikipedia.org/wiki/Elon_Musk 'tags=Tesla,Musk,Gigafactory,Elon Musk' path=C:\ file_name=test.csv top_file_name=top_test.csv pages_limit=20"

Result:
***test.csv***
![enter image description here](https://sun9-60.userapi.com/ZVixHV8GlK_5wzxMk21u-okNYEOMa-4PI_fO7g/VEnwvSuIZOU.jpg)
***top_test.csv***
![enter image description here](https://sun9-52.userapi.com/VEuD5juyYqgUU3SaHBJ0KbdlKEU5_BnJfT-Dkw/U55FHOeBEOA.jpg)

## Documentation

Documentation in progress....