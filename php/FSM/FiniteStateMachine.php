<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 11/25/2017
 * Time: 10:40
 */
namespace FSM;


/**
 * Class FiniteStateMachine
 * @package FSM
 */
final class FiniteStateMachine {
    /**
     * @var EventStateGraph
     */
    private $_graph;

    /**
     * FiniteStateMachine constructor.
     */
    final public function __construct(EventStateGraph $graph) {
        $this->_graph = $graph;
    }

    /**
     * @param EventFactoryInterface $factory
     */
    final public function run(EventFactoryInterface $factory, $start_state): void {
        foreach ($factory as $event) {
            echo sprintf("Machine State: %s\n", $start_state);
            echo sprintf("Machine Receive Event: %s\n", $event);

            $next_state = $this->_graph->match($start_state, $event);
            echo sprintf("Machine Next State: %s\n", $next_state);
            $start_state = $next_state;
        }
    }
}
