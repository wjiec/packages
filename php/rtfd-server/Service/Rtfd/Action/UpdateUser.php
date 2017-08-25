<?php
/**
 * Update user action
 */


class Rtfd_Action_UpdateUser extends Rtfd_Abstract_Action {
    /**
     * update user
     */
    public function start() {
        // getting user info
        $uid = $this->get_option('uid');
        $username = $this->get_option('username');
        $password = $this->get_option('password');
        $role_id = $this->get_option('role_id');
        $group_id = $this->get_option('group_id');
        // check user info
        if (!$uid || !$username || !$role_id || !$group_id) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'lose some fields'
            ));
        }
        // check exists
        $helper = new Rtfd_Helper_Database();
        $user = $helper->fetch_single_row(
            "select * from `users` where `uid`='{$uid}';"
        );
        // check user
        if (!$user) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'user not found'
            ));
        }
        // password
        if ($password === '') {
            $password = $user['password'];
        } else {
            $password = password_hash($password, PASSWORD_DEFAULT);
        }
        // update
        $result = $helper->query(
            "update
                    `users`
                  set
                    `name`='{$username}',
                    `password`='{$password}',
                    `role_id`='{$role_id}',
                    `group_id`='{$group_id}'
                  where
                    `uid`='{$uid}'
                  ;"
        );

        // check result
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot update user'
            ));
        }

        $role = $helper->fetch_single_row(
            "select * from `roles` where `rid`='{$user['role_id']}';"
        );
        if ($role === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot fetch role'
            ));
        }

        if ($uid === $this->get_config()->get_user_id()) {
            // update token
            $token = Rtfd_Jwt::generate_token(array(
                'expired' => 0,
                'uid' => $user['uid'],
                'gid' => $user['group_id'],
                'username' => $user['nickname'],
                'role' => $role['name'],
                'verify' => md5($password)
            ), _rtfd_default_jwt_key_);
            // set cookies
            Rtfd_Request::set_cookie('rpt', $token);
        }

        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}
