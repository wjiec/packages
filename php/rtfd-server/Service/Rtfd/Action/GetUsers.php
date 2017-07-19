<?php
/**
 * Users Manager
 */


class Rtfd_Action_GetUsers extends Rtfd_Abstract_Action {
    /**
     * getting user list
     */
    public function start() {
        // database helper
        $helper = new Rtfd_Helper_Database();
        // fetch all users
        $users = $helper->fetch_all(
            "select `uid`, `name`, `role_id`, `group_id` from `users`;"
        );
        // check result
        if (!$users) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot fetch users list'
            ));
        }
        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'users' => $users
        ));
    }
}