#!/usr/bin/ruby

require 'httpclient'

client = HTTPClient.new

contents = client.get_content 'http://httpbin.org/ip'

puts contents

