<?php
/**
 * Guest Role
 */


/**
 * Class Rtfd_Role_Guest
 */
class Rtfd_Role_Guest extends Rtfd_Abstract_Role {
    /**
     * Rtfd_Role_Admin constructor.
     */
    public function __construct() {
        $this->set_role_name('Guest');
    }
}
