<?php
/**
 * Action Interface
 */

interface Rtfd_Interface_Api {
    /**
     * Rtfd_Interface_Api constructor.
     */
    public function __construct(array $options);

    /**
     * start current Action operator
     */
    public function start();
}
