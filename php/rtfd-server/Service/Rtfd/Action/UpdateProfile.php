<?php
/**
 * update self
 */


class Rtfd_Action_UpdateProfile extends Rtfd_Abstract_Action {
    /**
     * update self profile
     */
    public function start() {
        $uid = $this->get_config()->get_user_id();
        $nickname = $this->get_option('nickname');
        $password = trim($this->get_option('password'));

        $helper = new Rtfd_Helper_Database();
        $user = $helper->fetch_single_row(
            "select * from `users` where `uid`='{$uid}';"
        );

        if ($user === false) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'user not found'
            ));
        }

        if ($password === '') {
            $password = $user['password'];
        } else {
            $password = password_hash($password, PASSWORD_DEFAULT);

            // select role
            $role = $helper->fetch_single_row(
                "select * from `roles` where `rid`='{$user['role_id']}';"
            );
            if ($role === false) {
                Rtfd_Request::abort(500, array(
                    'errno' => 500,
                    'error' => 'cannot fetch role'
                ));
            }
            // update token
            $token = Rtfd_Jwt::generate_token(array(
                'expired' => 0,
                'uid' => $user['uid'],
                'gid' => $user['group_id'],
                'username' => $nickname,
                'role' => $role['name'],
                'verify' => md5($password)
            ), _rtfd_default_jwt_key_);
            // set cookies
            Rtfd_Request::set_cookie('rpt', $token);
        }

        // update
        $result = $helper->query(
            "update
                    `users`
                  set
                    `nickname`='{$nickname}',
                    `password`='{$password}'
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

        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}