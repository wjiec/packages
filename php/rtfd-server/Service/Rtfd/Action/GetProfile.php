<?php
/**
 * Get self profile
 */


class rtfd_Action_GetProfile extends Rtfd_Abstract_Action {
    /**
     * get profile
     */
    public function start() {
        $uid = $this->get_config()->get_user_id();

        $helper = new Rtfd_Helper_Database();
        $user_info = $helper->fetch_single_row(
            "select
                    `users`.`uid` as `uid`,
                    `users`.`name` as `name`,
                    `users`.`nickname` as `nickname`,
                    `roles`.`name` as `role`,
                    `groups`.`name` as `group`
                  from `users`
    
                  inner join `roles`
                    on `roles`.`rid`=`users`.`role_id`
    
                  inner join `groups`
                    on `groups`.`gid`=`users`.`group_id`
                  
                  where `users`.`uid`='{$uid}'
                  ;"
        );

        if ($user_info === false) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'cannot fetch user profile'
            ));
        }

        // make response
        Rtfd_Response::json_response($user_info);
    }
}