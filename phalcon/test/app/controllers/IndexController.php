<?php


/**
 * Class IndexController
 */
class IndexController extends ControllerBase {

    final public function indexAction() {
        /** @noinspection PhpUndefinedFieldInspection */
        $this->view->dispatcher_params = $this->dispatcher->getParams();
    }

}

