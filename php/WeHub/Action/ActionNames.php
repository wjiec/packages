<?php
/**
 * ActionNames.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Action;


/**
 * Class ActionNames
 * @package WeHub\Action
 */
final class ActionNames {

    public const LOGIN_ACTION = 'login';

    public const LOGOUT_ACTION = 'logout';

    public const REPORT_CONTACT_ACTION = 'report_contact';

    public const REPORT_NEW_FRIEND_ACTION = 'report_new_friend';

    public const REPORT_NEW_MSG_ACTION = 'report_new_msg';

    public const PULL_TASK_ACTION = 'pull_task';

    public const PULL_TASK_RESULT_ACTION = 'report_task_result';

    /**
     * @param string $action
     * @return bool
     */
    final static public function has(string $action): bool {
        try {
            $ref = new \ReflectionClass(__CLASS__);
            return in_array($action, array_values($ref->getConstants()));
        } catch (\ReflectionException $e) {
            return false;
        }
    }
}
