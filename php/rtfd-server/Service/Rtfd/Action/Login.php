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
        $username = Rtfd_Request::post_parameter('username');
        $password = Rtfd_Request::post_parameter('password');
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
            "select `password`, `roles`.`name` as `role_name` from `users` join `roles` 
                  where `users`.`name`='{$username}';"
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
        Rtfd_Request::set_cookie('rp', Rtfd_Jwt::generate_token(array(
            'expired' => 0,
            'username' => $username,
            'role' => $user['role_name']
        ), _rtfd_default_jwt_key_));
        // make response
        Rtfd_Response::simple_response(true, null);
    }
}
