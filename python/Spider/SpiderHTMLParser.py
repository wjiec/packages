# -*- coding: UTF-8 -*-
'''
Created on 2015年12月24日

@author: ShadowMan
'''
from bs4 import BeautifulSoup
import re
import urlparse


class Parser(object):
    
    
    def fetchHrefs(self, url, soup):
        links = set()
        hrefs = soup.find_all('a', href=re.compile(r'/view/\d+\.htm'))
        for link in hrefs:
            newUrl = urlparse.urljoin(url, link['href'])
            links.add(newUrl)
        return links
    
    
    def fetchData(self, url, soup):
        data = { 'url' : url }
        title = soup.find('dd', class_='lemmaWgt-lemmaTitle-title').find('h1')
        summary = soup.find('div', class_='lemma-summary').find('div', class_='para')
        data['title'] = title.get_text()
        data['summary'] = summary.get_text()
        return data
        
        
    
    def parse(self, url, content):
        if url is None or content is None:
            return None
        soup = BeautifulSoup(content, 'html.parser', from_encoding='UTF-8')
        hrefs = self.fetchHrefs(url, soup)
        data = self.fetchData(url, soup)
        return hrefs, data
