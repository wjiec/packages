<?php
/**
 * Update group
 */


class Rtfd_Action_UpdateGroup extends Rtfd_Abstract_Action {
    /**
     * update group
     */
    public function start() {
        $gid = $this->get_option('gid');
        $name = $this->get_option('name');

        if (intval($gid) === 1) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'cannot modify admin role'
            ));
        }

        $helper = new Rtfd_Helper_Database();
        $group = $helper->fetch_single_row(
            "select count(*) as cnt from `groups` where `gid`='{$gid}';"
        );
        if ($group['cnt'] === '0') {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'group not found'
            ));
        }

        $result = $helper->query(
            "update
                    `groups`
                  set
                    `name`='{$name}'
                  where
                    `gid`='{$gid}';"
        );
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'update error occurs'
            ));
        }

        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}