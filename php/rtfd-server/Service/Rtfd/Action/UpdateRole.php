<?php
/**
 * Update role action
 */


class Rtfd_Action_UpdateRole extends Rtfd_Abstract_Action {
    /**
     * update role
     */
    public function start() {
        $rid = $this->get_option('rid');
        $name = $this->get_option('name');
        $privilege_level = $this->get_option('pl');

        if (intval($rid) === 1) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'cannot modify admin role'
            ));
        }

        $helper = new Rtfd_Helper_Database();
        $role = $helper->fetch_single_row(
            "select count(*) as cnt from `roles` where `rid`='{$rid}';"
        );
        if ($role['cnt'] === '0') {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'role not found'
            ));
        }

        $result = $helper->query(
            "update
                    `roles`
                  set
                    `name`='{$name}',
                    `privilege_level`='{$privilege_level}'
                  where
                    `rid`='{$rid}';"
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