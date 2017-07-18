<?php
/**
 * Get Doc Content Action
 */


class Rtfd_Action_GetDocContent extends Rtfd_Abstract_Action {
    /**
     * Loading file contents
     */
    public function start() {
        // getting path parameter
        $path = $this->get_option('path');
        // explode doc_name and path
        list($doc_name, $rel_path) = explode('@', $path);
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // select absolute path
        $root = $helper->fetch_single_row(
            "select `path` from `docs` where `name`='{$doc_name}';"
        );
        // build absolute path
        $absolute_path = join('/', array(
            rtrim($root['path'], '/\\'),
            trim($rel_path, '/\\'))
        );
        // check file exists
        if (is_file($absolute_path)) {
            include $absolute_path;
        } else {
            // encoding error, try gb2312
            $encoding_path = iconv('utf-8', 'gb2312', $absolute_path);
            if (is_file($encoding_path)) {
                include $encoding_path;
            } else {
                echo 'xxx';
            }
        }

        // getting output buffer
        $contents = ob_get_clean();
        // is enable syntax highlight
        if ($this->get_option('hl', 'no') === false) {
            // replace unsupported syntax
            $contents = preg_replace('/```\w+/', '```', $contents);
            $contents = preg_replace('/~~~\w+/', '~~~', $contents);
            // escape
            $contents = htmlentities($contents);
        }
        // make response
        Rtfd_Response::json_response(array(
            'path' => $path,
            'contents' => $contents
        ));
    }
}
