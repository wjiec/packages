# -*- coding: UTF-8 -*-
'''
Created on 2015年12月24日

@author: ShadowMan
'''
import SpiderURLManager, SpiderHTMLDownloader, SpiderHTMLParser,\
    SpiderProcesser

class Spider(object):
    def __init__(self):
        self.urlManager = SpiderURLManager.Manager()
        self.htmlDownloader = SpiderHTMLDownloader.Downloader()
        self.htmlParser = SpiderHTMLParser.Parser()
        self.htmlProcesser = SpiderProcesser.Processer()
    
    def craw(self, seed, count, depth = -1):
        self.urlManager.addUrl(seed)
        
        cnt = 0
        while self.urlManager.hasUrl():
            try:
                url = self.urlManager.getUrl()
                content = self.htmlDownloader.download(url)
                urls, data = self.htmlParser.parse(url, content)
                self.urlManager.addUrls(urls)
                self.htmlProcesser.collect(data)
                print 'Craw %d `%s` Done' %(cnt, data['title'])
                cnt = cnt + 1
            except:
                print 'Craw Failed'
        
        self.htmlProcesser.output()
    
    
if __name__ == "__main__":
    spider = Spider()
    spider.craw('http://baike.baidu.com/view/21087.htm', 1000)