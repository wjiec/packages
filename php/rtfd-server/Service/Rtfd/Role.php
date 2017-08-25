<?php
/**
 * User Role Base Class
 */


/**
 * Class Rtfd_Abstract_Role
 */
class Rtfd_Role {
    /**
     * role name
     *
     * @var string
     */
    private $_role_name;

    /**
     * privilege level
     *
     * @var int
     */
    private $_privilege_level;

    /**
     * allowed actions
     *
     * @var array
     */
    private $_allowed_actions;

    /**
     * Rtfd_Abstract_Role constructor.
     */
    final public function __construct() {
    }

    /**
     * @param string $role_name
     */
    public function set_role_name($role_name) {
        $this->_role_name = strtolower(Rtfd_Utils::to_string($role_name));

        $this->_init_role();
    }

    /**
     * @return string
     */
    public function get_role_name() {
        return $this->_role_name;
    }

    /**
     * @return int
     */
    public function get_privilege_level() {
        return $this->_privilege_level;
    }

    /**
     * get allowed actions
     *
     * @param bool $lower_case
     * @return array
     */
    final public function get_allowed_actions($lower_case = false) {
        if ($lower_case === true) {
            return array_map(function($action) {
                return strtolower($action);
            }, $this->_allowed_actions);
        }
        return $this->_allowed_actions;
    }

    /**
     * initializing role options
     */
    final private function _init_role() {
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // getting role options  [no qa]
        $role = $helper->fetch_single_row(
            "select * from `roles` where `name`='{$this->get_role_name()}';"
        );
        // Guest user
        if (!$role) {
            $this->_privilege_level = 0;
        } else {
            // setting member
            $this->_privilege_level = intval($role['privilege_level']);
        }
        // getting allowed actions
        $actions = $helper->fetch_all(
            "select `name` from `actions` where `privilege_level` <= '{$this->_privilege_level}';"
        );
        // empty allowed actions
        if (!$actions || empty($actions)) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'no operations are permitted'
            ));
        }
        // assign to member
        $this->_allowed_actions = array_map(function($array) {
            return current($array);
        }, $actions);
    }
}
