<?php
/**
 * Base Action: Login [Privilege: 0]
 */


class Rtfd_Action_Login extends Rtfd_Abstract_Action {
    /**
     * Login Service
     */
    public function start() {
        // get parameters
        $username = $this->get_option('username');
        $password = $this->get_option('password');
        // check username/password correct
        if ($username === null || $password === null) {
            Rtfd_Request::abort(401, array(
                'errno' => 401,
                'error' => 'username or password lose'
            ));
        }
        // check username/password is match
        $helper = new Rtfd_Helper_Database();
        // fetch user information
        $user = $helper->fetch_single_row(
            "select
                    `uid`,
                    `password`,
                    `nickname`,
                    `users`.`group_id` as `gid`,
                    `roles`.`rid`,
                    `users`.`role_id`,
                    `roles`.`name` as `role_name`
                  from `users` join `roles`
                  where `users`.`name` = '{$username}'
                  having `roles`.`rid`=`users`.`role_id`;"
        );
        // check user valid
        if ($user === false) {
            Rtfd_Request::abort(401, array(
                'errno' => 401,
                'error' => 'cannot found this user'
            ));
        }
        // check password
        if (!password_verify($password, $user['password'])) {
            Rtfd_Request::abort(401, array(
                'errno' => 401,
                'error' => 'username or password invalid'
            ));
        }
        // write cookies
        $token = Rtfd_Jwt::generate_token(array(
            'expired' => 0,
            'uid' => $user['uid'],
            'gid' => $user['gid'],
            'username' => $user['nickname'],
            'role' => $user['role_name']
        ), _rtfd_default_jwt_key_);
        // set cookies
        Rtfd_Request::set_cookie('rpt', $token);
        // update user
        $this->get_config()->set_privilege($token);
        // make response
        return (new Rtfd_Action_Init($this->get_config()))->start();
    }
}
