<?php
/**
 * Dispatcher.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub;


use WeHub\Response\ResponseInterface;
use WeHub\Response\SimpleResponse;
use WeHub\Utils\Callback;


/**
 * Class Dispatcher
 * @package WeHub
 */
final class Dispatcher {

    /**
     * @var Callback[]
     */
    private static $event_callback = array();

    /**
     * @var null|Callback
     */
    private static $callback_provider = null;

    /**
     * Dispatcher constructor.
     */
    final public function __construct() {
    }

    /**
     * @param string $action
     * @param Callback $callback
     */
    final public static function on(string $action, Callback $callback) {
        self::$event_callback[$action] = $callback;
    }

    /**
     * @param Callback $provider
     */
    final public static function registerCallbackProvider(Callback $provider) {
        self::$callback_provider = $provider;
    }

    /**
     * @param Event $event
     * @return ResponseInterface
     * @throws DispatcherError
     */
    final public function dispatch(Event $event): ResponseInterface {
        $callback = self::getActionCallback($event);
        if ($callback === null) {
            return new SimpleResponse($event->getAction());
        }

        $response_class = sprintf('%s\Response\%sResponse',
            __NAMESPACE__, ucfirst($event->getAction()->getCamelCaseActionName()));

        if (!class_exists($response_class)) {
            $response_class = SimpleResponse::class;
        }

        try {
            return new $response_class($event->getAction(), $callback->apply($event));
        } catch (\Throwable $e) {
            if (strpos($e->getMessage(), 'Argument') === 0) {
                throw new DispatcherError("`{$event->getAction()->getActionName()}` " .
                    "action callback return value invalid");
            }
            throw new DispatcherError($e->getMessage());
        }
    }

    /**
     * @param Event $event
     * @return Callback|null
     */
    final private static function getActionCallback(Event $event): ?Callback {
        if (self::$callback_provider !== null) {
            $callback = self::$callback_provider->apply($event);
            if ($callback instanceof Callback) {
                return $callback;
            }
        }

        if (isset(self::$event_callback[$event->getAction()->getActionName()])) {
            return self::$event_callback[$event->getAction()->getActionName()];
        }

        return null;
    }

}
