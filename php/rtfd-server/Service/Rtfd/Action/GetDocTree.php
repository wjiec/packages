<?php
/**
 * Action for GetDocsTree
 */


class Rtfd_Action_GetDocTree extends Rtfd_Abstract_Action {
    /**
     * get docs tree start
     */
    public function start() {
        // doc name
        $doc = $this->get_option('doc');
        // check exists
        if ($doc === null) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'option `doc` not found'
            ));
        }

        if (preg_match('/^share_(\w{32})$/', $doc, $results)) {
            self::_generate_share_tree($doc, $results[1]);
        }

        // create database helper
        $helper = new Rtfd_Helper_Database();
        // getting doc path
        $path = $helper->fetch_single_row(
            "select `path` from `docs` where `name`='$doc';"
        );
        try {
            // result array
            $result = null;
            // start scan dir
            $this->scan_dir($result, $doc, $path['path'], '');
            // make response
            Rtfd_Response::json_response($result);
        } catch (Exception $e) {
            Rtfd_Request::abort(500, array(
                'errno' => $e->getCode(),
                'error' => $e->getMessage()
            ));
        }
    }

    /**
     * @param string $doc_name
     * @param string $path
     * @param string $rel_path
     * @return array
     */
    private function scan_dir(&$result, $doc_name, $path, $rel_path) {
        // simple file
        if (is_file($path)) {
            $file_name = explode('/', trim($rel_path, '/\\'));

            $result = array(
                'is_file' => true,
                'name' => $file_name[count($file_name) - 1],
                'path' => str_replace('/', '!', join('@', array($doc_name, $rel_path))),
                'children' => null
            );
            return;
        }
        $result = array();
        // scan directory
        $dir = scandir($path);
        // check result
        if ($dir === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot access'
            ));
        }
        // for each all child dir
        foreach ($dir as $name) {
            // continue hidden files and dir
            if ($name[0] === '.') {
                continue;
            }
            // encoding solve
            $encode_name = Rtfd_Utils::Text($name);
            // real path for child
            $real_path = join('/', array($path, $name));
            // relative path
            $relative_path = join('/', array($rel_path, $encode_name));
            // check is dir
            if (is_dir($real_path)) {
                $result[$encode_name] = array(
                    'is_file' => false,
                    'name' => $encode_name,
                    'path' => str_replace('/', '!', join('@', array($doc_name, $relative_path))),
                    'children' => array()
                );
                $this->scan_dir($result[$encode_name]['children'], $doc_name, $real_path, $relative_path);
            } else {
                // scan child file
                $this->scan_dir($result[$encode_name], $doc_name, $real_path, $relative_path);
            }
        }
    }

    /**
     * @param string $hash
     */
    private function _generate_share_tree($doc, $hash) {
        $helper = new Rtfd_Helper_Database();

        $share_info = $helper->fetch_single_row(
            "select * from `share` where `code`='{$hash}';"
        );

        if (!$share_info) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot access'
            ));
        }

        $paths = json_decode(urldecode($share_info['paths']), true);
        if ($paths === null) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'server error'
            ));
        }

        $file_name = explode('!', $paths[0]);
        Rtfd_Response::json_response(array(
            $file_name[count($file_name) - 1] => array(
                'is_file' => true,
                'name' => $file_name[count($file_name) - 1],
                'path' => $doc . '@!' . $file_name[count($file_name) - 1],
                'children' => null
            )
        ));
    }
}
