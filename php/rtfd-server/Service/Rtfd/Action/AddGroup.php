<?php
/**
 * Add group action
 */


class Rtfd_Action_AddGroup extends Rtfd_Abstract_Action {
    /**
     * add group
     */
    public function start() {
        $group_name = $this->get_option('name');

        if (!$group_name) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'lose group name'
            ));
        }

        $helper = new Rtfd_Helper_Database();
        $group = $helper->fetch_single_row(
            "select count(*) as cnt from `groups` where `name`='{$group_name}';"
        );

        if ($group['cnt'] !== '0') {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'duplicate group name'
            ));
        }

        $result = $helper->query(
            "insert into `groups`(`name`) values('{$group_name}');"
        );
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'insert error occurs'
            ));
        }

        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}