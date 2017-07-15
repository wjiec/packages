<?php
/**
 * Abstract Class of Exception
 */


/**
 * Interface Rtfd_Abstract_Exception
 */
abstract class Rtfd_Abstract_Exception extends Exception {
    /**
     * string og errno
     *
     * @var string
     */
    private $_string_errno;

    /**
     * string of error message
     *
     * @var string
     */
    private $_string_error;

    /**
     * Rtfd_Abstract_Exception constructor.
     * @param string $error
     * @param null $errno
     */
    public function __construct($error, $errno = null) {
        $this->_string_errno = Rtfd_Utils::to_string($errno);
        $this->_string_error = Rtfd_Utils::to_string($error);
        // parent constructor
        parent::__construct('Please using Throwable::get_message() method');
    }

    /**
     * get current exception errno
     *
     * @return null|string
     */
    public function get_code() {
        return $this->_string_errno;
    }

    /**
     * get current exception message
     *
     * @return string
     */
    public function get_message() {
        return $this->_string_error;
    }
}