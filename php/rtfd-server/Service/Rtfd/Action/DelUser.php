<?php
/**
 * Delete User
 */


class Rtfd_Action_DelUser extends Rtfd_Abstract_Action {
    /**
     * del user
     */
    public function start() {
        // user id
        $uid = $this->get_option('uid');
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // check exists
        $user = $helper->fetch_single_row(
            "select * from `users` where `uid`='{$uid}';"
        );
        // check exists
        if (!$user) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'user not found'
            ));
        }
        // check username is `admin`
        if (strtolower($user['name']) === 'admin') {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'cannot delete admin'
            ));
        }
        // delete
        $result = $helper->query(
            "delete from `users` where `uid`='{$uid}';"
        );
        // check result
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot delete user'
            ));
        }
        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}
