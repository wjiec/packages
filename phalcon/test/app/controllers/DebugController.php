<?php
/**
 * DebugController.php
 *
 * @package   phalcon
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 * @link      https://github.com/JShadowMan/here
 */



final class DebugController extends ControllerBase {

    final public function indexAction() {
        $this->view->dispatcher_params = $this->dispatcher->getParams();
    }

}
