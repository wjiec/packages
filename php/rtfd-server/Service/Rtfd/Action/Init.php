<?php
/**
 * Base Action: Init
 */


class Rtfd_Action_Init extends Rtfd_Abstract_Action {
    /**
     * initializing user information
     */
    public function start() {
        // create user
        $user = new Rtfd_User();
        // username
        $user->set_username($this->get_config()->get_username());
        // role name
        $user->set_role_name(ucfirst($this->get_config()->get_role_name()));
        // privilege level
        $user->set_privilege_level($this->get_config()->get_privilege_level());
        // allowed actions
        $user->set_allowed_actions($this->get_config()->get_allowed_actions());
        // token string
        $user->set_user_token($this->get_config()->get_user_token());
        // make response
        Rtfd_Response::json_response($user->export());
    }
}