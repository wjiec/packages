<?php
/**
 * Editor Role
 */


/**
 * Class Rtfd_Role_Editor
 */
class Rtfd_Role_Editor extends Rtfd_Abstract_Role {
    /**
     * Rtfd_Role_Admin constructor.
     */
    public function __construct() {
        $this->set_role_name('Editor');
    }
}
