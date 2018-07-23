<?php

use Phalcon\Mvc\Controller;

class ControllerBase extends Controller {

    /**
     * https://docs.phalconphp.com/hr/3.3/controllers#events
     *
     * @param \Phalcon\Dispatcher $dispatcher
     */
    public function beforeExecuteRoute(\Phalcon\Dispatcher $dispatcher) {
    }

    public function afterExecuteRoute(\Phalcon\Dispatcher $dispatcher) {
        var_dump($dispatcher);
        die();
    }
}
