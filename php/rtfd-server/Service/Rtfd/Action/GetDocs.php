<?php
/**
 * Getting Docs List
 */


class Rtfd_Action_GetDocs extends Rtfd_Abstract_Action {
    /**
     * from database getting allowed docs
     */
    public function start() {
        // current user privilege level
        $privilege_level = $this->get_config()->get_privilege_level();
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // getting all docs
        $docs = $helper->fetch_all(
            "select `name` from `docs` where `min_privilege_level` <= '{$privilege_level}';"
        );
        // make response
        Rtfd_Response::json_response($docs);
    }
}
