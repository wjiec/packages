<?php
/**
 * RegisterController.phpphp
 *
 * @package   phalcon
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 * @link      https://github.com/JShadowMan/here
 */


/**
 * Class RegisterController
 */
final class RegisterController extends \Phalcon\Mvc\Controller {
    final public function indexAction() {
    }

    final public function registerAction() {
        $user = new Users();

        $success = $user->save($this->request->getPost(), array(
            'name', 'password'
        ));

        if ($success) {
            $this->view->render('register', 'success');
        } else {
            $this->view->render('register', 'failure');
        }
    }

    final public function successAction() {
    }

    final public function failureAction() {
    }
}
