<?php
/**
 * AddUser action
 */


class Rtfd_Action_AddUser extends Rtfd_Abstract_Action {
    /**
     * add user
     */
    public function start() {
        // user account info
        $username = $this->get_option('username');
        $password = $this->get_option('password');
        $role_id = $this->get_option('role_id');
        $group_id = $this->get_option('group_id');

        // check parameter
        if (!$username || !$password || !$role_id || !$group_id) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'parameter invalid'
            ));
        }

        // check role_id and group_id is valid
        $helper = new Rtfd_Helper_Database();
        // check role exists
        $role = $helper->fetch_single_row(
            "select * from `roles` where `rid`='{$role_id}';"
        );
        if (!$role) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'role not found, forbidden'
            ));
        }
        // check group exists
        $group = $helper->fetch_single_row(
            "select * from `groups` where `gid`='{$group_id}';"
        );
        if (!$group) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'group not found, forbidden'
            ));
        }

        // check user exists
        // check group exists
        $user = $helper->fetch_single_row(
            "select * from `users` where `name`='{$username}';"
        );
        if ($user) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'duplicate user'
            ));
        }

        $password = password_hash($password, PASSWORD_DEFAULT);
        // insert
        $result = $helper->query(
            "insert into `users`
                    (`name`, `nickname`, `password`, `role_id`, `group_id`)
                  values
                    ('{$username}', '{$username}', '{$password}', '{$role_id}', '$group_id');"
        );

        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}
