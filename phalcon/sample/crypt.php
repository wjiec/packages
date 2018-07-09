<?php
/**
 * crypt.php
 *
 * @package   phalcon
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 * @link      https://github.com/JShadowMan/here
 */
namespace Sample;
use Phalcon\Crypt;


$crypt = new Crypt();

$key = random_bytes(32);
$text = 'hello phalcon';

$encrypted = $crypt->encrypt($text, $key);
$decrypted = $crypt->decrypt($encrypted, $key);

assert($decrypted === $text);
