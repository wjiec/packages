# -*- coding: UTF-8 -*-
'''
Created on 2015年12月24日

@author: ShadowMan
'''
import urllib2


class Downloader(object):
    
    
    def download(self, url):
        html = urllib2.urlopen(url)
        if html.getcode() == 200:
            return html.read()
        else:
            return None
    
    



