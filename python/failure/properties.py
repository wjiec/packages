from __future__ import print_function
import os, sys, re, json

def videoInfo(video):
    command = 'ffprobe -v quiet -print_format json -show_format -show_streams -i "%s"'
    jsonString = os.popen( command % ( video ) ).read()
    try:
        return json.loads(jsonString)
    except Exception as e:
        print('error occurs', e)

def writeNFO(videoInformation):
    videoName = videoInformation['format']['filename']
    nfoFileName = videoName[:videoName.rfind('.')] + '.nfo'

    nfoHandle = open(nfoFileName, 'w')
    nfoHandle.write('''\
General
Complete name                  : %s
Format                         : %s
File size                      : %.2f GB
Duration                       : %d h %d min
Overall bit rate               : %s kb/s
Encoded date                   : %s
Writing library                : %s
''' % ( 
        videoName,                                 # Complete name
        videoInformation['format']['format_name'], # Format
        float(os.path.getsize(videoName)) / (1024 ** 3),    # File size
        int(float(videoInformation['format']['duration']) / 3600), ((float(videoInformation['format']['duration']) / 3600) % 1) * 60,
        int(videoInformation['format']['bit_rate']) / 1000, # Overall bit rate
        videoInformation['format']['tags']['creation_time'],# Movie name
        videoInformation['format']['tags']['encoder']
        ))

    for stream in videoInformation['streams']:
        nfoHandle.write('''\n\
%s
ID                             : %s
Format                         : %s
Format profile                 : %s
''' % (
        stream['codec_type'],
        stream['index'],
        stream['codec_name'],
        stream['profile'] if 'profile' in stream else ""
        ))

    nfoHandle.close()

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Usage: %s [video]" % ( sys.argv[0] ))
        exit()
    else:
        writeNFO(videoInfo(sys.argv[1]))

