<?php
/**
 * Users Manager
 */


class Rtfd_Action_GetUsers extends Rtfd_Abstract_Action {
    /**
     * getting user list
     */
    public function start() {
        // database helper
        $helper = new Rtfd_Helper_Database();
        // fetch all users
        $users = $helper->fetch_all(
        "select
                `users`.`uid` as `user_id`,
                `users`.`name` as `user_name`,
                `roles`.`name` as `role_name`,
                `groups`.`name` as `group_name`
              from `users`

              inner join `roles`
                on `roles`.`rid`=`users`.`role_id`

              inner join `groups`
                on `groups`.`gid`=`users`.`group_id`
              ;"
        );
        // check result
        if (!$users) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot fetch users list'
            ));
        }
        // Roles
        $roles = $helper->fetch_all(
            "select `rid`, `name` from `roles`;"
        );
        // check result
        if (!$roles) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot fetch roles list'
            ));
        }
        // Roles
        $groups = $helper->fetch_all(
            "select `gid`, `name` from `groups`;"
        );
        // check result
        if (!$groups) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot fetch groups list'
            ));
        }
        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'users' => $users,
            'roles' => $roles,
            'groups' => $groups
        ));
    }
}
