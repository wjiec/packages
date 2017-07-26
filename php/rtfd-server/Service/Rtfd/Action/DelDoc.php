<?php
/**
 * Delete Doc
 */


class Rtfd_Action_DelDoc extends Rtfd_Abstract_Action {
    /**
     * del user
     */
    public function start() {
        // user id
        $cid = $this->get_option('cid');
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // check exists
        $doc = $helper->fetch_single_row(
            "select * from `docs` where `cid`='{$cid}';"
        );
        // check exists
        if (!$doc) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'doc not found'
            ));
        }
        // delete
        $result = $helper->query(
            "delete from `docs` where `cid`='{$cid}';"
        );
        // check result
        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot delete user'
            ));
        }
        // make response
        Rtfd_Response::json_response(array(
            'status' => 0,
            'message' => 'success'
        ));
    }
}
