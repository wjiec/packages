<?php
/**
 * Finite State Machine Example
 */
namespace FSM;
require_once __DIR__ . '/../bootstrap.php';


/**
 * Class CarEventFactory
 * @package FSM
 */
class CarEventFactory implements EventFactoryInterface {
    public function getIterator() {
        return new \ArrayIterator(array('Run', 'Run', 'Stop', 'Run', 'Stop', 'Stop'));
    }
}
$factory = new CarEventFactory();

$fsm_graph = new EventStateGraph();
$fsm_graph->set_state('Stopped', 'Running', 'Stopping');
$fsm_graph->set_event('Run', 'Stop');
$fsm_graph/* Stopped      Running     Stopping */
/*  Run */->fill('Running', 'Running', 'Running')
/* Stop */->fill('Stopped', 'Stopping', 'Stopped')
;

$fsm = new FiniteStateMachine($fsm_graph);
$fsm->run($factory, 'Stopped');
