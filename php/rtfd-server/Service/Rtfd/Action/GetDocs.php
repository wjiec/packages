<?php
/**
 * Getting Docs List
 */


class Rtfd_Action_GetDocs extends Rtfd_Abstract_Action {
    /**
     * from database getting allowed docs
     */
    public function start() {
        // current user id
        $user_id = $this->get_config()->get_user_id();
        // group id
        $group_id = $this->get_config()->get_group_id();
        // current user privilege level
        $privilege_level = $this->get_config()->get_privilege_level();
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // getting all docs
        $docs = $helper->fetch_all(
            "select
                    `name`
                  from `docs`

                  where `min_privilege_level`<='{$privilege_level}'
                    or `owner_id`='{$user_id}'
                    or `group_id`='{$group_id}'
                  ;"
        );
        // make response
        Rtfd_Response::json_response($docs);
    }
}
