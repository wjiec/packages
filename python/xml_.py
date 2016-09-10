#!/usr/bin/env python3

from xml.parsers.expat import ParserCreate

def startElementEvent(name, attrs):
    print('Start Element Event', name, attrs)

def charDataEvent(text):
    print('   Char Data Event', text)

def endElementEvent(name):
    print('End Element Event', name)

xml = r'''<?xml version="1.0"?>
<languages count='2'>
    <language description='true'>
        <name>Python</name>
        <description>Simple</description>
    </language>
    <language description='true'>
        <name>C</name>
        <description>Low-Level</description>
    </language>
</languages>
'''

parser = ParserCreate()

parser.StartElementHandler = startElementEvent
parser.CharacterDataHandler = charDataEvent
parser.EndElementHandler = endElementEvent

parser.Parse(xml)