<?php
/**
 * Update doc action
 */


class Rtfd_Action_UpdateDoc extends Rtfd_Abstract_Action {
    /**
     * update user
     */
    public function start() {
        // getting user info
        $cid = $this->get_option('cid');
        $name = $this->get_option('name');
        $path = $this->get_option('path');
        $mpl = intval($this->get_option('mpl'));
        $owner_id = $this->get_option('owner_id');
        $group_id = $this->get_option('group_id');
        // check user info
        if (!$cid || !$name || !$path || !$owner_id || !$group_id) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'lose some fields'
            ));
        }
        // check exists
        $helper = new Rtfd_Helper_Database();
        $doc = $helper->fetch_single_row(
            "select * from `docs` where `cid`='{$cid}';"
        );
        // check user
        if (!$doc) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'doc not found'
            ));
        }
        // update
        $result = $helper->query(
            "update
                    `docs`
                  set
                    `name`='{$name}',
                    `path`='{$path}',
                    `owner_id`='{$owner_id}',
                    `group_id`='{$group_id}',
                    `min_privilege_level`='{$mpl}'
                  where
                    `cid`='{$cid}'
                  ;"
        );
        // check result
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot update user'
            ));
        }
        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}
