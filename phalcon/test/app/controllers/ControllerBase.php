<?php

use Phalcon\Mvc\Controller;

class ControllerBase extends Controller {

    /**
     * https://docs.phalconphp.com/hr/3.3/controllers#events
     *
     * @param \Phalcon\Dispatcher $dispatcher
     */
    public function beforeExecuteRoute(\Phalcon\Dispatcher $dispatcher) {
        if ($dispatcher->getHandlerClass() === DebugController::class) {
            $dispatcher->setParam('debug_mode', true);
        }
    }

    public function afterExecuteRoute(\Phalcon\Dispatcher $dispatcher) {
        /* @var \Phalcon\Logger\Adapter\File $logger */
        $logger = $this->di->get('logger');

        $logger->info(sprintf(
            "%s -> %s::%s",
            date('Y-m-d h:i:s'), $dispatcher->getHandlerClass(), $dispatcher->getActionName()
        ));
    }
}
