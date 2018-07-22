<?php

use Phalcon\Mvc\Controller;

class ControllerBase extends Controller {
    public function beforeExecuteRoute(\Phalcon\Dispatcher $dispatcher) {
        var_dump($dispatcher->getControllerName());
    }
}
