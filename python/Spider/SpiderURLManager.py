# -*- coding: UTF-8 -*-
'''
Created on 2015年12月24日

@author: ShadowMan
'''


class Manager(object):
    def __init__(self):
        self.newURLList = set()
        self.oldURLList = set()
    
    def addUrl(self, newUrl):
        if newUrl not in self.oldURLList and newUrl not in self.newURLList:
            self.newURLList.add(newUrl)

    
    def hasUrl(self):
        return len(self.newURLList)

    
    def getUrl(self):
        old = self.newURLList.pop()
        self.oldURLList.add(old)
        return old

    
    def addUrls(self, urls):
        for u in urls:
            self.addUrl(u)
    
    
    
    
    
    
    
    



