<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 11/25/2017
 * Time: 15:11
 */

$random_array = array();
for ($index = 0; $index < 999999; ++$index) {
    $random_array[] = bin2hex(random_bytes(4));
}

$keys = array();
for ($index = 0; $index < 1000; ++$index) {
    $keys[] = bin2hex(random_bytes(4));
}

$start_time = microtime(true);
foreach ($keys as $key) {
    array_search($key, $random_array);
}
$end_time = microtime(true);
echo $end_time - $start_time;

$start_time = microtime(true);
foreach ($keys as $key) {
    foreach ($random_array as $val) {
        if ($val === $key) {
            break;
        }
    }
}
$end_time = microtime(true);


echo $end_time - $start_time;
