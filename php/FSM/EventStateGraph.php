<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 11/25/2017
 * Time: 14:18
 */
namespace FSM;


/**
 * Class EventStateGraph
 * @package FSM
 */
final class EventStateGraph {
    /**
     * @var array
     */
    private $_state;

    /**
     * @var array
     */
    private $_event;

    /**
     * @var array
     */
    private $_matrix;

    /**
     * EventStateGraph constructor.
     */
    final public function __construct() {
        $this->_state = array();
        $this->_event = array();
        $this->_matrix = array();
    }

    /**
     * @param array ...$states
     */
    final public function set_state(...$states): void {
        $this->_state = $states;
    }

    /**
     * @param array ...$events
     */
    final public function set_event(...$events): void {
        $this->_event = $events;
    }

    /**
     * @param array ...$action
     * @return EventStateGraph
     */
    final public function fill(...$actions): self {
        if (count($actions) !== count($this->_state)) {
            throw new \Exception("fill actions not matched");
        }
        $this->_matrix[] = $actions;

        return $this;
    }

    /**
     * @param string $state
     * @param string $event
     */
    final public function match(string $state, string $event): string {
        if (!in_array($state, $this->_state) || !in_array($event, $this->_event)) {
            throw new \Exception("state or event not defined");
        }

        $state_index = array_search($state, $this->_state);
        $event_index = array_search($event, $this->_event);

        return $this->_matrix[$event_index][$state_index];
    }
}
