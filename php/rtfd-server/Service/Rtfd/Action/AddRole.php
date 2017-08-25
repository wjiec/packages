<?php
/**
 * AddRole Action
 */

class Rtfd_Action_AddRole extends Rtfd_Abstract_Action {
    /**
     * add new role
     */
    public function start() {
        $role_name = $this->get_option('name');
        $privilege_level = $this->get_option('pl');

        if (!$role_name || !$privilege_level) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'lose some fields'
            ));
        }

        $privilege_level = intval($privilege_level);
        if ($privilege_level <= 0 || $privilege_level > 100) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'privilege level invalid'
            ));
        }

        $helper = new Rtfd_Helper_Database();
        $role = $helper->fetch_single_row(
            "select count(*) as cnt from `roles` where `name`='{$role_name}';"
        );
        if ($role['cnt'] !== '0') {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'duplicate role name'
            ));
        }

        $result = $helper->query(
            "insert into `roles`(`name`, `privilege_level`) values('{$role_name}', '{$privilege_level}');"
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
