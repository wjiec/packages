<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr\Lib\Abstracts;
use Throwable;


class ExceptionBase extends \Exception {
    /**
     * ExceptionBase constructor.
     * @param string $message
     * @param int $code
     * @param Throwable|null $previous
     */
    public function __construct($message, $code = 1996, Throwable $previous = null) {
        parent::__construct($message, 1996, $previous);
    }
}
