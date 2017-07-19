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
        // current user privilege level
        $privilege_level = $this->get_config()->get_privilege_level();
        // explode doc_name and path
        list($doc_name, $rel_path) = explode('@', $path);
        // fix rel_path
        $rel_path = str_replace('!', '/', $rel_path);
        // create database helper
        $helper = new Rtfd_Helper_Database();
        // select absolute path
        $root = $helper->fetch_single_row(
            "select
                    `path`
                  from `docs`
                  where `name`='{$doc_name}'
                    and (
                      `min_privilege_level`<='{$privilege_level}'
                      or `group_id`='{$this->get_config()->get_group_id()}'
                      or `owner_id`='{$this->get_config()->get_user_id()}'
                    )
                  ;"
        );
        if (!$root) {
            Rtfd_Request::abort(401, array(
                'errno' => 401,
                'error' => 'privilege level not match'
            ));
        }
        // build absolute path
        $absolute_path = join('/', array(
            rtrim($root['path'], '/\\'),
            trim($rel_path, '/\\'))
        );
        // check file exists
        if (is_file($absolute_path)) {
            readfile($absolute_path);
        } else {
            // encoding error, try gb2312
            $encoding_path = iconv('utf-8', 'gb2312', $absolute_path);
            if (is_file($encoding_path)) {
                readfile($encoding_path);
            } else {
                echo 'xxx';
            }
        }

        // getting output buffer
        $contents = ob_get_clean();
        // is enable syntax highlight
        if ($this->get_option('hl', 'no') === 'yes') {
            // replace unsupported syntaxrtfd@localhost
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
