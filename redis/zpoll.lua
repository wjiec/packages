local vals = redis.call('zrangebyscore', KEYS[1], 0, KEYS[2], 'limit', 0, ARGV[1] and ARGV[1] or 1)
if vals[1] then
    redis.call('zrem', KEYS[1], unpack(vals))
end

return vals

--
-- $ redis-cli script load "$(cat zpoll.lua)"
-- "2a1b2d7fe7b976a8ae14a7912bb047f9543263a1"
--
-- $ redisc-cli zadd QUEUE:KEY 1 java
-- $ redisc-cli zadd QUEUE:KEY 2 go
-- $ redisc-cli zadd QUEUE:KEY 3 c
--
-- $ redis-cli evalsha 2a1b2d7fe7b976a8ae14a7912bb047f9543263a1 2 QUEUE:KEY 1
-- 1) "java"
-- $ redis-cli evalsha 2a1b2d7fe7b976a8ae14a7912bb047f9543263a1 2 QUEUE:KEY 1
-- (empty list or set)
-- $ redis-cli evalsha 2a1b2d7fe7b976a8ae14a7912bb047f9543263a1 2 QUEUE:KEY 3 2
-- 1) "go"
-- 2) "c"
--
