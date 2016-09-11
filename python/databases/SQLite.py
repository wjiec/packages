#!/usr/bin.env python3

import sqlite3

conn = sqlite3.connect('test.db')
cursor = conn.cursor()

o = cursor.execute('CREATE TABLE IF NOT EXISTS `user` (\
    `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\
    `name` VARCHAR NOT NULL,\
    `password` VARCHAR NOT NULL\
    )')
print('create table', o, o.rowcount)

o = cursor.execute('INSERT INTO `user` (`name`,`password`) VALUES (\'shadow\', \'ShadowPassword\')')
print('insert data', o, o.rowcount)

cursor.close()
conn.commit()
conn.close()

conn = sqlite3.connect('test.db')
cursor = conn.cursor()

o = cursor.execute(r"SELECT * FROM `user`")
print('select', o, o.rowcount)

for item in cursor.fetchall():
    print(item)

cursor.close()
conn.commit()
conn.close()