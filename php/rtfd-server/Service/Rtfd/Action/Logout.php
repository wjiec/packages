<?php
/**
 * Logout
 */


class Rtfd_Action_Logout extends Rtfd_Abstract_Action {
    /**
     * logout
     */
    public function start() {
        // delete cookie
        Rtfd_Request::set_cookie('rpt', '');
    }
}