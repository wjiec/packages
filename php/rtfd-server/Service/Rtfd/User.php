<?php
/**
 * User Class
 */


class Rtfd_User {
    /**
     * @var array
     */
    private $_variables;

    /**
     * Rtfd_User constructor.
     */
    public function __construct() {
        $this->_variables = array(
            'user' => array(
                'username' => null,
                'role_name' => null,
                'privilege_level' => null,
                'allowed_actions' => null
            ),
            'token' => null
        );
    }

    /**
     * !!don't send to client
     *
     * @param string $uid
     */
    public function set_user_id($uid) {
        $this->_variables['user']['uid'] = $uid;
    }

    /**
     * @param string $username
     */
    public function set_username($username) {
        $this->_variables['user']['username'] = $username;
    }

    /**
     * @param $role_name
     */
    public function set_role_name($role_name) {
        $this->_variables['user']['role_name'] = $role_name;
    }

    /**
     * @param $level
     */
    public function set_privilege_level($level) {
        $this->_variables['user']['privilege_level'] = $level;
    }

    /**
     * @param array $actions
     */
    public function set_allowed_actions(array $actions) {
        $this->_variables['user']['allowed_actions'] = $actions;
    }

    /**
     * @param string $token
     */
    public function set_user_token($token) {
        $this->_variables['token'] = $token;
    }

    /**
     * @return array
     */
    public function export() {
        return $this->_variables;
    }

    /**
     * @return string
     */
    public function __toString() {
        return json_encode($this->_variables);
    }
}