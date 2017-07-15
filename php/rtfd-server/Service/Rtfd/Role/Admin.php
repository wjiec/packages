<?php
/**
 * Admin Role
 */


/**
 * Class Rtfd_Role_Admin
 */
class Rtfd_Role_Admin extends Rtfd_Abstract_Role {
    /**
     * Rtfd_Role_Admin constructor.
     */
    public function __construct() {
        $this->set_role_name('Admin');
    }
}
