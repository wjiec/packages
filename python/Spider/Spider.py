#-*- coding: UTF-8 -*-
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
    
    def craw(self, seed, count = 50, depth = -1):
        self.urlManager.addUrl(seed)
        
        cnt = 0
        try:
            while self.urlManager.hasUrl() and cnt < count:
                try:
                    url = self.urlManager.getUrl()
                    content = self.htmlDownloader.download(url)
                    urls, data = self.htmlParser.parse(url, content)
                    self.urlManager.addUrls(urls)
                    self.htmlProcesser.collect(data)
                    print 'Craw %d `%s` Done' %(cnt, data['title'])
                    cnt = cnt + 1
                except Exception, e:
                    print 'Craw Error. => ', e
        except KeyboardInterrupt:
            print("\nKeyboardInterrupt Exit.")
            exit()
        except:
            print('FALAL ERROR')


        self.htmlProcesser.output()
    
    
if __name__ == "__main__":
    spider = Spider()
    spider.craw('http://baike.baidu.com/view/21087.htm', count = 20)
