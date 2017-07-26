<?php
/**
 * AddDoc action
 */


class Rtfd_Action_AddDoc extends Rtfd_Abstract_Action {
    /**
     * add user
     */
    public function start() {
        // docs account info
        $name = $this->get_option('name');
        $path = $this->get_option('path');
        $owner_id = $this->get_option('owner_id');
        $group_id = $this->get_option('group_id');
        $mpl = intval($this->get_option('mpl'));

        // check parameter
        if (!$name || !$path || !$owner_id || !$group_id) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'parameter invalid'
            ));
        }

        // check role_id and group_id is valid
        $helper = new Rtfd_Helper_Database();
        // check role exists
        $owner = $helper->fetch_single_row(
            "select * from `users` where `uid`='{$owner_id}';"
        );
        if (!$owner) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'owner not found, forbidden'
            ));
        }
        // check group exists
        $group = $helper->fetch_single_row(
            "select * from `groups` where `gid`='{$group_id}';"
        );
        if (!$group) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'group not found, forbidden'
            ));
        }

        // check doc exists
        $doc = $helper->fetch_single_row(
            "select * from `docs` where `name`='{$name}';"
        );
        if ($doc) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'duplicate doc'
            ));
        }

        // check directory exists
        if (!is_dir($path)) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'directory invalid'
            ));
        }

        // insert
        $result = $helper->query(
            "insert into `docs`
                    (`name`, `path`, `owner_id`, `group_id`, `min_privilege_level`)
                  values
                    ('{$name}', '{$path}', '{$owner_id}', '{$group_id}', '{$mpl}');"
        );
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'insert error occurs'
            ));
        }

        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}
