#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
import flask
from flask import render_template

app = flask.Flask(__name__)


@app.route('/')
@app.route('/home')
def home():
    return render_template('home.html', title="首页", to="about", todesc="关于")


@app.route('/about')
def about():
    return render_template('about.html', title="首页", to="home", todesc="首页")


if __name__ == '__main__':
    app.run('0.0.0.0', 8898, debug=True)
