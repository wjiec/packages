<?php
/**
 * WeHubEvent.php
 *
 * @package   YiFenRobot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub;


use WeHub\Action\ActionInterface;
use WeHub\Action\ActionSimple;
use WeHub\Validator\ValidatorFactory;

/**
 * Class WeHubEvent
 * @package WeHub
 */
final class Event {

    /**
     * @var ActionInterface
     */
    private $action;

    /**
     * @var string
     */
    private $app_id;

    /**
     * @var string
     */
    private $wx_id;

    /**
     * @var array
     */
    private $data;

    /**
     * WeHubEvent constructor.
     * @param string $action
     * @param string $app_id
     * @param string $wx_id
     * @param array $data
     */
    final private function __construct(string $action, string $app_id, string $wx_id, array $data) {
        $this->action = new ActionSimple($action);
        $this->app_id = $app_id;
        $this->wx_id = $wx_id;
        $this->data = $data;
    }

    /**
     * @param array $event
     * @param null|ValidatorFactory $validator
     * @return null|Event
     */
    final static function factory(array $event, ?ValidatorFactory $validator): ?self {
        $constructor_params = array();
        foreach (self::EVENT_STRUCTURE as $param_key) {
            if (!isset($event[$param_key])) {
                return null;
            }

            try {
                if (!$validator->getValidator($param_key)->validate($event[$param_key], $event)) {
                    return null;
                }
            } catch (Validator\ValidatorError $e) {
                return null;
            }

            $constructor_params[] = $event[$param_key];
        }
        return new self(...$constructor_params);
    }

    /**
     * @return ActionInterface
     */
    final public function getAction(): ActionInterface {
        return $this->action;
    }

    /**
     * @return string
     */
    final public function getAppId(): string {
        return $this->app_id;
    }

    /**
     * @return string
     */
    final public function getWxId(): string {
        return $this->wx_id;
    }

    /**
     * @return array
     */
    final public function getData(): array {
        return $this->data;
    }

    private const EVENT_STRUCTURE = array(
        'action', 'appid', 'wxid', 'data'
    );
}
